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

        Event event = null;

        switch (action.getType()) {
            case "OnKeyPress":
                event = new KeyPressEvent(player, action.getParams()[0].getAsString(), action.getParams()[1].getAsBoolean(), action.getParams()[2].getAsBoolean());
                break;
            case "OnKeyRelease":
                event = new KeyReleaseEvent(player, action.getParams()[0].getAsString(), action.getParams()[1].getAsBoolean(), action.getParams()[2].getAsBoolean());
                break;
            case "OnSoundFinished":
                event = new SoundFinishedEvent(player, player.getSound(action.getParams()[0].getAsInt()));
                break;
            case "OnWebLoadComplete":
                event = new WebReadyEvent(player, player.getWebUI(action.getParams()[0].getAsInt()));
                break;
            case "OnPlayerCrouch":
                event = new PlayerCrouchStateEvent(player, true);
                break;
            case "OnPlayerEndCrouch":
                event = new PlayerCrouchStateEvent(player, false);
                break;
            case "OnPlayerFall":
                event = new PlayerFallStateEvent(player, true);
                break;
            case "OnPlayerEndFall":
                event = new PlayerFallStateEvent(player, false);
                break;
            case "OnPlayerEnterWater":
                event = new PlayerSwimStateEvent(player, true);
                break;
            case "OnPlayerLeaveWater":
                event = new PlayerSwimStateEvent(player, false);
                break;
            case "OnPlayerSkydive":
                event = new PlayerSkydiveEvent(player);
                break;
            case "OnPlayerCancelSkydive":
                event = new PlayerSkydiveEndEvent(player, false);
                break;
            case "OnPlayerSkydiveCrash":
                event = new PlayerSkydiveEndEvent(player, true);
                break;
            case "OnCollisionEnter":
                event = new CollisionEnterEvent(player, action.getParams()[0].getAsInt(), HitType.get(action.getParams()[1].getAsInt()), action.getParams()[2].getAsInt());
                break;
            case "OnCollisionLeave":
                event = new CollisionLeaveEvent(player, action.getParams()[0].getAsInt(), HitType.get(action.getParams()[1].getAsInt()), action.getParams()[2].getAsInt());
                break;
            case "OnResolutionChange":
                event = new ResolutionChangeEvent(player, action.getParams()[0].getAsInt(), action.getParams()[1].getAsInt());
                break;
            case "OnPlayerReloaded":
                event = new PlayerReloadedEvent(player);
                break;
            case "OnPlayerParachuteLand":
                event = new PlayerParachuteLandEvent(player);
                break;
            case "OnPlayerParachuteOpen":
                event = new PlayerParachuteStateEvent(player, true);
                break;
            case "OnPlayerParachuteClose":
                event = new PlayerParachuteStateEvent(player, false);
                break;
            case "OnObjectHit":
                WorldObject object = server.getObject(action.getParams()[0].getAsInt());
                HitType hitType = HitType.get(action.getParams()[1].getAsInt());
                HitEntity hitEntity = null;
                int hitId = action.getParams()[2].getAsInt();
                switch (hitType) {
                    case HIT_NPC:
                        hitEntity = server.getNPC(hitId);
                        break;
                    case HIT_OBJECT:
                        if (hitId > 0) {
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
                event = new ObjectHitEvent(object, hitType, hitEntity, hitLocation, normal);
                break;
            case "OnPlayerBeginEditObject":
                event = new PlayerBeginEditObjectEvent(player, server.getObject(action.getParams()[0].getAsInt()));
                break;
            case "OnPlayerEndEditObject":
                event = new PlayerEndEditObjectEvent(player, server.getObject(action.getParams()[0].getAsInt()));
                break;
            case "OnLightStreamIn":
                event = new LightStreamInEvent(player, server.getLight(action.getParams()[0].getAsInt()));
                break;
            case "OnNPCStreamIn":
                event = new NPCStreamInEvent(player, server.getNPC(action.getParams()[0].getAsInt()));
                break;
            case "OnObjectStreamIn":
                event = new ObjectStreamInEvent(player, server.getObject(action.getParams()[0].getAsInt()));
                break;
            case "OnPickupStreamIn":
                event = new PickupStreamInEvent(player, server.getPickup(action.getParams()[0].getAsInt()));
                break;
            case "OnText3DStreamIn":
                event = new Text3DStreamInEvent(player, server.getText3D(action.getParams()[0].getAsInt()));
                break;
        }

        return event;
    }

}
