package net.onfirenetwork.onsetjava.simple;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Getter;
import net.onfirenetwork.onsetjava.api.CommandExecutor;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.OnsetServer;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.event.*;
import net.onfirenetwork.onsetjava.api.event.client.UnknownClientEvent;
import net.onfirenetwork.onsetjava.api.event.client.PlayerEnterVehicleEvent;
import net.onfirenetwork.onsetjava.api.event.client.PlayerExitVehicleEvent;
import net.onfirenetwork.onsetjava.api.event.server.UnknownServerEvent;
import net.onfirenetwork.onsetjava.api.plugin.Plugin;
import net.onfirenetwork.onsetjava.api.plugin.PluginManager;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.NetworkStats;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.simple.adapter.ActionAdapter;
import net.onfirenetwork.onsetjava.simple.adapter.ActionAdapterListener;
import net.onfirenetwork.onsetjava.simple.adapter.InboundAction;
import net.onfirenetwork.onsetjava.simple.adapter.OutboundAction;
import net.onfirenetwork.onsetjava.simple.event.DefaultClientEventTransformer;
import net.onfirenetwork.onsetjava.simple.event.DefaultServerEventTransformer;
import net.onfirenetwork.onsetjava.simple.plugin.SimplePluginManager;
import org.javatuples.Triplet;

import java.io.File;
import java.util.*;

public class SimpleOnsetServer implements OnsetServer {

    private int nextNonce = 1;
    private int nextDimId = 0;
    @Getter
    private SimpleScheduler scheduler;
    private ActionAdapter adapter;
    private Map<Integer, Completable<JsonElement[]>> returnFutures = new HashMap<>();
    @Getter
    private List<Dimension> dimensions = new ArrayList<>();
    @Getter
    private List<Player> players = new ArrayList<>();
    @Getter
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<NPC> npcs = new ArrayList<>();
    @Getter
    private List<WorldObject> objects = new ArrayList<>();
    private List<Text3D> text3ds = new ArrayList<>();
    @Getter
    private List<Pickup> pickups = new ArrayList<>();
    @Getter
    private List<Light> lights = new ArrayList<>();
    @Getter
    private List<Door> doors = new ArrayList<>();
    @Getter
    private EventBus eventBus;
    @Getter
    private PluginManager pluginManager;
    private Map<String, CommandExecutor> commandMap = new HashMap<>();
    @Getter
    private List<String> enabledClientEvents = new ArrayList<>();
    @Getter
    private List<String> registeredKeys = new ArrayList<>();
    private List<ClientEventTransformer> clientEventTransformers = new ArrayList<>();
    private List<ServerEventTransformer> serverEventTransformers = new ArrayList<>();

    public SimpleOnsetServer() {
        scheduler = new SimpleScheduler();
        eventBus = new EventBus(this::registerHandler);
        pluginManager = new SimplePluginManager(this);
        adapter = new ActionAdapter(System.in, System.out, new ActionAdapterListener() {
            public void onAction(InboundAction action) {
                if (action.getType().equals("Return")) {
                    processReturn(action);
                    return;
                }
                if (action.getType().equals("Command")) {
                    processCommand(action);
                    return;
                }
                Event event = null;
                for(ServerEventTransformer transformer : serverEventTransformers){
                    event = transformer.transform(action.getType(), action.getNonce(), action.getParams());
                    if(event != null) {
                        break;
                    }
                }
                if (event == null) {
                    event = new UnknownServerEvent(action.getType(), action.getNonce(), action.getParams());
                }
                eventBus.fire(event);
            }

            public void onClientAction(Player player, InboundAction action) {
                if (action.getType().equals("Return")) {
                    processReturn(action);
                    return;
                }
                Event event = null;
                for(ClientEventTransformer transformer : clientEventTransformers){
                    event = transformer.transform(player, action.getType(), action.getNonce(), action.getParams());
                    if(event != null) {
                        break;
                    }
                }
                if (event == null) {
                    event = new UnknownClientEvent(player, action.getType(), action.getNonce(), action.getParams());
                }
                eventBus.fire(event);
                if(event instanceof Cancellable){
                    if(event instanceof PlayerEnterVehicleEvent ){
                        PlayerEnterVehicleEvent e = (PlayerEnterVehicleEvent) event;
                        if(!e.isCancelled()){
                            e.getPlayer().enterVehicle(e.getVehicle());
                        }
                    }
                    if(event instanceof PlayerExitVehicleEvent){
                        PlayerExitVehicleEvent e = (PlayerExitVehicleEvent) event;
                        if(!e.isCancelled()){
                            e.getPlayer().exitVehicle();
                        }
                    }
                }
            }
        });
        addServerEventTransformer(new DefaultServerEventTransformer());
        addClientEventTransformer(new DefaultClientEventTransformer());
    }

