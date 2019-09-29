package net.onfirenetwork.onsetjava.testplugin.listener;

import net.onfirenetwork.onsetjava.api.event.server.PlayerJoinEvent;
import net.onfirenetwork.onsetjava.api.util.Location;

public class WelcomeListener {

    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("Herzlich Willkommen, " + e.getPlayer().getName() + "!");
        e.getPlayer().setLocation(new Location(125773, 80246, 1645, 90));
    }

}
