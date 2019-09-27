package net.onfirenetwork.onsetjava.simple.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.WorldObject;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleWorldObject implements WorldObject {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    public void remove(){
        dimension.getServer().getObjects().remove(this);
        dimension.getServer().call("DestroyObject", id).get();
    }
}
