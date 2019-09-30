package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

public interface PlayerVehicle {
    Player getPlayer();

    Vehicle getVehicle();

    int getRPM();

    int getForwardSpeed();

    String getWheelSurface(int wheel);

    double getSteerAngle(int wheel);

    boolean isWheelInAir(int wheel);

    boolean isInAir();

    boolean isHornActive();

    boolean isInWater();

    boolean isSeatOccupied(int seat);

    Vector3d getDoorLocation(int door);
}
