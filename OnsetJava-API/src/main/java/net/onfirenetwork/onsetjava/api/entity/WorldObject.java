package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;

public interface WorldObject extends HitEntity {
    int getId();
    Dimension getDimension();
    void remove();
}
