package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

public interface PlayerVehicle {
    Player getPlayer();

    Vehicle getVehicle();

    Completable<Integer> getRPM();

    Completable<Integer> getForwardSpeed();

    Completable<String> getWheelSurface(int wheel);

    Completable<Double> getSteerAngle(int wheel);

    Completable<Boolean> isWheelInAir(int wheel);

    Completable<Boolean> isInAir();

    Completable<Boolean> isHornActive();

    Completable<Boolean> isInWater();

    Completable<Boolean> isSeatOccupied(int seat);
    @Deprecated
    Vector3d getDoorLocation(int door);
}
