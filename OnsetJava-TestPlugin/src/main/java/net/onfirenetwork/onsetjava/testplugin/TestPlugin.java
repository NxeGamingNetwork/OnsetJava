package net.onfirenetwork.onsetjava.testplugin;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.client.WebUI;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.enums.WebVisibility;
import net.onfirenetwork.onsetjava.api.event.client.KeyPressEvent;
import net.onfirenetwork.onsetjava.api.event.server.PlayerJoinEvent;
import net.onfirenetwork.onsetjava.api.plugin.Plugin;
import net.onfirenetwork.onsetjava.api.plugin.PluginInfo;
import net.onfirenetwork.onsetjava.api.util.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class TestPlugin implements Plugin {

    public void onEnable(){
        Map<Player, WebUI> webUIMap = new HashMap<>();
        OnsetJava.getServer().getEventBus().listen(PlayerJoinEvent.class, event -> {
            event.getPlayer().registerKeys("E");
            event.getPlayer().sendMessage("Herzlich Willkommen, "+event.getPlayer().getName()+"!");
            WebUI webUI = event.getPlayer().createWebUI(new Vector2d(0, 0), new Vector2d(800, 600));
            webUI.setVisibility(WebVisibility.HIDDEN);
            webUI.setAlignment(0.5, 0.5);
            webUI.setAnchors(0.5, 0.5, 0.5, 0.5);
            webUI.setUrl("http://asset/javatest/gui/atm.html");
            webUIMap.put(event.getPlayer(), webUI);
        });
        OnsetJava.getServer().getEventBus().listen(KeyPressEvent.class, event -> {
            if(event.getKey().equals("E")){
                WebUI ui = webUIMap.get(event.getPlayer());
                if(ui.getVisibility() == WebVisibility.HIDDEN){
                    ui.setVisibility(WebVisibility.VISIBLE);
                }else {
                    ui.setVisibility(WebVisibility.HIDDEN);
                }
            }
        });
        OnsetJava.getServer().registerCommand("steamid", (command,player,args) -> {
            player.sendMessage("Deine SteamID64 lautet: "+player.getSteamId());
        });
        OnsetJava.getServer().registerCommand("sound", (command,player,args) -> {
            player.createSound(args[0]);
            player.sendMessage("Sound started!");
        });
    }

    private PluginInfo info = new PluginInfo("TestPlugin", "1.0", "Onfire Network");
    public PluginInfo info() {
        return info;
    }
}
