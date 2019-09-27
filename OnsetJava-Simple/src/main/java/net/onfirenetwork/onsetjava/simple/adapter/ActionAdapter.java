package net.onfirenetwork.onsetjava.simple.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public void startSync(){
        shouldExit = false;
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
                            listener.onAction(gson.fromJson(line, InboundAction.class));
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
