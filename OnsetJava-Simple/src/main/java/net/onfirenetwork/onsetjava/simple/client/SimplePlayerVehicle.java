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

    public Completable<Integer> getRPM() {
        Completable<Integer> completable = new Completable<>();
        call("GetVehicleEngineRPM", vehicle.getId()).then(ret -> {
            completable.complete(ret[0].getAsInt());
        });
        return completable;
    }

    public Completable<Integer> getForwardSpeed() {
        Completable<Integer> completable = new Completable<>();
        call("GetVehicleForwardSpeed", vehicle.getId()).then(ret -> {
            completable.complete(ret[0].getAsInt());
        });
        return completable;
    }

    public Completable<String> getWheelSurface(int wheel) {
        Completable<String> completable = new Completable<>();
        call("GetVehicleWheelSurface", vehicle.getId(), wheel).then(ret -> {
            completable.complete(ret[0].getAsString());
        });
        return completable;
    }

    public Completable<Double> getSteerAngle(int wheel) {
        Completable<Double> completable = new Completable<>();
        call("GetVehicleWheelSteerAngle", vehicle.getId(), wheel).then(ret -> {
            completable.complete(ret[0].getAsDouble());
        });
        return completable;
    }

    public Completable<Boolean> isWheelInAir(int wheel) {
        Completable<Boolean> completable = new Completable<>();
        call("IsVehicleWheelInAir", vehicle.getId(), wheel).then(ret -> {
            completable.complete(ret[0].getAsBoolean());
        });
        return completable;
    }

    public Completable<Boolean> isInAir() {
        Completable<Boolean> completable = new Completable<>();
        call("IsVehicleInAir", vehicle.getId()).then(ret -> {
            completable.complete(ret[0].getAsBoolean());
        });
        return completable;
    }

    public Completable<Boolean> isHornActive() {
        Completable<Boolean> completable = new Completable<>();
        call("IsVehicleHornActive", vehicle.getId()).then(ret -> {
            completable.complete(ret[0].getAsBoolean());
        });
        return completable;
    }

    public Completable<Boolean> isInWater() {
        Completable<Boolean> completable = new Completable<>();
        call("IsVehicleInWater", vehicle.getId()).then(ret -> {
            completable.complete(ret[0].getAsBoolean());
        });
        return completable;
    }

    public Completable<Boolean> isSeatOccupied(int seat) {
        Completable<Boolean> completable = new Completable<>();
        call("IsVehicleSeatOccupied", vehicle.getId(), seat).then(ret -> {
            completable.complete(ret[0].getAsBoolean());
        });
        return completable;
    }

    public Vector3d getDoorLocation(int door) {
        return JsonUtils.toVector(call("GetVehicleDoorLocation", vehicle.getId()).get());
    }

    private Completable<JsonElement[]> call(String name, Object... params) {
        return player.getDimension().getServer().callClient(player.getId(), name, params);
    }
}
