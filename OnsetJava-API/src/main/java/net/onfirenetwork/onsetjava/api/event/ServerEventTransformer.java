package net.onfirenetwork.onsetjava.api.event;

import com.google.gson.JsonElement;

public interface ServerEventTransformer {
    Event transform(String type, int nonce, JsonElement[] params);
    String[] register(Class<Event> eventClass);
}
