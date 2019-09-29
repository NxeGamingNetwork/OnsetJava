package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

public interface Pickup {
    int getId();

    Dimension getDimension();

    void setScale(Vector3d scale);

    Vector3d getScale();

    void remove();

    void setDimension(Dimension dimension);
}
