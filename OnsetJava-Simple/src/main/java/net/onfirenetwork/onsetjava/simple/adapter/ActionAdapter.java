package net.onfirenetwork.onsetjava.simple.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.onfirenetwork.onsetjava.api.OnsetJava;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ActionAdapter {

    private boolean shouldExit = true;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ActionAdapterListener listener;
    private Gson gson = new GsonBuilder().create();

    public ActionAdapter(InputStream inputStream, OutputStream outputStream, ActionAdapterListener listener){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.listener = listener;
    }

    public void prepare(){
        shouldExit = false;
    }

    public void startSync(){
        while (!shouldExit){
            try {
                while(inputStream.available() < 1){
                    if(shouldExit)
                        break;
                    Thread.yield();
                }
                if(!shouldExit){
                    StringBuilder sb = new StringBuilder();
                    while(inputStream.available() > 0){
                        byte[] data = new byte[Math.min(1024, inputStream.available())];
                        inputStream.read(data);
                        sb.append(new String(data, StandardCharsets.UTF_8));
                    }
                    String[] lines = sb.toString().split("\n");
                    for(String line : lines){
                        if(line.length() == 0)
                            continue;
                        try {
                            InboundAction action = gson.fromJson(line, InboundAction.class);
                            if(action.getType().equals("Forward")){
                                int playerId = action.getParams()[0].getAsInt();
                                String json = action.getParams()[1].getAsString();
                                InboundAction clientAction = gson.fromJson(json, InboundAction.class);
                                new Thread(() -> {
                                    listener.onClientAction(OnsetJava.getServer().getPlayer(playerId), clientAction);
                                });
                            }else{
                                new Thread(() -> {
                                    listener.onAction(action);
                                }).start();
                            }
                        }catch (Exception ex){}
                    }
                }
            }catch (Exception ex){ ex.printStackTrace(); }
        }

    }

    public void start(){
        new Thread(this::startSync).start();
    }

    public void stop(){
        shouldExit = true;
    }

    public void call(OutboundAction action){
        if(shouldExit)
            return;
        try {
            String json = gson.toJson(action)+"\n";
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
