package net.onfirenetwork.onsetjava.api.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@AllArgsConstructor
public enum HitType {
    HIT_AIR(1),
    HIT_PLAYER(2),
    HIT_VEHICLE(3),
    HIT_NPC(4),
    HIT_OBJECT(5),
    HIT_LANDSCAPE(6),
    HIT_WATER(7)
    ;
    int identifier;
}
