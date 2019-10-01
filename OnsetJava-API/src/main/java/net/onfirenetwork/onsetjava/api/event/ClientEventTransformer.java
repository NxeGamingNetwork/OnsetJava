package net.onfirenetwork.onsetjava.api.event;

import com.google.gson.JsonElement;
import net.onfirenetwork.onsetjava.api.entity.Player;

public interface ClientEventTransformer {
    Event transform(Player player, String type, int nonce, JsonElement[] params);
    String[] register(Class<Event> eventClass);
}
