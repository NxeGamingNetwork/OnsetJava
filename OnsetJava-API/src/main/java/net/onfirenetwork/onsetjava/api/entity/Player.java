package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.client.Sound;
import net.onfirenetwork.onsetjava.api.client.WebUI;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

import java.util.List;

public interface Player {
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
    WebUI createWebUI(Vector2d position, Vector2d size, int zOrder, int frameRate);
    WebUI createWebUI(Vector2d position, Vector2d size, int zOrder);
    WebUI createWebUI(Vector2d position, Vector2d size);
    WebUI create3DWebUI(Location location, Vector3d rotation, Vector2d size, int frameRate);
    WebUI create3DWebUI(Location location, Vector3d rotation, Vector2d size);
    Sound getSound(int id);
    List<Sound> getSounds();
    Sound createSound(String soundFile, boolean loop);
    Sound createSound(String soundFile);
    Sound create3DSound(Location location, String soundFile, double radius, boolean loop);
    Sound create3DSound(Location location, String soundFile, double radius);
    Sound create3DSound(Location location, String soundFile);
}
