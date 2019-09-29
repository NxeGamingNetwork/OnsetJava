package net.onfirenetwork.onsetjava.api.event.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Text3D;
import net.onfirenetwork.onsetjava.api.event.Event;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Text3DStreamInEvent extends Event {

    Player player;
    Text3D text3D;

}
