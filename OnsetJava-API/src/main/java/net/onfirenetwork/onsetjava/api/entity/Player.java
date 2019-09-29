package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.client.Sound;
import net.onfirenetwork.onsetjava.api.client.WebUI;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

import java.util.List;

public interface Player extends HitEntity {
    int getId();
    Dimension getDimension();
    void setDimension(Dimension dimension);
    String getSteamId();
    String getName();
    void setName(String name);
    double getHealth();
    void setHealth(double health);
    double getArmor();
    void setArmor(double armor);
    Location getLocation();
    void setLocation(Location location);
    void exitVehicle();
    void enterVehicle(Vehicle vehicle, int seat);
    void enterVehicle(Vehicle vehicle);
    void setVoiceEnabled(boolean enable);
    boolean isVoiceEnabled();
    void sendMessage(String message);
    void setSpawnLocation(Location location);
    Vehicle getVehicle();
    void kick(String message);
    void registerKeys(String... keys);
    WebUI getWebUI(int id);
    List<WebUI> getWebUIs();
    Completable<WebUI> createWebUI(Vector2d position, Vector2d size, int zOrder, int frameRate);
    default Completable<WebUI> createWebUI(Vector2d position, Vector2d size, int zOrder){
        return createWebUI(position, size, zOrder, 16);
    }
    default Completable<WebUI> createWebUI(Vector2d position, Vector2d size){
        return createWebUI(position, size, 0);
    }
    Completable<WebUI> create3DWebUI(Location location, Vector3d rotation, Vector2d size, int frameRate);
    default Completable<WebUI> create3DWebUI(Location location, Vector3d rotation, Vector2d size){
        return create3DWebUI(location, rotation, size, 16);
    }
    Sound getSound(int id);
    List<Sound> getSounds();
    Completable<Sound> createSound(String soundFile, boolean loop);
    default Completable<Sound> createSound(String soundFile){
        return createSound(soundFile, false);
    }
    Completable<Sound> create3DSound(Location location, String soundFile, double radius, boolean loop);
    default Completable<Sound> create3DSound(Location location, String soundFile, double radius){
        return create3DSound(location, soundFile, radius, false);
    }
    default Completable<Sound> create3DSound(Location location, String soundFile){
        return create3DSound(location, soundFile, 2500);
    }
}
