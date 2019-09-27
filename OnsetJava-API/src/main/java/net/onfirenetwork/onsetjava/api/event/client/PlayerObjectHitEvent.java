package net.onfirenetwork.onsetjava.api.event.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.WorldObject;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.enums.HitType;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class PlayerObjectHitEvent extends Event {
    Player player;
    WorldObject object;
    HitType hitType;
    int hidId;
    Vector3d position;
    Vector3d normal;
}
