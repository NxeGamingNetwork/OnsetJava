package net.onfirenetwork.onsetjava.testplugin.listener;

import net.onfirenetwork.onsetjava.api.client.WebUI;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.enums.WebVisibility;
import net.onfirenetwork.onsetjava.api.event.client.KeyPressEvent;
import net.onfirenetwork.onsetjava.api.event.server.PlayerJoinEvent;
import net.onfirenetwork.onsetjava.api.event.server.PlayerQuitEvent;
import net.onfirenetwork.onsetjava.api.util.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class ATMListener {

    private Map<Player, WebUI> webUIMap = new HashMap<>();

    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().createWebUI(new Vector2d(0, 0), new Vector2d(800, 600)).then(webUI -> {
            webUI.setVisibility(WebVisibility.HIDDEN);
            webUI.setAlignment(0.5, 0.5);
            webUI.setAnchors(0.5, 0.5, 0.5, 0.5);
            webUI.setUrl("http://asset/javatest/gui/atm.html");
            webUIMap.put(e.getPlayer(), webUI);
        });
    }

    public void onQuit(PlayerQuitEvent e){
        webUIMap.remove(e.getPlayer());
    }

    public void onKeyPress(KeyPressEvent e){
        if(e.getKey().equals("E")){
            WebUI ui = webUIMap.get(e.getPlayer());
            if(ui.getVisibility() == WebVisibility.HIDDEN){
                ui.setVisibility(WebVisibility.VISIBLE);
            }else {
                ui.setVisibility(WebVisibility.HIDDEN);
            }
        }
    }

}
