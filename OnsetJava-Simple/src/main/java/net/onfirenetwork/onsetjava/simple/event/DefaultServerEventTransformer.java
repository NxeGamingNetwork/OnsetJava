package net.onfirenetwork.onsetjava.simple.event;

import com.google.gson.JsonElement;
import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.enums.DamageType;
import net.onfirenetwork.onsetjava.api.enums.HitType;
import net.onfirenetwork.onsetjava.api.enums.PlayerState;
import net.onfirenetwork.onsetjava.api.enums.WeaponModel;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.ServerEventTransformer;
import net.onfirenetwork.onsetjava.api.event.server.*;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;

public class DefaultServerEventTransformer implements ServerEventTransformer {

    public Event transform(String type, int nonce, JsonElement[] params) {
        SimpleOnsetServer server = (SimpleOnsetServer) OnsetJava.getServer();
        Event event = null;
        switch (type) {
            case "OnPlayerServerAuth": {
                Player player = new SimplePlayer(OnsetJava.getServer().getDimension(0), params[0].getAsInt());
                server.getPlayers().add(player);
                event = new PlayerConnectEvent(player);
                break;
            }
            case "OnPlayerJoin": {
                Player player = server.getPlayer(params[0].getAsInt());
                server.callClientAction(player.getId(), "RegisterEvents", 0, server.getEnabledClientEvents());
                server.callClientAction(player.getId(), "RegisterKeys", 0, server.getRegisteredKeys());
                event = new PlayerJoinEvent(player);
                break;
            }
            case "OnPlayerQuit": {
                Player player = server.getPlayer(params[0].getAsInt());
                server.getPlayers().remove(player);
                event = new PlayerQuitEvent(player);
                break;
            }
            case "OnPlayerSteamAuth": {
                Player player = server.getPlayer(params[0].getAsInt());
                event = new PlayerAuthEvent(player);
                break;
            }
            case "OnPlayerChat": {
                Player player = server.getPlayer(params[0].getAsInt());
                event = new PlayerChatEvent(player, params[1].getAsString());
                break;
            }
            case "OnPlayerDeath": {
                Player player = server.getPlayer(params[0].getAsInt());
                Player killer = server.getPlayer(params[1].getAsInt());
                event = new PlayerDeathEvent(player, killer);
                break;
            }
            case "OnPlayerSpawn": {
                Player player = server.getPlayer(params[0].getAsInt());
                event = new PlayerSpawnEvent(player);
                break;
            }
            case "OnPlayerEnterVehicle": {
                Player player = server.getPlayer(params[0].getAsInt());
                Vehicle vehicle = server.getVehicle(params[1].getAsInt());
                event = new PlayerEnterVehicleEvent(player, vehicle, params[2].getAsInt());
                break;
            }
            case "OnPlayerLeaveVehicle": {
                Player player = server.getPlayer(params[0].getAsInt());
                Vehicle vehicle = server.getVehicle(params[1].getAsInt());
                event = new PlayerExitVehicleEvent(player, vehicle, params[2].getAsInt());
                break;
            }
            case "OnPlayerDamage": {
                Player player = server.getPlayer(params[0].getAsInt());
                DamageType damageType = DamageType.get(params[1].getAsInt());
                event = new PlayerDamageEvent(player, damageType, params[2].getAsDouble());
                break;
            }
            case "OnVehicleRespawn": {
                Vehicle vehicle = server.getVehicle(params[0].getAsInt());
                event = new VehicleRespawnEvent(vehicle);
                break;
            }
            case "OnNPCDeath": {
                NPC npc = server.getNPC(params[0].getAsInt());
                Player killer = server.getPlayer(params[1].getAsInt());
                event = new NPCDeathEvent(npc, killer);
                break;
            }
            case "OnPlayerPickupHit": {
                Player player = server.getPlayer(params[0].getAsInt());
                Pickup pickup = server.getPickup(params[1].getAsInt());
                event = new PlayerPickupEvent(player, pickup);
                break;
            }
            case "OnVehiclePickupHit": {
                Vehicle vehicle = server.getVehicle(params[0].getAsInt());
                Pickup pickup = server.getPickup(params[1].getAsInt());
                event = new VehiclePickupEvent(vehicle, pickup);
                break;
            }
            case "OnPlayerStateChange": {
                Player player = server.getPlayer(params[0].getAsInt());
                PlayerState newState = PlayerState.get(params[1].getAsInt());
                PlayerState oldState = PlayerState.get(params[2].getAsInt());
                event = new PlayerStateChangeEvent(player, newState, oldState);
                break;
            }
            case "OnPlayerStreamIn": {
                Player streamed = server.getPlayer(params[0].getAsInt());
                Player player = server.getPlayer(params[1].getAsInt());
                event = new PlayerStreamInEvent(streamed, player);
                break;
            }
            case "OnPlayerStreamOut": {
                Player streamed = server.getPlayer(params[0].getAsInt());
                Player player = server.getPlayer(params[1].getAsInt());
                event = new PlayerStreamOutEvent(streamed, player);
                break;
            }
            case "OnVehicleStreamIn": {
                Vehicle streamed = server.getVehicle(params[0].getAsInt());
                Player player = server.getPlayer(params[1].getAsInt());
                event = new VehicleStreamInEvent(streamed, player);
                break;
            }
            case "OnVehicleStreamOut": {
                Vehicle streamed = server.getVehicle(params[0].getAsInt());
                Player player = server.getPlayer(params[1].getAsInt());
                event = new VehicleStreamOutEvent(streamed, player);
                break;
            }
            case "OnPlayerWeaponShot": {
                Player player = server.getPlayer(params[0].getAsInt());
                WeaponModel weapon = WeaponModel.getModel(params[1].getAsInt());
                HitType hitType = HitType.get(params[2].getAsInt());
                HitEntity hitEntity = null;
                int hitId = params[3].getAsInt();
                switch (hitType) {
                    case HIT_NPC:
                        hitEntity = server.getNPC(hitId);
                        break;
                    case HIT_VEHICLE:
                        hitEntity = server.getVehicle(hitId);
                        break;
                    case HIT_OBJECT:
                        if (hitId > 0)
                            hitEntity = server.getObject(hitId);
                        break;
                    case HIT_PLAYER:
                        hitEntity = server.getPlayer(hitId);
                        break;
                }
                Location location = new Location(params[4].getAsInt(), params[5].getAsInt(), params[6].getAsInt());
                Vector2d start = new Vector2d(params[7].getAsInt(), params[8].getAsInt());
                Vector3d normal = new Vector3d(params[9].getAsInt(), params[10].getAsInt(), params[11].getAsInt());
                event = new PlayerWeaponShotEvent(player, weapon, hitType, hitEntity, location, start, normal);
                break;
            }
            case "OnPlayerChatCommand": {
                Player player = server.getPlayer(params[0].getAsInt());
                String command = params[1].getAsString();
                boolean exists = params[2].getAsBoolean();
                event = new PlayerCommandEvent(player, command, exists);
                break;
            }
            case "OnNPCSpawn": {
                NPC npc = server.getNPC(params[0].getAsInt());
                event = new NPCSpawnEvent(npc);
                break;
            }
            case "OnNPCDamage": {
                NPC npc = server.getNPC(params[0].getAsInt());
                DamageType damageType = DamageType.get(params[1].getAsInt());
                double amount = params[2].getAsDouble();
                event = new NPCDamageEvent(npc, damageType, amount);
                break;
            }
            case "OnNPCReachTarget": {
                NPC npc = server.getNPC(params[0].getAsInt());
                event = new NPCReachTargetEvent(npc);
                break;
            }
            case "OnClientConnectionRequest":
                String address = params[0].getAsString();
                int port = params[0].getAsInt();
                event = new ClientConnectionEvent(address, port);
                break;
            case "OnPlayerDownloadFile": {
                Player player = server.getPlayer(params[0].getAsInt());
                String fileName = params[1].getAsString();
                String checkSum = params[2].getAsString();
                event = new PlayerDownloadFileEvent(player, fileName, checkSum);
                break;
            }
        }
        return event;
    }

