package net.onfirenetwork.onsetjava.simple.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Light;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleLight implements Light {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    public void remove(){
        dimension.getServer().getLights().remove(this);
        dimension.getServer().call("DestroyLight", id).get();
    }
}
