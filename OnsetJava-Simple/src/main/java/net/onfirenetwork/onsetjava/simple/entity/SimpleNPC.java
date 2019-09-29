package net.onfirenetwork.onsetjava.simple.entity;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.entity.NPC;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.enums.CharacterAnimation;
import net.onfirenetwork.onsetjava.api.enums.CharacterModel;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleNPC implements NPC {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    @Getter
    CharacterModel model;
    Map<String, Object> attributes = new HashMap<>();

    public SimpleNPC(SimpleDimension dimension, int id, CharacterModel model) {
        this.dimension = dimension;
        this.id = id;
        this.model = model;
    }

    public Location getLocation() {
        JsonElement[] ret = dimension.getServer().call("GetNPCLocation", id).get();
        double heading = dimension.getServer().call("GetNPCHeading", id).get()[0].getAsDouble();
        return new Location(ret[0].getAsDouble(), ret[1].getAsDouble(), ret[2].getAsDouble(), heading);
    }

    public void setLocation(Location location) {
        dimension.getServer().call("SetNPCLocation", id, location.getX(), location.getY(), location.getZ());
        dimension.getServer().call("SetNPCHeading", id, location.getHeading());
    }

    public void setHealth(double health) {
        dimension.getServer().call("SetNPCHealth", id, health);
    }

    public double getHealth() {
        return dimension.getServer().call("GetNPCHealth", id).get()[0].getAsDouble();
    }

    public void startAnimation(CharacterAnimation animation, boolean loop) {
        dimension.getServer().call("SetNPCAnimation", id, animation.name(), loop);
    }

    public void stopAnimation() {
        dimension.getServer().call("SetNPCAnimation", id, "STOP", false);
    }

    public void setTargetLocation(Location location, double speed) {
        dimension.getServer().call("SetNPCTargetLocation", id, location.getX(), location.getY(), location.getZ(), speed);
    }

    public void setTargetLocation(Location location) {
        dimension.getServer().call("SetNPCTargetLocation", id, location.getX(), location.getY(), location.getZ());
    }

    public void followPlayer(Player player, double speed) {
        dimension.getServer().call("SetNPCFollowPlayer", id, player.getId(), speed);
    }

    public void followPlayer(Player player) {
        dimension.getServer().call("SetNPCFollowPlayer", id, player.getId());
    }

    public void followVehicle(Vehicle vehicle, double speed) {
        dimension.getServer().call("SetNPCFollowPlayer", id, vehicle.getId(), speed);
    }

    public void followVehicle(Vehicle vehicle) {
        dimension.getServer().call("SetNPCFollowPlayer", id, vehicle.getId());
    }

    public void remove() {
        dimension.getServer().getNPCs().remove(this);
        dimension.getServer().call("DestroyNPC", id).get();
    }

    public void setDimension(Dimension dimension) {
        this.dimension.getNPCs().remove(this);
        this.dimension.getServer().call("SetNPCDimension", id, dimension.getId());
        this.dimension = (SimpleDimension) dimension;
        this.dimension.getNPCs().add(this);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }
}
