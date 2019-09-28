package net.onfirenetwork.onsetjava.api.event.server;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.enums.PlayerState;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PlayerStateChangeEvent extends Event {
    Player player;
    PlayerState newState;
    PlayerState oldState;
}
