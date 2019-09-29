package net.onfirenetwork.onsetjava.simple.event;

import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.client.*;
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
        if(action.getType().equals("OnPlayerCrouch")){
            return new PlayerCrouchStateEvent(player, true);
        }
        if(action.getType().equals("OnPlayerEndCrouch")){
            return new PlayerCrouchStateEvent(player, false);
        }
        if(action.getType().equals("OnPlayerFall")){
            return new PlayerFallStateEvent(player, true);
        }
        if(action.getType().equals("OnPlayerEndFall")){
            return new PlayerFallStateEvent(player, false);
        }
        if(action.getType().equals("OnPlayerEnterWater")){
            return new PlayerSwimStateEvent(player, true);
        }
        if(action.getType().equals("OnPlayerLeaveWater")){
            return new PlayerSwimStateEvent(player, false);
        }
        if(action.getType().equals("OnPlayerSkydive")){
            return new PlayerSkydiveEvent(player);
        }
        if(action.getType().equals("OnPlayerCancelSkydive")){
            return new PlayerSkydiveEndEvent(player, false);
        }
        if(action.getType().equals("OnPlayerSkydiveCrash")){
            return new PlayerSkydiveEndEvent(player, true);
        }
        return null;
    }

}
