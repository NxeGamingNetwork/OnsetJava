package net.onfirenetwork.onsetjava.simple.client;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.client.PlayerVehicle;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;
import net.onfirenetwork.onsetjava.simple.util.JsonUtils;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePlayerVehicle implements PlayerVehicle {
    @Getter
    SimplePlayer player;
    @Getter
    Vehicle vehicle;

    public SimplePlayerVehicle(SimplePlayer player, Vehicle vehicle) {
        this.player = player;
        this.vehicle = vehicle;
    }

    public int getRPM() {
        return call("GetVehicleEngineRPM", vehicle.getId()).get()[0].getAsInt();
    }

    public int getForwardSpeed() {
        return call("GetVehicleForwardSpeed", vehicle.getId()).get()[0].getAsInt();
    }

    public String getWheelSurface(int wheel) {
        return call("GetVehicleWheelSurface", vehicle.getId(), wheel).get()[0].getAsString();
    }

    public double getSteerAngle(int wheel) {
        return call("GetVehicleWheelSteerAngle", vehicle.getId(), wheel).get()[0].getAsDouble();
    }

    public boolean isWheelInAir(int wheel) {
        return call("IsVehicleWheelInAir", vehicle.getId(), wheel).get()[0].getAsBoolean();
    }

    public boolean isInAir() {
        return call("IsVehicleInAir", vehicle.getId()).get()[0].getAsBoolean();
    }

    public boolean isHornActive() {
        return call("IsVehicleHornActive", vehicle.getId()).get()[0].getAsBoolean();
    }

    public boolean isInWater() {
        return call("IsVehicleInWater", vehicle.getId()).get()[0].getAsBoolean();
    }

    public boolean isSeatOccupied(int seat) {
        return call("IsVehicleSeatOccupied", vehicle.getId(), seat).get()[0].getAsBoolean();
    }

    public Vector3d getDoorLocation(int door) {
        return JsonUtils.toVector(call("GetVehicleDoorLocation", vehicle.getId()).get());
    }

    private Completable<JsonElement[]> call(String name, Object... params) {
        return player.getDimension().getServer().callClient(player.getId(), name, params);
    }
}
