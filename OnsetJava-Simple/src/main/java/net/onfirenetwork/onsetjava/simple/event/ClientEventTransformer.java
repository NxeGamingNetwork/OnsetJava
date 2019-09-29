package net.onfirenetwork.onsetjava.simple.event;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.OnsetServer;
import net.onfirenetwork.onsetjava.api.entity.HitEntity;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.WorldObject;
import net.onfirenetwork.onsetjava.api.enums.HitType;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.client.*;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.adapter.InboundAction;

public class ClientEventTransformer {

    public Event transform(Player player, InboundAction action) {
        OnsetServer server = OnsetJava.getServer();
        if (action.getType().equals("OnKeyPress")) {
            return new KeyPressEvent(player, action.getParams()[0].getAsString());
        }
        if (action.getType().equals("OnKeyRelease")) {
            return new KeyReleaseEvent(player, action.getParams()[0].getAsString());
        }
        if (action.getType().equals("OnSoundFinished")) {
            return new SoundFinishedEvent(player, player.getSound(action.getParams()[0].getAsInt()));
        }
        if (action.getType().equals("OnWebLoadComplete")) {
            return new WebReadyEvent(player, player.getWebUI(action.getParams()[0].getAsInt()));
        }
        if (action.getType().equals("OnPlayerCrouch")) {
            return new PlayerCrouchStateEvent(player, true);
        }
        if (action.getType().equals("OnPlayerEndCrouch")) {
            return new PlayerCrouchStateEvent(player, false);
        }
        if (action.getType().equals("OnPlayerFall")) {
            return new PlayerFallStateEvent(player, true);
        }
        if (action.getType().equals("OnPlayerEndFall")) {
            return new PlayerFallStateEvent(player, false);
        }
        if (action.getType().equals("OnPlayerEnterWater")) {
            return new PlayerSwimStateEvent(player, true);
        }
        if (action.getType().equals("OnPlayerLeaveWater")) {
            return new PlayerSwimStateEvent(player, false);
        }
        if (action.getType().equals("OnPlayerSkydive")) {
            return new PlayerSkydiveEvent(player);
        }
        if (action.getType().equals("OnPlayerCancelSkydive")) {
            return new PlayerSkydiveEndEvent(player, false);
        }
        if (action.getType().equals("OnPlayerSkydiveCrash")) {
            return new PlayerSkydiveEndEvent(player, true);
        }
        if (action.getType().equals("OnCollisionEnter")) {
            return new CollisionEnterEvent(player, action.getParams()[0].getAsInt(), HitType.get(action.getParams()[1].getAsInt()), action.getParams()[2].getAsInt());
        }
        if (action.getType().equals("OnCollisionLeave")) {
            return new CollisionLeaveEvent(player, action.getParams()[0].getAsInt(), HitType.get(action.getParams()[1].getAsInt()), action.getParams()[2].getAsInt());
        }
        if (action.getType().equals("OnResolutionChange")) {
            return new ResolutionChangeEvent(player, action.getParams()[0].getAsInt(), action.getParams()[1].getAsInt());
        }
        if (action.getType().equals("OnPlayerReloaded")) {
            return new PlayerReloadedEvent(player);
        }
        if (action.getType().equals("OnPlayerParachuteLand")) {
            return new PlayerParachuteLandEvent(player);
        }
        if (action.getType().equals("OnPlayerParachuteOpen")) {
            return new PlayerParachuteStateEvent(player, true);
        }
        if (action.getType().equals("OnPlayerParachuteClose")) {
            return new PlayerParachuteStateEvent(player, false);
        }
        if (action.getType().equals("OnObjectHit")) {
            WorldObject object = server.getObject(action.getParams()[0].getAsInt());
            HitType hitType = HitType.get(action.getParams()[1].getAsInt());
            HitEntity hitEntity = null;
            int hitId = action.getParams()[2].getAsInt();
            switch (hitType){
                case HIT_NPC:
                    hitEntity = server.getNPC(hitId);
                    break;
                case HIT_OBJECT:
                    if(hitId > 0){
                        hitEntity = server.getObject(hitId);
                    }
                    break;
                case HIT_VEHICLE:
                    hitEntity = server.getVehicle(hitId);
                    break;
                case HIT_PLAYER:
                    hitEntity = server.getPlayer(hitId);
                    break;
            }
            Location hitLocation = new Location(action.getParams()[3].getAsDouble(), action.getParams()[4].getAsDouble(), action.getParams()[5].getAsDouble());
            Vector3d normal = new Vector3d(action.getParams()[6].getAsDouble(), action.getParams()[7].getAsDouble(), action.getParams()[8].getAsDouble());
            return new ObjectHitEvent(object, hitType, hitEntity, hitLocation, normal);
        }
        if (action.getType().equals("OnPlayerBeginEditObject")) {
            return new PlayerBeginEditObjectEvent(player, server.getObject(action.getParams()[0].getAsInt()));
        }
        if (action.getType().equals("OnPlayerEndEditObject")) {
            return new PlayerEndEditObjectEvent(player, server.getObject(action.getParams()[0].getAsInt()));
        }
        if (action.getType().equals("OnLightStreamIn")) {
            return new LightStreamInEvent(player, server.getLight(action.getParams()[0].getAsInt()));
        }
        if (action.getType().equals("OnNPCStreamIn")) {
            return new NPCStreamInEvent(player, server.getNPC(action.getParams()[0].getAsInt()));
        }
        if (action.getType().equals("OnObjectStreamIn")) {
            return new ObjectStreamInEvent(player, server.getObject(action.getParams()[0].getAsInt()));
        }
        if (action.getType().equals("OnPickupStreamIn")) {
            return new PickupStreamInEvent(player, server.getPickup(action.getParams()[0].getAsInt()));
        }
        if (action.getType().equals("OnText3DStreamIn")) {
            return new Text3DStreamInEvent(player, server.getText3D(action.getParams()[0].getAsInt()));
        }
        return null;
    }

}