    public void run() {
        adapter.prepare();
        scheduler.start();
        createDimension();
        registerServerEvent("OnPlayerServerAuth", "OnPlayerJoin", "OnPlayerQuit");
        File pluginFolder = new File("java_plugins");
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        } else {
            ((SimplePluginManager) pluginManager).load(pluginFolder);
        }
        for (Plugin plugin : pluginManager.getPlugins()) {
            plugin.onEnable();
            print("Enabled "+pluginManager.getInfo(plugin));
        }
        adapter.startSync();
        for (Plugin plugin : pluginManager.getPlugins()) {
            plugin.onDisable();
            print("Disabled "+pluginManager.getInfo(plugin));
        }
    }

    public void stop() {
        adapter.stop();
    }

    private int nonce() {
        int nonce = nextNonce;
        nextNonce++;
        return nonce;
    }

    private void processReturn(InboundAction action) {
        if (returnFutures.containsKey(action.getNonce())) {
            Completable<JsonElement[]> future = returnFutures.get(action.getNonce());
            returnFutures.remove(action.getNonce());
            future.complete(action.getParams());
        }
    }

    private void processCommand(InboundAction action) {
        String command = action.getParams()[0].getAsString().toLowerCase(Locale.GERMAN);
        if (commandMap.containsKey(command)) {
            String[] args = new String[action.getParams().length - 2];
            for (int i = 0; i < args.length; i++) {
                args[i] = action.getParams()[i + 2].getAsString();
            }
            commandMap.get(command).onCommand(command, getPlayer(action.getParams()[1].getAsInt()), args);
        }
    }

    public void registerCommand(String name, CommandExecutor executor) {
        commandMap.put(name, executor);
        callAction("RegisterCommand", 0, name);
    }

    public void registerKeys(String... keys) {
        for (String key : keys) {
            if (!registeredKeys.contains(key)) {
                registeredKeys.add(key);
            }
        }
        for (Player player : players) {
            callClientAction(player.getId(), "RegisterKeys", 0, (Object) keys);
        }
    }

    public void registerServerEvent(String... eventNames) {
        if(eventNames.length == 0)
            return;
        callAction("RegisterEvents", 0, (Object) eventNames);
    }

    public void registerClientEvent(String... eventNames) {
        if(eventNames.length == 0)
            return;
        enabledClientEvents.addAll(Arrays.asList(eventNames));
        for (Player player : players) {
            callClientAction(player.getId(), "RegisterEvents", 0, (Object) eventNames);
        }
    }

    public void callAction(String type, int nonce, Object... params) {
        adapter.call(new OutboundAction(type, nonce, params));
    }

    public void callClientAction(int playerId, String type, int nonce, Object... params) {
        adapter.call(new OutboundAction("Forward", 0, playerId, new Gson().toJson(new OutboundAction(type, nonce, params))));
    }

    private Triplet<Integer, Object[], Completable<JsonElement[]>> prepareCall(String name, Object... params) {
        int nonce = nonce();
        Completable<JsonElement[]> future = new Completable<>();
        returnFutures.put(nonce, future);
        Object[] p = new Object[params.length + 1];
        p[0] = name;
        for (int i = 0; i < params.length; i++) {
            p[i + 1] = params[i];
        }
        return Triplet.with(nonce, p, future);
    }

    public Completable<JsonElement[]> call(String name, Object... params) {
        Triplet<Integer, Object[], Completable<JsonElement[]>> tuple = prepareCall(name, params);
        callAction("Call", tuple.getValue0(), tuple.getValue1());
        return tuple.getValue2();
    }

    public Completable<JsonElement[]> callClient(int playerId, String name, Object... params) {
        Triplet<Integer, Object[], Completable<JsonElement[]>> tuple = prepareCall(name, params);
        callClientAction(playerId, "Call", tuple.getValue0(), tuple.getValue1());
        return tuple.getValue2();
    }

    public Dimension createDimension() {
        int dimId = nextDimId;
        nextDimId++;
        Dimension dimension = new SimpleDimension(this, dimId);
        dimensions.add(dimension);
        return dimension;
    }

    public Dimension getDimension(int id) {
        for (Dimension dimension : dimensions) {
            if (dimension.getId() == id) {
                return dimension;
            }
        }
        return null;
    }

    public void broadcast(String message) {
        call("AddPlayerChatAll", message);
    }

    public void broadcastRange(Vector2d location, int range, String message) {
        call("AddPlayerChatRange", location.getX(), location.getY(), range, message);
    }

    public void print(String message) {
        call("print", message);
    }

    public int getTickCount() {
        return call("GetTickCount").get()[0].getAsInt();
    }

    public int getGameVersion() {
        return call("GetGameVersion").get()[0].getAsInt();
    }

    public String getGameVersionString() {
        return call("GetGameVersionString").get()[0].getAsString();
    }

    public int getServerTickRate() {
        return call("GetServerTickRate").get()[0].getAsInt();
    }

    public void shutdown(String message) {
        if (message != null) {
            call("ServerExit", message);
        } else {
            call("ServerExit");
        }
    }

    public String getName() {
        return call("GetServerName").get()[0].getAsString();
    }

    public void setName(String name) {
        call("SetServerName", name);
    }

    public int getMaxPlayers() {
        return call("GetMaxPlayers").get()[0].getAsInt();
    }

    public NetworkStats getNetworkStats() {
        return new NetworkStats(call("GetNetworkStats").get()[0].getAsJsonObject());
    }

    public Player getPlayer(int id) {
        return players.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Vehicle getVehicle(int id) {
        return vehicles.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public List<NPC> getNPCs() {
        return npcs;
    }

    public NPC getNPC(int id) {
        return null;
    }

    public WorldObject getObject(int id) {
        return objects.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public List<Text3D> getText3Ds() {
        return text3ds;
    }

    public Text3D getText3D(int id) {
        return text3ds.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Pickup getPickup(int id) {
        return pickups.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Light getLight(int id) {
        return lights.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Door getDoor(int id){
        return doors.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public void addClientEventTransformer(ClientEventTransformer transformer){
        clientEventTransformers.add(transformer);
    }

    public void addServerEventTransformer(ServerEventTransformer transformer){
        serverEventTransformers.add(transformer);
    }

    private void registerHandler(Class<Event> eventClass) {
        for(ServerEventTransformer transformer : serverEventTransformers){
            registerServerEvent(transformer.register(eventClass));
        }
        for(ClientEventTransformer transformer : clientEventTransformers){
            registerClientEvent(transformer.register(eventClass));
        }
    }
}
