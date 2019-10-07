package net.onfirenetwork.onsetjava.api.enums;

import lombok.Getter;

public class DoorModel {

    @Getter
    private int id;

    private DoorModel(int id){
        this.id = id;
    }

    public static DoorModel getModel(int id){
        return new DoorModel(id);
    }

    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (!(other instanceof DoorModel))
            return false;
        return ((DoorModel) other).id == id;
    }

}