    public String[] register(Class<Event> eventClass){
        if (eventClass.equals(PlayerAuthEvent.class))
            return new String[]{"OnPlayerSteamAuth"};
        else if (eventClass.equals(PlayerEnterVehicleEvent.class))
            return new String[]{"OnPlayerEnterVehicle"};
        else if (eventClass.equals(PlayerExitVehicleEvent.class))
            return new String[]{"OnPlayerLeaveVehicle"};
        else if (eventClass.equals(PlayerDeathEvent.class))
            return new String[]{"OnPlayerDeath"};
        else if (eventClass.equals(PlayerSpawnEvent.class))
            return new String[]{"OnPlayerSpawn"};
        else if (eventClass.equals(PlayerPickupEvent.class))
            return new String[]{"OnPlayerPickupHit"};
        else if (eventClass.equals(VehiclePickupEvent.class))
            return new String[]{"OnVehiclePickupHit"};
        else if (eventClass.equals(PlayerStateChangeEvent.class))
            return new String[]{"OnPlayerStateChange"};
        else if (eventClass.equals(NPCDeathEvent.class))
            return new String[]{"OnNPCDeath"};
        else if (eventClass.equals(VehicleRespawnEvent.class))
            return new String[]{"OnVehicleRespawn"};
        else if (eventClass.equals(PlayerDamageEvent.class))
            return new String[]{"OnPlayerDamage"};
        else if (eventClass.equals(PlayerChatEvent.class))
            return new String[]{"OnPlayerChat"};
        else if (eventClass.equals(PlayerStreamInEvent.class))
            return new String[]{"OnPlayerStreamIn"};
        else if (eventClass.equals(PlayerStreamOutEvent.class))
            return new String[]{"OnPlayerStreamOut"};
        else if (eventClass.equals(VehicleStreamInEvent.class))
            return new String[]{"OnVehicleStreamIn"};
        else if (eventClass.equals(VehicleStreamOutEvent.class))
            return new String[]{"OnVehicleStreamOut"};
        else if (eventClass.equals(PlayerWeaponShotEvent.class))
            return new String[]{"OnPlayerWeaponShot"};
        else if (eventClass.equals(PlayerCommandEvent.class))
            return new String[]{"OnPlayerChatCommand"};
        else if (eventClass.equals(NPCSpawnEvent.class))
            return new String[]{"OnNPCSpawn"};
        else if (eventClass.equals(NPCDamageEvent.class))
            return new String[]{"OnNPCDamage"};
        else if (eventClass.equals(NPCReachTargetEvent.class))
            return new String[]{"OnNPCReachTarget"};
        else if (eventClass.equals(ClientConnectionEvent.class))
            return new String[]{"OnClientConnectionRequest"};
        else if (eventClass.equals(PlayerDownloadFileEvent.class))
            return new String[]{"OnPlayerDownloadFile"};
        return new String[0];
    }

}
