package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.util.Location;

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
}
