package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.enums.AttachType;

public interface StickyEntity {

    int getId();

    default AttachType getAttachType(){
        if(this instanceof Player){
            return AttachType.PLAYER;
        }
        if(this instanceof WorldObject){
            return AttachType.OBJECT;
        }
        if(this instanceof NPC){
            return AttachType.NPC;
        }
        if(this instanceof Vehicle){
            return AttachType.VEHICLE;
        }
        return AttachType.NONE;
    }

}
