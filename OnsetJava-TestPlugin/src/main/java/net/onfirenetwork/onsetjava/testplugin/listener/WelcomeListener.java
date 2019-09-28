package net.onfirenetwork.onsetjava.testplugin.listener;

import net.onfirenetwork.onsetjava.api.event.server.PlayerJoinEvent;

public class WelcomeListener {

    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().sendMessage("Herzlich Willkommen, "+e.getPlayer().getName()+"!");
    }

}
