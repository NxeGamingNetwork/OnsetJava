package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;

public interface Light {
    int getId();

    Dimension getDimension();

    void remove();

    void setDimension(Dimension dimension);
}
