package net.onfirenetwork.onsetjava.simple.event;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.enums.HitType;
import net.onfirenetwork.onsetjava.api.enums.WeaponModel;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.enums.DamageType;
import net.onfirenetwork.onsetjava.api.enums.PlayerState;
import net.onfirenetwork.onsetjava.api.event.server.*;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;
import net.onfirenetwork.onsetjava.simple.adapter.InboundAction;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;

public class ServerEventTransformer {

    public Event transform(InboundAction action){
        SimpleOnsetServer server = (SimpleOnsetServer) OnsetJava.getServer();
        if(action.getType().equals("OnPlayerServerAuth")){
            Player player = new SimplePlayer(OnsetJava.getServer().getDimension(0), action.getParams()[0].getAsInt());
            server.getPlayers().add(player);
            server.callClientAction(player.getId(), "RegisterEvents", 0, server.getEnabledClientEvents());
            return new PlayerConnectEvent(player);
        }
        if(action.getType().equals("OnPlayerJoin")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            return new PlayerJoinEvent(player);
        }
        if(action.getType().equals("OnPlayerQuit")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            server.getPlayers().remove(player);
            return new PlayerQuitEvent(player);
        }
        if(action.getType().equals("OnPlayerSteamAuth")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            return new PlayerQuitEvent(player);
        }
        if(action.getType().equals("OnPlayerChat")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            return new PlayerChatEvent(player, action.getParams()[1].getAsString());
        }
        if(action.getType().equals("OnPlayerDeath")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            Player killer = server.getPlayer(action.getParams()[1].getAsInt());
            return new PlayerDeathEvent(player, killer);
        }
        if(action.getType().equals("OnPlayerSpawn")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            return new PlayerSpawnEvent(player);
        }
        if(action.getType().equals("OnPlayerEnterVehicle")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            Vehicle vehicle = server.getVehicle(action.getParams()[1].getAsInt());
            return new PlayerEnterVehicleEvent(player, vehicle, action.getParams()[2].getAsInt());
        }
        if(action.getType().equals("OnPlayerLeaveVehicle")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            Vehicle vehicle = server.getVehicle(action.getParams()[1].getAsInt());
            return new PlayerExitVehicleEvent(player, vehicle, action.getParams()[2].getAsInt());
        }
        if(action.getType().equals("OnPlayerDamage")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            DamageType damageType = DamageType.get(action.getParams()[1].getAsInt());
            return new PlayerDamageEvent(player, damageType, action.getParams()[2].getAsDouble());
        }
        if(action.getType().equals("OnVehicleRespawn")){
            Vehicle vehicle = server.getVehicle(action.getParams()[0].getAsInt());
            return new VehicleRespawnEvent(vehicle);
        }
        if(action.getType().equals("OnNPCDeath")){
            NPC npc = server.getNPC(action.getParams()[0].getAsInt());
            Player killer = server.getPlayer(action.getParams()[1].getAsInt());
            return new NPCDeathEvent(npc, killer);
        }
        if(action.getType().equals("OnPlayerPickupHit")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            Pickup pickup = server.getPickup(action.getParams()[1].getAsInt());
            return new PlayerPickupEvent(player, pickup);
        }
        if(action.getType().equals("OnVehiclePickupHit")){
            Vehicle vehicle = server.getVehicle(action.getParams()[0].getAsInt());
            Pickup pickup = server.getPickup(action.getParams()[1].getAsInt());
            return new VehiclePickupEvent(vehicle, pickup);
        }
        if(action.getType().equals("OnPlayerStateChange")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            PlayerState newState = PlayerState.get(action.getParams()[1].getAsInt());
            PlayerState oldState = PlayerState.get(action.getParams()[2].getAsInt());
            return new PlayerStateChangeEvent(player, newState, oldState);
        }
        if(action.getType().equals("OnPlayerStreamIn")){
            Player streamed = server.getPlayer(action.getParams()[0].getAsInt());
            Player player = server.getPlayer(action.getParams()[1].getAsInt());
            return new PlayerStreamInEvent(streamed, player);
        }
        if(action.getType().equals("OnPlayerStreamOut")){
            Player streamed = server.getPlayer(action.getParams()[0].getAsInt());
            Player player = server.getPlayer(action.getParams()[1].getAsInt());
            return new PlayerStreamOutEvent(streamed, player);
        }
        if(action.getType().equals("OnVehicleStreamIn")){
            Vehicle streamed = server.getVehicle(action.getParams()[0].getAsInt());
            Player player = server.getPlayer(action.getParams()[1].getAsInt());
            return new VehicleStreamInEvent(streamed, player);
        }
        if(action.getType().equals("OnVehicleStreamOut")){
            Vehicle streamed = server.getVehicle(action.getParams()[0].getAsInt());
            Player player = server.getPlayer(action.getParams()[1].getAsInt());
            return new VehicleStreamOutEvent(streamed, player);
        }
        if(action.getType().equals("OnPlayerWeaponShot")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            WeaponModel weapon = WeaponModel.getModel(action.getParams()[1].getAsInt());
            HitType hitType = HitType.get(action.getParams()[2].getAsInt());
            HitEntity hitEntity = null;
            int hitId = action.getParams()[3].getAsInt();
            switch (hitType){
                case HIT_NPC:
                    hitEntity = server.getNPC(hitId);
                    break;
                case HIT_VEHICLE:
                    hitEntity = server.getVehicle(hitId);
                    break;
                case HIT_OBJECT:
                    if(hitId > 0)
                        hitEntity = server.getObject(hitId);
                    break;
                case HIT_PLAYER:
                    hitEntity = server.getPlayer(hitId);
                    break;
            }
            Location location = new Location(action.getParams()[4].getAsInt(), action.getParams()[5].getAsInt(), action.getParams()[6].getAsInt());
            Vector2d start = new Vector2d(action.getParams()[7].getAsInt(), action.getParams()[8].getAsInt());
            Vector3d normal = new Vector3d(action.getParams()[9].getAsInt(), action.getParams()[10].getAsInt(), action.getParams()[11].getAsInt());
            return new PlayerWeaponShotEvent(player, weapon, hitType, hitEntity, location, start, normal);
        }
        if(action.getType().equals("OnPlayerChatCommand")){
            Player player = server.getPlayer(action.getParams()[0].getAsInt());
            String command = action.getParams()[1].getAsString();
            boolean exists = action.getParams()[2].getAsInt() == 1;
            return new PlayerChatCommandEvent(player, command, exists);
        }
        if(action.getType().equals("OnNPCSpawn")){
            NPC npc = server.getNPC(action.getParams()[0].getAsInt());
            return new NPCSpawnEvent(npc);
        }
        if(action.getType().equals("OnNPCDamage")){
            NPC npc = server.getNPC(action.getParams()[0].getAsInt());
            DamageType type = DamageType.get(action.getParams()[1].getAsInt());
            double amount = action.getParams()[2].getAsDouble();
            return new NPCDamageEvent(npc, type, amount);
        }
        if(action.getType().equals("OnNPCReachTarget")){
            NPC npc = server.getNPC(action.getParams()[0].getAsInt());
            return new NPCReachTargetEvent(npc);
        }
        if(action.getType().equals("OnClientConnectionRequest")){
            String address = action.getParams()[0].getAsString();
            int port = action.getParams()[0].getAsInt();
            return new ClientConnectionEvent(address, port);
        }
        return null;
    }

}
