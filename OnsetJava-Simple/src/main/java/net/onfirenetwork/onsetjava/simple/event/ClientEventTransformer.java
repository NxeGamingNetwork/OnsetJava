package net.onfirenetwork.onsetjava.simple.event;

import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.client.KeyPressEvent;
import net.onfirenetwork.onsetjava.api.event.client.KeyReleaseEvent;
import net.onfirenetwork.onsetjava.api.event.client.SoundFinishedEvent;
import net.onfirenetwork.onsetjava.api.event.client.WebReadyEvent;
import net.onfirenetwork.onsetjava.simple.adapter.InboundAction;

public class ClientEventTransformer {

    public Event transform(Player player, InboundAction action){
        if(action.getType().equals("OnKeyPress")){
            return new KeyPressEvent(player, action.getParams()[0].getAsString());
        }
        if(action.getType().equals("OnKeyRelease")){
            return new KeyReleaseEvent(player, action.getParams()[0].getAsString());
        }
        if(action.getType().equals("OnSoundFinished")){
            return new SoundFinishedEvent(player, player.getSound(action.getParams()[0].getAsInt()));
        }
        if(action.getType().equals("OnWebLoadComplete")){
            return new WebReadyEvent(player, player.getWebUI(action.getParams()[0].getAsInt()));
        }
        return null;
    }

}
