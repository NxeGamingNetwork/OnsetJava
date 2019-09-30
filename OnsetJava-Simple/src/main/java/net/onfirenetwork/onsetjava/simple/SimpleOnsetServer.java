package net.onfirenetwork.onsetjava.simple;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Getter;
import net.onfirenetwork.onsetjava.api.CommandExecutor;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.OnsetServer;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.EventBus;
import net.onfirenetwork.onsetjava.api.event.client.*;
import net.onfirenetwork.onsetjava.api.event.server.*;
import net.onfirenetwork.onsetjava.api.plugin.Plugin;
import net.onfirenetwork.onsetjava.api.plugin.PluginManager;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.NetworkStats;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.simple.adapter.ActionAdapter;
import net.onfirenetwork.onsetjava.simple.adapter.ActionAdapterListener;
import net.onfirenetwork.onsetjava.simple.adapter.InboundAction;
import net.onfirenetwork.onsetjava.simple.adapter.OutboundAction;
import net.onfirenetwork.onsetjava.simple.event.ClientEventTransformer;
import net.onfirenetwork.onsetjava.simple.event.ServerEventTransformer;
import net.onfirenetwork.onsetjava.simple.plugin.SimplePluginManager;
import org.javatuples.Triplet;

import java.io.File;
import java.util.*;

public class SimpleOnsetServer implements OnsetServer {

    private int nextNonce = 1;
    private int nextDimId = 0;
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
    private EventBus eventBus;
    @Getter
    private PluginManager pluginManager;
    private Map<String, CommandExecutor> commandMap = new HashMap<>();
    @Getter
    private List<String> enabledClientEvents = new ArrayList<>();
    @Getter
    private List<String> registeredKeys = new ArrayList<>();

    public SimpleOnsetServer() {
        eventBus = new EventBus(this::registerHandler);
        pluginManager = new SimplePluginManager(this);
        ClientEventTransformer clientEventTransformer = new ClientEventTransformer();
        ServerEventTransformer serverEventTransformer = new ServerEventTransformer();
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
                Event event = serverEventTransformer.transform(action);
                if (event != null) {
                    eventBus.fire(event);
                    return;
                }
            }

