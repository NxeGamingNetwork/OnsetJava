package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;

public interface Pickup {
    int getId();
    Dimension getDimension();
    void remove();
}
