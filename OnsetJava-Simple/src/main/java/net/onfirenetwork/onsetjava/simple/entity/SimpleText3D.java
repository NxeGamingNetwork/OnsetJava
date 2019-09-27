package net.onfirenetwork.onsetjava.simple.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Text3D;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleText3D implements Text3D {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    public void remove(){
        dimension.getServer().getText3Ds().remove(this);
        dimension.getServer().call("DestroyText3D", id).get();
    }
}
