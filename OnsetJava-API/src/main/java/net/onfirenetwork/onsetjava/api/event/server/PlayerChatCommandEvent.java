package net.onfirenetwork.onsetjava.api.event.server;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.event.Event;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerChatCommandEvent extends Event {
    @Getter
    Player player;
    @Getter
    String command;
    boolean exists;
    public boolean doesExist(){
        return exists;
    }
}
