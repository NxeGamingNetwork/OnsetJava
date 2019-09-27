package net.onfirenetwork.onsetjava.simple.event;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.OnsetServer;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.EventBus;
import net.onfirenetwork.onsetjava.api.event.server.PlayerConnectEvent;
import net.onfirenetwork.onsetjava.api.event.server.PlayerJoinEvent;
import net.onfirenetwork.onsetjava.api.event.server.PlayerQuitEvent;
import net.onfirenetwork.onsetjava.simple.adapter.InboundAction;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;

public class ServerEventTransformer {

    public Event transform(InboundAction action){
        OnsetServer server = OnsetJava.getServer();
        EventBus bus = server.getEventBus();
        if(action.getType().equals("OnPlayerServerAuth")){
            Player player = new SimplePlayer(OnsetJava.getServer().getDimension(0), action.getParams()[0].getAsInt());
            server.getPlayers().add(player);
            bus.fire(new PlayerConnectEvent(player));
        }
        if(action.getType().equals("OnPlayerJoin")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            bus.fire(new PlayerJoinEvent(player));
        }
        if(action.getType().equals("OnPlayerQuit")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            server.getPlayers().remove(player);
            bus.fire(new PlayerQuitEvent(player));
        }
        return null;
    }

}
