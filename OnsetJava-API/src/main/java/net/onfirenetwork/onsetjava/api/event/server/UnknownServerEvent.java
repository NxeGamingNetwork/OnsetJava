package net.onfirenetwork.onsetjava.api.event.server;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.event.Event;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnknownServerEvent extends Event {
    String type;
    int nonce;
    JsonElement[] params;
}
