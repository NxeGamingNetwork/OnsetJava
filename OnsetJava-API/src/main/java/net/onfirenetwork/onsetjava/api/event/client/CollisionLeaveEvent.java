package net.onfirenetwork.onsetjava.api.event.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.enums.HitType;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class CollisionLeaveEvent extends Event {
    Player player;
    int collision;
    HitType hitType;
    int hitId;
}