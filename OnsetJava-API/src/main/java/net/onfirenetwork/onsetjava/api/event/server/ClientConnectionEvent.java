package net.onfirenetwork.onsetjava.api.event.server;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.event.Event;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class ClientConnectionEvent extends Event {
    String address;
    int port;
}
