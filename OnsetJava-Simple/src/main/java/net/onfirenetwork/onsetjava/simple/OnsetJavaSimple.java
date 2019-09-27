package net.onfirenetwork.onsetjava.simple;

import com.google.gson.Gson;
import lombok.Getter;

import java.io.InputStream;
import java.io.OutputStream;

public class OnsetJavaSimple {

    public static void main(String [] args){
        OnsetJavaSimple api = new OnsetJavaSimple(System.in, System.out);
        api.start(false);
    }

    private ActionAdapter adapter;
    @Getter
    private OnsetServerAPI serverAPI;
    private int nextNonce = 1;

    public OnsetJavaSimple(InputStream inputStream, OutputStream outputStream){
        adapter = new ActionAdapter(inputStream, outputStream, action -> {
            if(action.getType().equals("Return")){
                serverAPI.processReturn(action);
            }
        });
        serverAPI = new OnsetServerAPI(this);
    }

    public int nonce(){
        int nonce = nextNonce;
        nextNonce++;
        return nonce;
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

    public void call(String type, int nonce, Object... params){
        adapter.call(new OutboundAction(type, nonce, params));
    }

    public void callClient(int playerId, String type, int nonce, Object... params){
        adapter.call(new OutboundAction("Forward", 0, new Gson().toJson(new OutboundAction(type, nonce, params))));
    }

}
