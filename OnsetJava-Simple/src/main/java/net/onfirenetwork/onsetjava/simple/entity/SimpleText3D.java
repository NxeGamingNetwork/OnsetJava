package net.onfirenetwork.onsetjava.simple.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.entity.Text3D;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleText3D implements Text3D {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;

    public void remove() {
        dimension.getServer().getText3Ds().remove(this);
        dimension.getServer().call("DestroyText3D", id).get();
    }

    public void setDimension(Dimension dimension) {
        this.dimension.getText3Ds().remove(this);
        this.dimension.getServer().call("SetText3DDimension", id, dimension.getId());
        this.dimension = (SimpleDimension) dimension;
        this.dimension.getText3Ds().add(this);
    }
}
