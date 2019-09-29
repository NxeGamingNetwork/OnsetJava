package net.onfirenetwork.onsetjava.simple.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.client.Sound;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleSound implements Sound {
    Player player;
    @Getter
    int id;

    public void remove() {
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        player.getSounds().remove(this);
        server.callClient(player.getId(), "DestroySound", id);
    }
}
