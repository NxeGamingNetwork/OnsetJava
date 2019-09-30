package net.onfirenetwork.onsetjava.simple.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.entity.Text3D;
import net.onfirenetwork.onsetjava.api.enums.AttachType;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
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
        this.dimension.getServer().call("SetText3DDimension", id, dimension.getId());
        this.dimension = (SimpleDimension) dimension;
    }

    public void setAttached(AttachType attachType, int attachId, Vector3d location, Vector3d rotation, String socketName) {
        if (rotation != null) {
            dimension.getServer().call("SetText3DAttached", id, attachId, location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY(), rotation.getZ(), socketName);
        } else {
            dimension.getServer().call("SetText3DAttached", id, attachId, location.getX(), location.getY(), location.getZ());
        }
    }
}
