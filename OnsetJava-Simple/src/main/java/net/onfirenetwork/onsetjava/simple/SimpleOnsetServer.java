package net.onfirenetwork.onsetjava.simple;

import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Getter;
import net.onfirenetwork.onsetjava.api.CommandExecutor;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.OnsetServer;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.EventBus;
import net.onfirenetwork.onsetjava.api.event.server.PlayerJoinEvent;
import net.onfirenetwork.onsetjava.simple.adapter.*;
import net.onfirenetwork.onsetjava.simple.event.ClientEventTransformer;
import net.onfirenetwork.onsetjava.simple.event.ServerEventTransformer;

import java.util.*;

public class SimpleOnsetServer implements OnsetServer {

    private int nextNonce = 1;
    private int nextDimId = 0;
    private ActionAdapter adapter;
    private OnsetJavaSimple api;
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
    private EventBus eventBus = new EventBus();
    private Map<String, CommandExecutor> commandMap = new HashMap<>();

    public SimpleOnsetServer(){
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
                if(event != null){
                    eventBus.fire(event);
                    return;
                }
            }
            public void onClientAction(Player player, InboundAction action) {
                Event event = clientEventTransformer.transform(player, action);
                if(event != null){
                    eventBus.fire(event);
                    return;
                }
            }
        });
    }

    public void run(){
        adapter.prepare();
        createDimension();
        enableEvents("OnPlayerJoin", "OnPlayerQuit", "OnPlayerServerAuth");
        eventBus.listen(PlayerJoinEvent.class, event -> {
            String name = event.getPlayer().getName();
            event.getPlayer().sendMessage("Herzlich Willkommen, "+name+"!");
        });
        registerCommand("julian", (command, sender, args) -> {
            sender.sendMessage("Hallo Julian!");
        });
        adapter.startSync();
    }

    public void stop(){
        adapter.stop();
    }

    public int nonce(){
        int nonce = nextNonce;
        nextNonce++;
        return nonce;
    }

    public void processReturn(InboundAction action){
        if(returnFutures.containsKey(action.getNonce())){
            Completable<JsonElement[]> future = returnFutures.get(action.getNonce());
            returnFutures.remove(action.getNonce());
            future.complete(action.getParams());
        }
    }

    public void processCommand(InboundAction action){
        String command = action.getParams()[0].getAsString().toLowerCase(Locale.GERMAN);
        if(commandMap.containsKey(command)){
            String[] args = new String[action.getParams().length-2];
            for(int i=0; i<args.length; i++){
                args[i] = action.getParams()[i+2].getAsString();
            }
            commandMap.get(command).onCommand(command, getPlayer(action.getParams()[1].getAsInt()), args);
        }
    }

    public void registerCommand(String name, CommandExecutor executor){
        commandMap.put(name, executor);
        callAction("RegisterCommand", 0, name);
    }

    public void enableEvents(String... eventNames){
        callAction("RegisterEvents", 0, (Object) eventNames);
    }

    public void callAction(String type, int nonce, Object... params){
        adapter.call(new OutboundAction(type, nonce, params));
    }

    public void callClientAction(String type, int nonce, Object... params){
        adapter.call(new OutboundAction("Forward", 0, new Gson().toJson(new OutboundAction(type, nonce, params))));
    }

    public Completable<JsonElement[]> call(String name, Object... params){
        int nonce = nonce();
        Completable<JsonElement[]> future = new Completable<>();
        returnFutures.put(nonce, future);
        Object[] p = new Object[params.length+1];
        p[0] = name;
        for(int i=0; i<params.length; i++){
            p[i+1] = params[i];
        }
        callAction("Call", nonce, p);
        return future;
    }

    public Dimension createDimension(){
        int dimId = nextDimId;
        nextDimId++;
        Dimension dimension = new SimpleDimension(this, dimId);
        dimensions.add(dimension);
        return dimension;
    }

    public Dimension getDimension(int id){
        for(Dimension dimension : dimensions){
            if(dimension.getId() == id){
                return dimension;
            }
        }
        return null;
    }

    public void broadcast(String message){
        call("AddPlayerChatAll", message);
    }

    public void shutdown(){
        call("ServerExit", "");
    }

    public Player getPlayer(int id){
        for(Player player : players){
            if(player.getId() == id){
                return player;
            }
        }
        return null;
    }

    public Vehicle getVehicle(int id){
        for(Vehicle vehicle : vehicles){
            if(vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null;
    }

    public List<NPC> getNPCs(){
        return npcs;
    }
    public NPC getNPC(int id){
        return null;
    }

    public WorldObject getObject(int id){
        return null;
    }

    public List<Text3D> getText3Ds(){
        return text3ds;
    }
    public Text3D getText3D(int id){
        return null;
    }

    public Pickup getPickup(int id){
        return null;
    }

    public Light getLight(int id){
        return null;
    }

}
