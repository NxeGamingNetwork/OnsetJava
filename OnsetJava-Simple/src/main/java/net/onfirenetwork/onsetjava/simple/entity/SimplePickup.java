package net.onfirenetwork.onsetjava.simple.entity;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.entity.Pickup;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePickup implements Pickup {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    Map<String, Object> attributes = new HashMap<>();

    public SimplePickup(SimpleDimension dimension, int id) {
        this.dimension = dimension;
        this.id = id;
    }

    public void setScale(Vector3d scale) {
        dimension.getServer().call("SetPickupScale", id, scale.getX(), scale.getY(), scale.getZ());
    }

    public Vector3d getScale() {
        JsonElement[] scale = dimension.getServer().call("GetPickupScale", id).get();
        return new Vector3d(scale[0].getAsDouble(), scale[1].getAsDouble(), scale[2].getAsDouble());
    }

    public void remove() {
        dimension.getServer().getPickups().remove(this);
        dimension.getServer().call("DestroyPickup", id).get();
    }

    public void setDimension(Dimension dimension) {
        this.dimension.getServer().call("SetPickupDimension", id, dimension.getId());
        this.dimension = (SimpleDimension) dimension;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }
}
