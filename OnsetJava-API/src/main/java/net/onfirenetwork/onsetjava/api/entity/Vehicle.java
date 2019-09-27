package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.util.Location;

public interface Vehicle {
    int getId();
    Dimension getDimension();
    void remove();
    double getHealth();
    void setHealth(double health);
    Location getLocation();
    void setLocation(Location location);
    void setLicensePlate(String licensePlate);
    void enterPlayer(Player player, int seat);
    void enterPlayer(Player player);
}
