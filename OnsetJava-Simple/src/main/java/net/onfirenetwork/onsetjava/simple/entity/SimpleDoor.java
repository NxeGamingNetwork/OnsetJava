package net.onfirenetwork.onsetjava.simple.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Door;
import net.onfirenetwork.onsetjava.api.enums.DoorModel;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleDoor implements Door {

    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    @Getter
    DoorModel model;
    @Getter
    Location location;

    public void setOpen(boolean open){
        dimension.getServer().call("SetDoorOpen", id, open);
    }

    public boolean isOpen(){
        return dimension.getServer().call("IsDoorOpen", id).get()[0].getAsBoolean();
    }

    public void setLocation(Location location){
        this.location = location;
        dimension.getServer().call("SetDoorLocation", id, location.getX(), location.getY(), location.getZ(), location.getHeading());
    }

    public void remove(){
        dimension.getServer().getDoors().remove(this);
        dimension.getServer().call("DestroyDoor", id);
    }


}
