package net.onfirenetwork.onsetjava.simple.entity;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.enums.VehicleModel;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleVehicle implements Vehicle {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    @Getter
    VehicleModel model;
    Map<String, Object> attributes = new HashMap<>();

    public SimpleVehicle(SimpleDimension dimension, int id, VehicleModel model) {
        this.dimension = dimension;
        this.id = id;
        this.model = model;
    }

    public void remove() {
        dimension.getServer().getVehicles().remove(this);
        dimension.getServer().call("DestroyVehicle", id).get();
    }

    public Location getLocation() {
        JsonElement[] loc = dimension.getServer().call("GetVehicleLocation", id).get();
        double heading = dimension.getServer().call("GetVehicleHeading", id).get()[0].getAsDouble();
        return new Location(loc[0].getAsDouble(), loc[1].getAsDouble(), loc[2].getAsDouble(), heading);
    }

    public void setLocation(Location location) {
        dimension.getServer().call("SetVehicleLocation", id, location.getX(), location.getY(), location.getZ()).get();
        dimension.getServer().call("SetVehicleHeading", id, location.getHeading()).get();
    }

    public Vector3d getRotation() {
        JsonElement[] rot = dimension.getServer().call("GetVehicleRotation", id).get();
        return new Vector3d(rot[0].getAsDouble(), rot[1].getAsDouble(), rot[2].getAsDouble());
    }

    public void setRotation(Vector3d rotation) {
        dimension.getServer().call("SetVehicleRotation", id, rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public double getHealth() {
        return dimension.getServer().call("GetVehicleHealth", id).get()[0].getAsDouble();
    }

    public void setHealth(double health) {
        dimension.getServer().call("SetVehicleHealth", id, health);
    }

    public void setLicensePlate(String licensePlate) {
        dimension.getServer().call("SetLicensePlate", id, licensePlate);
    }

    public void enterPlayer(Player player, int seat) {
        player.enterVehicle(this, seat);
    }

    public void enterPlayer(Player player) {
        player.enterVehicle(this);
    }

    public void setRespawnParams(boolean enabled, int time, boolean repairOnRespawn) {
        dimension.getServer().call("SetVehicleRespawnParams", id, enabled, time, repairOnRespawn);
    }

    public void setRespawnParams(boolean enabled) {
        dimension.getServer().call("SetVehicleRespawnParams", id, enabled);
    }

    public void setColor(int color) {
        dimension.getServer().call("SetVehicleColor", id, color);
    }

    public int getColor() {
        return dimension.getServer().call("GetVehicleColor", id).get()[0].getAsInt();
    }

    public int getInteriorColor() {
        return dimension.getServer().call("GetVehicleInteriorColor", id).get()[0].getAsInt();
    }

    public void setVelocity(Vector3d velocity, boolean reset) {
        dimension.getServer().call("SetVehicleLinearVelocity", id, velocity.getX(), velocity.getY(), velocity.getZ(), reset);
    }

    public void setAngularVelocity(Vector3d velocity, boolean reset) {
        dimension.getServer().call("SetVehicleAngularVelocity", id, velocity.getX(), velocity.getY(), velocity.getZ(), reset);
    }

    public int getGear() {
        return dimension.getServer().call("GetVehicleGear", id).get()[0].getAsInt();
    }

    public double getHood() {
        return dimension.getServer().call("GetVehicleHoodRatio", id).get()[0].getAsDouble();
    }

    public void setHood(double angle) {
        dimension.getServer().call("SetVehicleHoodRatio", id, angle);
    }

    public double getTrunk() {
        return dimension.getServer().call("GetVehicleTrunkRatio", id).get()[0].getAsDouble();
    }

    public void setTrunk(double angle) {
        dimension.getServer().call("SetVehicleTrunkRatio", id, angle);
    }

    public void startEngine() {
        dimension.getServer().call("StartVehicleEngine", id);
    }

    public void stopEngine() {
        dimension.getServer().call("StopVehicleEngine", id);
    }

    public boolean getEngine() {
        return dimension.getServer().call("GetVehicleEngineState", id).get()[0].getAsBoolean();
    }

    public void setLight(boolean state) {
        dimension.getServer().call("SetVehicleLightEnabled", id, state);
    }

    public boolean getLight() {
        return dimension.getServer().call("GetVehicleLightState", id).get()[0].getAsBoolean();
    }

    public int getLightColor() {
        return dimension.getServer().call("GetVehicleLightColor", id).get()[0].getAsInt();
    }

    public double getDamage(int index) {
        return dimension.getServer().call("GetVehicleDamage", id, index).get()[0].getAsDouble();
    }

    public void setDamage(int index, double damage) {
        dimension.getServer().call("SetVehicleDamage", id, index, damage);
    }

    public void setNitro(boolean nitro) {
        dimension.getServer().call("AttachVehicleNitro", id, nitro);
    }

    public Player getDriver() {
        JsonElement vid = dimension.getServer().call("GetVehicleDriver", id).get()[0];
        if (vid == null || vid.isJsonNull() || vid.getAsInt() == 0)
            return null;
        return dimension.getServer().getPlayer(vid.getAsInt());
    }

    public Player getPassenger(int seat) {
        JsonElement vid = dimension.getServer().call("GetVehiclePassenger", id, seat).get()[0];
        if (vid == null || vid.isJsonNull() || vid.getAsInt() == 0)
            return null;
        return dimension.getServer().getPlayer(vid.getAsInt());
    }

    public int getSeatCount() {
        return dimension.getServer().call("GetVehicleNumberOfSeats", id).get()[0].getAsInt();
    }

    public void setDimension(Dimension dimension) {
        this.dimension.getVehicles().remove(this);
        this.dimension.getServer().call("SetVehicleDimension", id, dimension.getId());
        this.dimension = (SimpleDimension) dimension;
        this.dimension.getVehicles().add(this);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }
}
