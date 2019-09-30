package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.enums.AttachType;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

public interface Text3D {
    int getId();

    Dimension getDimension();

    void remove();

    void setDimension(Dimension dimension);

    void setAttached(AttachType attachType, int attachId, Vector3d location, Vector3d rotation, String socketName);

    default void setAttached(AttachType attachType, int attachId, Vector3d location) {
        setAttached(attachType, attachId, location, null, null);
    }
}
