package net.onfirenetwork.onsetjava.simple.event;

import com.google.gson.JsonElement;
import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.OnsetServer;
import net.onfirenetwork.onsetjava.api.entity.HitEntity;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.entity.WorldObject;
import net.onfirenetwork.onsetjava.api.enums.HitType;
import net.onfirenetwork.onsetjava.api.event.ClientEventTransformer;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.client.*;
import net.onfirenetwork.onsetjava.api.event.client.PlayerEnterVehicleEvent;
import net.onfirenetwork.onsetjava.api.event.client.PlayerExitVehicleEvent;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

public class DefaultClientEventTransformer implements ClientEventTransformer {

    public Event transform(Player player, String type, int nonce, JsonElement[] params) {
        OnsetServer server = OnsetJava.getServer();
        Event event = null;
        switch (type) {
            case "OnKeyPress":
                event = new KeyPressEvent(player, params[0].getAsString(), params[1].getAsBoolean(), params[2].getAsBoolean(), params[3].getAsBoolean(), params[4].getAsBoolean());
                break;
            case "OnKeyRelease":
                event = new KeyReleaseEvent(player, params[0].getAsString(), params[1].getAsBoolean(), params[2].getAsBoolean(), params[3].getAsBoolean(), params[4].getAsBoolean());
                break;
            case "OnSoundFinished":
                event = new SoundFinishedEvent(player, player.getSound(params[0].getAsInt()));
                break;
            case "OnWebLoadComplete":
                event = new WebReadyEvent(player, player.getWebUI(params[0].getAsInt()));
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
                event = new CollisionEnterEvent(player, params[0].getAsInt(), HitType.get(params[1].getAsInt()), params[2].getAsInt());
                break;
            case "OnCollisionLeave":
                event = new CollisionLeaveEvent(player, params[0].getAsInt(), HitType.get(params[1].getAsInt()), params[2].getAsInt());
                break;
            case "OnResolutionChange":
                event = new ResolutionChangeEvent(player, params[0].getAsInt(), params[1].getAsInt());
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
                WorldObject object = server.getObject(params[0].getAsInt());
                HitType hitType = HitType.get(params[1].getAsInt());
                HitEntity hitEntity = null;
                int hitId = params[2].getAsInt();
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
                Location hitLocation = new Location(params[3].getAsInt(), params[4].getAsInt(), params[5].getAsInt());
                Vector3d normal = new Vector3d(params[6].getAsDouble(), params[7].getAsDouble(), params[8].getAsDouble());
                event = new ObjectHitEvent(object, hitType, hitEntity, hitLocation, normal);
                break;
            case "OnPlayerBeginEditObject":
                event = new PlayerBeginEditObjectEvent(player, server.getObject(params[0].getAsInt()));
                break;
            case "OnPlayerEndEditObject":
                event = new PlayerEndEditObjectEvent(player, server.getObject(params[0].getAsInt()));
                break;
            case "OnLightStreamIn":
                event = new LightStreamInEvent(player, server.getLight(params[0].getAsInt()));
                break;
            case "OnNPCStreamIn":
                event = new NPCStreamInEvent(player, server.getNPC(params[0].getAsInt()));
                break;
            case "OnObjectStreamIn":
                event = new ObjectStreamInEvent(player, server.getObject(params[0].getAsInt()));
                break;
            case "OnPickupStreamIn":
                event = new PickupStreamInEvent(player, server.getPickup(params[0].getAsInt()));
                break;
            case "OnText3DStreamIn":
                event = new Text3DStreamInEvent(player, server.getText3D(params[0].getAsInt()));
                break;
            case "OnPlayerStartExitVehicle": {
                Vehicle vehicle = server.getVehicle(params[0].getAsInt());
                event = new PlayerExitVehicleEvent(player, vehicle);
                break;
            }
            case "OnPlayerStartEnterVehicle": {
                Vehicle vehicle = server.getVehicle(params[0].getAsInt());
                event = new PlayerEnterVehicleEvent(player, vehicle, params[1].getAsInt());
                break;
            }
        }
        return event;
    }

    public String[] register(Class<Event> eventClass){
        if (eventClass.equals(SoundFinishedEvent.class))
            return new String[]{"OnSoundFinished"};
        else if (eventClass.equals(PlayerEnterVehicleEvent.class))
            return new String[]{"OnPlayerStartEnterVehicle"};
        else if (eventClass.equals(PlayerExitVehicleEvent.class))
            return new String[]{"OnPlayerStartExitVehicle"};
        else if (eventClass.equals(WebReadyEvent.class))
            return new String[]{"OnWebLoadComplete"};
        else if (eventClass.equals(PlayerCrouchStateEvent.class))
            return new String[]{"OnPlayerCrouch", "OnPlayerEndCrouch"};
        else if (eventClass.equals(PlayerFallStateEvent.class))
            return new String[]{"OnPlayerFall", "OnPlayerEndFall"};
        else if (eventClass.equals(PlayerSwimStateEvent.class))
            return new String[]{"OnPlayerEnterWater", "OnPlayerLeaveWater"};
        else if (eventClass.equals(PlayerSkydiveEvent.class))
            return new String[]{"OnPlayerSkydive"};
        else if (eventClass.equals(PlayerSkydiveEndEvent.class))
            return new String[]{"OnPlayerCancelSkydive", "OnPlayerSkydiveCrash"};
        else if (eventClass.equals(CollisionEnterEvent.class))
            return new String[]{"OnCollisionEnter"};
        else if (eventClass.equals(CollisionLeaveEvent.class))
            return new String[]{"OnCollisionLeave"};
        else if (eventClass.equals(ResolutionChangeEvent.class))
            return new String[]{"OnResolutionChange"};
        else if (eventClass.equals(PlayerReloadedEvent.class))
            return new String[]{"OnPlayerReloaded"};
        else if (eventClass.equals(PlayerParachuteLandEvent.class))
            return new String[]{"OnPlayerParachuteLand"};
        else if (eventClass.equals(PlayerParachuteStateEvent.class))
            return new String[]{"OnPlayerParachuteOpen", "OnPlayerParachuteClose"};
        else if (eventClass.equals(ObjectHitEvent.class))
            return new String[]{"OnObjectHit"};
        else if (eventClass.equals(PlayerBeginEditObjectEvent.class))
            return new String[]{"OnPlayerBeginEditObject"};
        else if (eventClass.equals(PlayerEndEditObjectEvent.class))
            return new String[]{"OnPlayerEndEditObject"};
        else if (eventClass.equals(LightStreamInEvent.class))
            return new String[]{"OnLightStreamIn"};
        else if (eventClass.equals(NPCStreamInEvent.class))
            return new String[]{"OnNPCStreamIn"};
        else if (eventClass.equals(ObjectStreamInEvent.class))
            return new String[]{"OnObjectStreamIn"};
        else if (eventClass.equals(PickupStreamInEvent.class))
            return new String[]{"OnPickupStreamIn"};
        else if (eventClass.equals(Text3DStreamInEvent.class))
            return new String[]{"OnText3DStreamIn"};
        return new String[0];
    }

}
