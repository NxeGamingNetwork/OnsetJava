package net.onfirenetwork.onsetjava.api.event.server;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.enums.DamageType;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class PlayerDamageEvent extends Event {
    Player player;
    DamageType damageType;
    float amount;
}
