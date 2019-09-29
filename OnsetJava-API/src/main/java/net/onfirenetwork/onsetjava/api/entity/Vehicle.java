package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.enums.VehicleModel;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

public interface Vehicle extends HitEntity {
    int getId();

    Dimension getDimension();

    void remove();

    double getHealth();

    void setHealth(double health);

    Location getLocation();

    void setLocation(Location location);

    Vector3d getRotation();

    void setRotation(Vector3d rotation);

    void setLicensePlate(String licensePlate);

    void enterPlayer(Player player, int seat);

    void enterPlayer(Player player);

    VehicleModel getModel();

    void setRespawnParams(boolean enabled, int time, boolean repairOnRespawn);

    default void setRespawnParams(boolean enabled, int time) {
        setRespawnParams(enabled, time, true);
    }

    void setRespawnParams(boolean enabled);

    void setColor(int color);

    int getColor();

    int getInteriorColor();

    void setVelocity(Vector3d velocity, boolean reset);

    default void setVelocity(Vector3d velocity) {
        setVelocity(velocity, false);
    }

    void setAngularVelocity(Vector3d velocity, boolean reset);

    default void setAngularVelocity(Vector3d velocity) {
        setAngularVelocity(velocity, false);
    }

    int getGear();

    double getHood();

    void setHood(double angle);

    double getTrunk();

    void setTrunk(double angle);

    void startEngine();

    void stopEngine();

    default void setEngine(boolean state) {
        if (state) {
            startEngine();
        } else {
            stopEngine();
        }
    }

    boolean getEngine();

    void setLight(boolean state);

    boolean getLight();

    int getLightColor();

    double getDamage(int index);

    void setDamage(int index, double damage);

    void setNitro(boolean nitro);

    Player getDriver();

    Player getPassenger(int seat);

    int getSeatCount();

    void setDimension(Dimension dimension);
}
