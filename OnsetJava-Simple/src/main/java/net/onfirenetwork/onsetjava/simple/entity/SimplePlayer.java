package net.onfirenetwork.onsetjava.simple.entity;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.client.Sound;
import net.onfirenetwork.onsetjava.api.client.WebUI;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;
import net.onfirenetwork.onsetjava.simple.client.SimpleSound;
import net.onfirenetwork.onsetjava.simple.client.SimpleWebUI;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePlayer implements Player {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    String steamId;
    String name;
    @Getter
    boolean voiceEnabled = true;
    List<WebUI> webuis = new ArrayList<>();
    List<Sound> sounds = new ArrayList<>();

    public SimplePlayer(Dimension dimension, int id){
        this.dimension = (SimpleDimension) dimension;
        this.id = id;
    }
    public void setDimension(Dimension dimension){
        this.dimension = (SimpleDimension) dimension;
        this.dimension.getServer().call("SetPlayerDimension", id, dimension.getId()).get();
    }
    public String getSteamId(){
        if(steamId == null){
            steamId = dimension.getServer().call("GetPlayerSteamId", id).get()[0].getAsString();
        }
        return steamId;
    }
    public String getName(){
        if(name == null){
            name = dimension.getServer().call("GetPlayerName", id).get()[0].getAsString();
        }
        return name;
    }
    public void setName(String name){
        this.name = name;
        dimension.getServer().call("SetPlayerName", id, name);
    }
    public Location getLocation(){
        JsonElement[] loc = dimension.getServer().call("GetPlayerLocation", id).get();
        double heading = dimension.getServer().call("GetPlayerHeading", id).get()[0].getAsDouble();
        return new Location(loc[0].getAsDouble(), loc[1].getAsDouble(), loc[2].getAsDouble(), heading);
    }
    public void setLocation(Location location){
        dimension.getServer().call("SetPlayerLocation", id, location.getX(), location.getY(), location.getZ()).get();
        dimension.getServer().call("SetPlayerHeading", id, location.getHeading()).get();
    }
    public void setSpawnLocation(Location location){
        dimension.getServer().call("SetPlayerSpawnLocation", id, location.getX(), location.getY(), location.getZ(), location.getHeading()).get();
    }
    public double getHealth(){
        return dimension.getServer().call("GetPlayerHealth", id).get()[0].getAsDouble();
    }
    public void setHealth(double health){
        dimension.getServer().call("SetPlayerHealth", id, health);
    }
    public double getArmor(){
        return dimension.getServer().call("GetPlayerArmor", id).get()[0].getAsDouble();
    }
    public void setArmor(double armor){
        dimension.getServer().call("SetPlayerArmor", id, armor);
    }
    public void exitVehicle(){
        dimension.getServer().call("RemovePlayerFromVehicle", id).get();
    }
    public void enterVehicle(Vehicle vehicle){
        dimension.getServer().call("SetPlayerInVehicle", id, vehicle.getId()).get();
    }
    public void enterVehicle(Vehicle vehicle, int seat){
        dimension.getServer().call("SetPlayerInVehicle", id, vehicle.getId(), seat).get();
    }
    public void setVoiceEnabled(boolean enable){
        voiceEnabled = enable;
        dimension.getServer().call("SetPlayerVoiceEnabled", id, enable);
    }
    public void sendMessage(String message){
        dimension.getServer().call("AddPlayerChat", id, message);
    }
    public Vehicle getVehicle(){
        JsonElement vid = dimension.getServer().call("GetPlayerVehicle", id).get()[0];
        if(vid == null || vid.isJsonNull() || vid.getAsInt() == 0)
            return null;
        return dimension.getServer().getVehicle(vid.getAsInt());
    }
    public void kick(String message){
        dimension.getServer().call("KickPlayer", id, message);
    }
    public void registerKeys(String... keys){
        dimension.getServer().callClientAction(id, "RegisterKeys", 0, (Object) keys);
    }
    public WebUI getWebUI(int id){
        return webuis.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }
    public List<WebUI> getWebUIs(){
        return webuis;
    }
    public Completable<WebUI> createWebUI(Vector2d position, Vector2d size, int zOrder, int frameRate){
        Completable<WebUI> completable = new Completable<>();
        dimension.getServer().callClient(id, "CreateWebUI",position.getX(), position.getY(), size.getX(), size.getY(), zOrder, frameRate).then(ret -> {
            WebUI ui = new SimpleWebUI(this, ret[0].getAsInt());
            webuis.add(ui);
            completable.complete(ui);
        });
        return completable;
    }
    public Completable<WebUI> create3DWebUI(Location location, Vector3d rotation, Vector2d size, int frameRate){
        Completable<WebUI> completable = new Completable<>();
        dimension.getServer().callClient(id, "CreateWebUI3D", location.getX(), location.getY(), location.getY(), rotation.getX(), rotation.getY(), rotation.getZ(), size.getX(), size.getY(), frameRate).then(ret -> {
            WebUI ui = new SimpleWebUI(this, ret[0].getAsInt());
            webuis.add(ui);
            completable.complete(ui);
        });
        return completable;
    }
    public Sound getSound(int id){
        return sounds.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }
    public List<Sound> getSounds(){
        return sounds;
    }
    public Completable<Sound> createSound(String soundFile, boolean loop){
        Completable<Sound> completable = new Completable<>();
        dimension.getServer().callClient(id, "CreateSound", soundFile, loop).then(ret -> {
            Sound sound = new SimpleSound(this, ret[0].getAsInt());
            sounds.add(sound);
            completable.complete(sound);
        });
        return completable;
    }
    public Completable<Sound> create3DSound(Location location, String soundFile, double radius, boolean loop){
        Completable<Sound> completable = new Completable<>();
        dimension.getServer().callClient(id, "CreateSound3D", soundFile, location.getX(), location.getY(), location.getZ(), radius, loop).then(ret -> {
            Sound sound = new SimpleSound(this, ret[0].getAsInt());
            sounds.add(sound);
            completable.complete(sound);
        });
        return completable;
    }
}
