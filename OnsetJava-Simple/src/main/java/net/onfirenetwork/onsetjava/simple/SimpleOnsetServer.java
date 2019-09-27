package net.onfirenetwork.onsetjava.simple;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Getter;
import net.onfirenetwork.onsetjava.api.OnsetServer;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.event.EventBus;
import net.onfirenetwork.onsetjava.simple.adapter.*;
import net.onfirenetwork.onsetjava.simple.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleOnsetServer implements OnsetServer {

    private int nextNonce = 1;
    private ActionAdapter adapter;
    private OnsetJavaSimple api;
    private Map<Integer, Completable<JsonElement[]>> returnFutures = new HashMap<>();
    private List<Player> players = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<NPC> npcs = new ArrayList<>();
    private List<WorldObject> objects = new ArrayList<>();
    private List<Text3D> text3ds = new ArrayList<>();
    private List<Pickup> pickups = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();
    @Getter
    private EventBus eventBus = new EventBus();
    private EventBus serializationBus = new EventBus();

    public SimpleOnsetServer(){
        adapter = new ActionAdapter(System.in, System.out, action -> {
            if(action.getType().equals("Return")){
                processReturn(action);
                return;
            }
            if(action.getType().equals("Command")){
                processCommand(action);
                return;
            }
            serializationBus.fire(new ActionEvent(action));
        });
    }

    public void start(){
        start(false);
    }

    public void start(boolean sync){
        if(sync){
            adapter.startSync();
        }else{
            adapter.start();
        }
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
        //TODO
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

    public void broadcast(String message){
        call("AddPlayerChatAll", message);
    }

    public void shutdown(){
        call("ServerExit", "");
    }

    public Player getPlayer(int id){
        return null;
    }
    public List<Player> getPlayers(){
        return new ArrayList<>();
    }

    public Vehicle getVehicle(int id){
        return null;
    }
    public List<Vehicle> getVehicles(){
        return new ArrayList<>();
    }

    public NPC getNPC(int id){
        return null;
    }
    public List<NPC> getNPCs(){
        return npcs;
    }

    public WorldObject getObject(int id){
        return null;
    }
    public List<WorldObject> getObjects(){
        return objects;
    }

    public Text3D getText3D(int id){
        return null;
    }
    public List<Text3D> getText3Ds(){
        return text3ds;
    }

    public Pickup getPickup(int id){
        return null;
    }
    public List<Pickup> getPickups(){
        return pickups;
    }

    public Light getLight(int id){
        return null;
    }
    public List<Light> getLights(){
        return lights;
    }

}
