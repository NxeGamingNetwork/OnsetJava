package net.onfirenetwork.onsetjava.testplugin;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.event.server.PlayerJoinEvent;
import net.onfirenetwork.onsetjava.api.plugin.Plugin;
import net.onfirenetwork.onsetjava.api.plugin.PluginInfo;

public class TestPlugin implements Plugin {

    public void onEnable(){
        OnsetJava.getServer().getEventBus().listen(PlayerJoinEvent.class, event -> {
            event.getPlayer().sendMessage("Herzlich Willkommen, "+event.getPlayer().getName()+"!");
        });
        OnsetJava.getServer().registerCommand("steamid", (command,player,args) -> {
            player.sendMessage("Deine SteamID64 lautet: "+player.getSteamId());
        });
    }

    private PluginInfo info = new PluginInfo("TestPlugin", "1.0", "Onfire Network");
    public PluginInfo info() {
        return info;
    }
}