            public void onClientAction(Player player, InboundAction action) {
                if (action.getType().equals("Return")) {
                    processReturn(action);
                    return;
                }
                Event event = clientEventTransformer.transform(player, action);
                if (event != null) {
                    eventBus.fire(event);
                    return;
                }
            }
        });
    }

    public void run() {
        adapter.prepare();
        createDimension();
        enableEvents("OnPlayerServerAuth", "OnPlayerQuit");
        File pluginFolder = new File("java_plugins");
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        } else {
            ((SimplePluginManager) pluginManager).load(pluginFolder);
        }
        for (Plugin plugin : pluginManager.getPlugins()) {
            plugin.onEnable();
        }
        adapter.startSync();
        for (Plugin plugin : pluginManager.getPlugins()) {
            plugin.onDisable();
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

    public void enableEvents(String... eventNames) {
        callAction("RegisterEvents", 0, (Object) eventNames);
    }

    public void enableClientEvents(String... eventNames) {
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

    private void registerHandler(Class<Event> eventClass) {
        //Server
        if (eventClass.equals(PlayerJoinEvent.class))
            enableEvents("OnPlayerJoin");
        else if (eventClass.equals(PlayerEnterVehicleEvent.class))
            enableEvents("OnPlayerEnterVehicle");
        else if (eventClass.equals(PlayerExitVehicleEvent.class))
            enableEvents("OnPlayerLeaveVehicle");
        else if (eventClass.equals(PlayerDeathEvent.class))
            enableEvents("OnPlayerDeath");
        else if (eventClass.equals(PlayerSpawnEvent.class))
            enableEvents("OnPlayerSpawn");
        else if (eventClass.equals(PlayerPickupEvent.class))
            enableEvents("OnPlayerPickupHit");
        else if (eventClass.equals(VehiclePickupEvent.class))
            enableEvents("OnVehiclePickupHit");
        else if (eventClass.equals(PlayerStateChangeEvent.class))
            enableEvents("OnPlayerStateChange");
        else if (eventClass.equals(NPCDeathEvent.class))
            enableEvents("OnNPCDeath");
        else if (eventClass.equals(VehicleRespawnEvent.class))
            enableEvents("OnVehicleRespawn");
        else if (eventClass.equals(PlayerDamageEvent.class))
            enableEvents("OnPlayerDamage");
        else if (eventClass.equals(PlayerChatEvent.class))
            enableEvents("OnPlayerChat");
        else if (eventClass.equals(PlayerStreamInEvent.class))
            enableEvents("OnPlayerStreamIn");
        else if (eventClass.equals(PlayerStreamOutEvent.class))
            enableEvents("OnPlayerStreamOut");
        else if (eventClass.equals(VehicleStreamInEvent.class))
            enableEvents("OnVehicleStreamIn");
        else if (eventClass.equals(VehicleStreamOutEvent.class))
            enableEvents("OnVehicleStreamOut");
        else if (eventClass.equals(PlayerWeaponShotEvent.class))
            enableEvents("OnPlayerWeaponShot");
        else if (eventClass.equals(PlayerCommandEvent.class))
            enableEvents("OnPlayerChatCommand");
        else if (eventClass.equals(NPCSpawnEvent.class))
            enableEvents("OnNPCSpawn");
        else if (eventClass.equals(NPCDamageEvent.class))
            enableEvents("OnNPCDamage");
        else if (eventClass.equals(NPCReachTargetEvent.class))
            enableEvents("OnNPCReachTarget");
        else if (eventClass.equals(ClientConnectionEvent.class))
            enableEvents("OnClientConnectionRequest");
        else if (eventClass.equals(PlayerDownloadFileEvent.class))
            enableEvents("OnPlayerDownloadFile");
        //Client
        else if (eventClass.equals(SoundFinishedEvent.class))
            enableClientEvents("OnSoundFinished");
        else if (eventClass.equals(WebReadyEvent.class))
            enableClientEvents("OnWebLoadComplete");
        else if (eventClass.equals(PlayerCrouchStateEvent.class))
            enableClientEvents("OnPlayerCrouch", "OnPlayerEndCrouch");
        else if (eventClass.equals(PlayerFallStateEvent.class))
            enableClientEvents("OnPlayerFall", "OnPlayerEndFall");
        else if (eventClass.equals(PlayerSwimStateEvent.class))
            enableClientEvents("OnPlayerEnterWater", "OnPlayerLeaveWater");
        else if (eventClass.equals(PlayerSkydiveEvent.class))
            enableClientEvents("OnPlayerSkydive");
        else if (eventClass.equals(PlayerSkydiveEndEvent.class))
            enableClientEvents("OnPlayerCancelSkydive", "OnPlayerSkydiveCrash");
        else if (eventClass.equals(CollisionEnterEvent.class))
            enableClientEvents("OnCollisionEnter");
        else if (eventClass.equals(CollisionLeaveEvent.class))
            enableClientEvents("OnCollisionLeave");
        else if (eventClass.equals(ResolutionChangeEvent.class))
            enableClientEvents("OnResolutionChange");
        else if (eventClass.equals(PlayerReloadedEvent.class))
            enableClientEvents("OnPlayerReloaded");
        else if (eventClass.equals(PlayerParachuteLandEvent.class))
            enableClientEvents("OnPlayerParachuteLand");
        else if (eventClass.equals(PlayerParachuteStateEvent.class))
            enableClientEvents("OnPlayerParachuteOpen", "OnPlayerParachuteClose");
        else if (eventClass.equals(ObjectHitEvent.class))
            enableClientEvents("OnObjectHit");
        else if (eventClass.equals(PlayerBeginEditObjectEvent.class))
            enableClientEvents("OnPlayerBeginEditObject");
        else if (eventClass.equals(PlayerEndEditObjectEvent.class))
            enableClientEvents("OnPlayerEndEditObject");
        else if (eventClass.equals(LightStreamInEvent.class))
            enableClientEvents("OnLightStreamIn");
        else if (eventClass.equals(NPCStreamInEvent.class))
            enableClientEvents("OnNPCStreamIn");
        else if (eventClass.equals(ObjectStreamInEvent.class))
            enableClientEvents("OnObjectStreamIn");
        else if (eventClass.equals(PickupStreamInEvent.class))
            enableClientEvents("OnPickupStreamIn");
        else if (eventClass.equals(Text3DStreamInEvent.class))
            enableClientEvents("OnText3DStreamIn");
    }
}
