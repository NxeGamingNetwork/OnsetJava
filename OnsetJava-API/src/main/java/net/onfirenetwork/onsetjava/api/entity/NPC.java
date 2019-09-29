package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;

public interface NPC extends HitEntity {
    int getId();
    Dimension getDimension();
    void remove();
}
