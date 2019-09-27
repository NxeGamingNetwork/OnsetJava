package net.onfirenetwork.onsetjava.simple;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

public class OnsetServerAPI {

    private OnsetJavaSimple api;
    private Map<Integer, Completable<JsonElement[]>> returnFutures = new HashMap<>();

    public OnsetServerAPI(OnsetJavaSimple api){
        this.api = api;
    }

    public void processReturn(InboundAction action){
        if(returnFutures.containsKey(action.getNonce())){
            Completable<JsonElement[]> future = returnFutures.get(action.getNonce());
            returnFutures.remove(action.getNonce());
            future.complete(action.getParams());
        }
    }

    public void register(String... eventNames){
        api.call("Register", 0, (Object) eventNames);
    }

    public Completable<JsonElement[]> call(String name, Object... params){
        int nonce = api.nonce();
        Completable<JsonElement[]> future = new Completable<>();
        returnFutures.put(nonce, future);
        Object[] p = new Object[params.length+1];
        p[0] = name;
        for(int i=0; i<params.length; i++){
            p[i+1] = params[i];
        }
        api.call("Call", nonce, p);
        return future;
    }

    public void broadcast(String message){
        call("AddPlayerChatAll", message);
    }

}
