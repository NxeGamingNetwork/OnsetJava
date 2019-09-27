package net.onfirenetwork.onsetjava.simple.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Pickup;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePickup implements Pickup {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    public void remove(){
        dimension.getServer().getPickups().remove(this);
        dimension.getServer().call("DestroyPickup", id).get();
    }
}
