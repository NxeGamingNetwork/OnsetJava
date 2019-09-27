package net.onfirenetwork.onsetjava.api.event.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public enum DamageType {
    DAMAGE_WEAPON(1),
    DAMAGE_EXPLOSION(2),
    DAMAGE_FIRE(3),
    DAMAGE_FALL(4),
    DAMAGE_VEHICLE_COLLISION(5)
    ;
    int identifier;
}
