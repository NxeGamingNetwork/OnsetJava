package net.onfirenetwork.onsetjava.simple.event;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.OnsetServer;
import net.onfirenetwork.onsetjava.api.entity.NPC;
import net.onfirenetwork.onsetjava.api.entity.Pickup;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.event.Event;
import net.onfirenetwork.onsetjava.api.event.enums.DamageType;
import net.onfirenetwork.onsetjava.api.event.enums.PlayerState;
import net.onfirenetwork.onsetjava.api.event.server.*;
import net.onfirenetwork.onsetjava.simple.adapter.InboundAction;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;

public class ServerEventTransformer {

    public Event transform(InboundAction action){
        OnsetServer server = OnsetJava.getServer();
        if(action.getType().equals("OnPlayerServerAuth")){
            Player player = new SimplePlayer(OnsetJava.getServer().getDimension(0), action.getParams()[0].getAsInt());
            server.getPlayers().add(player);
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
        return null;
    }

}
