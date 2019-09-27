package net.onfirenetwork.onsetjava.simple.server;

import net.onfirenetwork.onsetjava.simple.OnsetServerAPI;

public class Player {

    private OnsetServerAPI api;
    private int id;
    private String name = null;
    private String steamId = null;

    public Player(OnsetServerAPI api, int id){
        this.api = api;
        this.id = id;
    }

    public String getSteamId(){
        if(steamId == null){
            steamId = api.call("GetPlayerSteamId", id).get()[0].getAsString();
        }
        return steamId;
    }

    public String getName(){
        if(name == null){
            name = api.call("GetPlayerName", id).get()[0].getAsString();
        }
        return name;
    }

    public void setName(String name){
        this.name = name;
        api.call("SetPlayerName", id, name);
    }

    public void kick(String message){
        api.call("KickPlayer", id, message);
    }

}
