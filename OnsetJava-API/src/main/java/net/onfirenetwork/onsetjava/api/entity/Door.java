package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.enums.DoorModel;
import net.onfirenetwork.onsetjava.api.util.Location;

public interface Door {

    int getId();

    Dimension getDimension();

    void setOpen(boolean open);

    boolean isOpen();

    DoorModel getModel();

    Location getLocation();

    void setLocation(Location location);

    void remove();

}
