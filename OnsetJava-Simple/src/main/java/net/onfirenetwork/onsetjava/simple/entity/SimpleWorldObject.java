package net.onfirenetwork.onsetjava.simple.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.entity.WorldObject;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;
import net.onfirenetwork.onsetjava.simple.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleWorldObject implements WorldObject {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    @Getter
    int model;
    Map<String, Object> attributes = new HashMap<>();

    public SimpleWorldObject(SimpleDimension dimension, int id, int model){
        this.dimension = dimension;
        this.id = id;
        this.model = model;
    }

    public void setStreamDistance(double distance) {
        dimension.getServer().call("SetObjectStreamDistance", id, distance);
    }

    public void setLocation(Location location) {
        dimension.getServer().call("SetObjectLocation", id, location.getX(), location.getY(), location.getZ());
    }

    public Location getLocation() {
        return JsonUtils.toVector3i(dimension.getServer().call("GetObjectLocation", id).get()).toLocation();
    }

    public void setRotation(Vector3d rotation) {
        dimension.getServer().call("SetObjectRotation", id, rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public Vector3d getRotation() {
        return JsonUtils.toVector(dimension.getServer().call("GetObjectRotation", id).get());
    }

    public void setScale(Vector3d scale) {
        dimension.getServer().call("SetObjectScale", id, scale.getX(), scale.getY(), scale.getZ());
    }

    public Vector3d getScale() {
        return JsonUtils.toVector(dimension.getServer().call("GetObjectScale", id).get());
    }

    public boolean isMoving() {
        return dimension.getServer().call("IsObjectMoving", id).get()[0].getAsBoolean();
    }

    public void startMoving(Location location, double speed) {
        dimension.getServer().call("SetObjectMoveTo", id, location.getX(), location.getY(), location.getZ(), speed);
    }

    public void startMoving(Location location) {
        dimension.getServer().call("SetObjectMoveTo", id, location.getX(), location.getY(), location.getZ());
    }

    public void stopMoving() {
        dimension.getServer().call("StopObjectMove", id);
    }

    public void setColor(int color) {
        dimension.getServer().call("SetObjectColor", id, color);
    }

    public void setTexture(String file, int slot) {
        dimension.getServer().call("SetObjectTexture", id, file, slot);
    }

    public void setTexture(String file) {
        dimension.getServer().call("SetObjectTexture", id, file);
    }

    public void setAnimatedTexture(String file, int rows, int columns, int slot) {
        dimension.getServer().call("SetObjectAnimatedTexture", id, file, rows, columns, slot);
    }

    public void setAnimatedTexture(String file, int rows, int columns) {
        dimension.getServer().call("SetObjectAnimatedTexture", id, file, rows, columns);
    }

    public void remove() {
        dimension.getServer().getObjects().remove(this);
        dimension.getServer().call("DestroyObject", id).get();
    }

    public void setDimension(Dimension dimension) {
        this.dimension.getServer().call("SetObjectDimension", id, dimension.getId());
        this.dimension = (SimpleDimension) dimension;
    }

    public boolean isAttached() {
        return dimension.getServer().call("IsObjectAttached", id).get()[0].getAsBoolean();
    }

    public void setRotateAxis(Vector3d rotateAxis) {
        dimension.getServer().call("SetObjectRotateAxis", id, rotateAxis.getX(), rotateAxis.getY(), rotateAxis.getZ());
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }
}
