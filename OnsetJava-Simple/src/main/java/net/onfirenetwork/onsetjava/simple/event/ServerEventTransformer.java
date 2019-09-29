package net.onfirenetwork.onsetjava.simple.event;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.enums.DamageType;
import net.onfirenetwork.onsetjava.api.enums.HitType;
import net.onfirenetwork.onsetjava.api.enums.PlayerState;
import net.onfirenetwork.onsetjava.api.enums.WeaponModel;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.server.*;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;
import net.onfirenetwork.onsetjava.simple.adapter.InboundAction;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;

public class ServerEventTransformer {

    public Event transform(InboundAction action) {
        SimpleOnsetServer server = (SimpleOnsetServer) OnsetJava.getServer();

        Event event = null;

        switch (action.getType()) {
            case "OnPlayerServerAuth": {
                Player player = new SimplePlayer(OnsetJava.getServer().getDimension(0), action.getParams()[0].getAsInt());
                server.getPlayers().add(player);
                server.callClientAction(player.getId(), "RegisterEvents", 0, server.getEnabledClientEvents());
                server.callClientAction(player.getId(), "RegisterKeys", 0, server.getRegisteredKeys());
                event = new PlayerConnectEvent(player);
                break;
            }
            case "OnPlayerJoin": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                event = new PlayerJoinEvent(player);
                break;
            }
            case "OnPlayerQuit": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                server.getPlayers().remove(player);
                event = new PlayerQuitEvent(player);
                break;
            }
            case "OnPlayerSteamAuth": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                event = new PlayerQuitEvent(player);
                break;
            }
            case "OnPlayerChat": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                event = new PlayerChatEvent(player, action.getParams()[1].getAsString());
                break;
            }
            case "OnPlayerDeath": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                Player killer = server.getPlayer(action.getParams()[1].getAsInt());
                event = new PlayerDeathEvent(player, killer);
                break;
            }
            case "OnPlayerSpawn": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                event = new PlayerSpawnEvent(player);
                break;
            }
            case "OnPlayerEnterVehicle": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                Vehicle vehicle = server.getVehicle(action.getParams()[1].getAsInt());
                event = new PlayerEnterVehicleEvent(player, vehicle, action.getParams()[2].getAsInt());
                break;
            }
            case "OnPlayerLeaveVehicle": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                Vehicle vehicle = server.getVehicle(action.getParams()[1].getAsInt());
                event = new PlayerExitVehicleEvent(player, vehicle, action.getParams()[2].getAsInt());
                break;
            }
            case "OnPlayerDamage": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                DamageType damageType = DamageType.get(action.getParams()[1].getAsInt());
                event = new PlayerDamageEvent(player, damageType, action.getParams()[2].getAsDouble());
                break;
            }
            case "OnVehicleRespawn": {
                Vehicle vehicle = server.getVehicle(action.getParams()[0].getAsInt());
                event = new VehicleRespawnEvent(vehicle);
                break;
            }
            case "OnNPCDeath": {
                NPC npc = server.getNPC(action.getParams()[0].getAsInt());
                Player killer = server.getPlayer(action.getParams()[1].getAsInt());
                event = new NPCDeathEvent(npc, killer);
                break;
            }
            case "OnPlayerPickupHit": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                Pickup pickup = server.getPickup(action.getParams()[1].getAsInt());
                event = new PlayerPickupEvent(player, pickup);
                break;
            }
            case "OnVehiclePickupHit": {
                Vehicle vehicle = server.getVehicle(action.getParams()[0].getAsInt());
                Pickup pickup = server.getPickup(action.getParams()[1].getAsInt());
                event = new VehiclePickupEvent(vehicle, pickup);
                break;
            }
            case "OnPlayerStateChange": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                PlayerState newState = PlayerState.get(action.getParams()[1].getAsInt());
                PlayerState oldState = PlayerState.get(action.getParams()[2].getAsInt());
                event = new PlayerStateChangeEvent(player, newState, oldState);
                break;
            }
            case "OnPlayerStreamIn": {
                Player streamed = server.getPlayer(action.getParams()[0].getAsInt());
                Player player = server.getPlayer(action.getParams()[1].getAsInt());
                event = new PlayerStreamInEvent(streamed, player);
                break;
            }
            case "OnPlayerStreamOut": {
                Player streamed = server.getPlayer(action.getParams()[0].getAsInt());
                Player player = server.getPlayer(action.getParams()[1].getAsInt());
                event = new PlayerStreamOutEvent(streamed, player);
                break;
            }
            case "OnVehicleStreamIn": {
                Vehicle streamed = server.getVehicle(action.getParams()[0].getAsInt());
                Player player = server.getPlayer(action.getParams()[1].getAsInt());
                event = new VehicleStreamInEvent(streamed, player);
                break;
            }
            case "OnVehicleStreamOut": {
                Vehicle streamed = server.getVehicle(action.getParams()[0].getAsInt());
                Player player = server.getPlayer(action.getParams()[1].getAsInt());
                event = new VehicleStreamOutEvent(streamed, player);
                break;
            }
            case "OnPlayerWeaponShot": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                WeaponModel weapon = WeaponModel.getModel(action.getParams()[1].getAsInt());
                HitType hitType = HitType.get(action.getParams()[2].getAsInt());
                HitEntity hitEntity = null;
                int hitId = action.getParams()[3].getAsInt();
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
                Location location = new Location(action.getParams()[4].getAsInt(), action.getParams()[5].getAsInt(), action.getParams()[6].getAsInt());
                Vector2d start = new Vector2d(action.getParams()[7].getAsInt(), action.getParams()[8].getAsInt());
                Vector3d normal = new Vector3d(action.getParams()[9].getAsInt(), action.getParams()[10].getAsInt(), action.getParams()[11].getAsInt());
                event = new PlayerWeaponShotEvent(player, weapon, hitType, hitEntity, location, start, normal);
                break;
            }
            case "OnPlayerChatCommand": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                String command = action.getParams()[1].getAsString();
                boolean exists = action.getParams()[2].getAsInt() == 1;
                event = new PlayerCommandEvent(player, command, exists);
                break;
            }
            case "OnNPCSpawn": {
                NPC npc = server.getNPC(action.getParams()[0].getAsInt());
                event = new NPCSpawnEvent(npc);
                break;
            }
            case "OnNPCDamage": {
                NPC npc = server.getNPC(action.getParams()[0].getAsInt());
                DamageType type = DamageType.get(action.getParams()[1].getAsInt());
                double amount = action.getParams()[2].getAsDouble();
                event = new NPCDamageEvent(npc, type, amount);
                break;
            }
            case "OnNPCReachTarget": {
                NPC npc = server.getNPC(action.getParams()[0].getAsInt());
                event = new NPCReachTargetEvent(npc);
                break;
            }
            case "OnClientConnectionRequest":
                String address = action.getParams()[0].getAsString();
                int port = action.getParams()[0].getAsInt();
                event = new ClientConnectionEvent(address, port);
                break;
            case "OnPlayerDownloadFile": {
                Player player = server.getPlayer(action.getParams()[0].getAsInt());
                String fileName = action.getParams()[1].getAsString();
                String checkSum = action.getParams()[2].getAsString();
                event = new PlayerDownloadFileEvent(player, fileName, checkSum);
                break;
            }
        }

        return event;
    }

}
