package net.onfirenetwork.onsetjava.api;

import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.event.ClientEventTransformer;
import net.onfirenetwork.onsetjava.api.event.EventBus;
import net.onfirenetwork.onsetjava.api.event.ServerEventTransformer;
import net.onfirenetwork.onsetjava.api.plugin.PluginManager;
import net.onfirenetwork.onsetjava.api.util.NetworkStats;
import net.onfirenetwork.onsetjava.api.util.Vector2d;

import java.util.List;

public interface OnsetServer {

    void broadcast(String message);

    default void broadcast(String message, String color){
        broadcast("<span color=\""+color+"\">"+message+"</>");
    }

    void broadcastRange(Vector2d location, int range, String message);

    void print(String text);

    int getTickCount();

    int getGameVersion();

    String getGameVersionString();

    int getServerTickRate();

    void shutdown(String message);

    default void shutdown() {
        shutdown(null);
    }

    String getName();

    void setName(String name);

    int getMaxPlayers();

    NetworkStats getNetworkStats();

    Player getPlayer(int id);

    List<Player> getPlayers();

    Vehicle getVehicle(int id);

    List<Vehicle> getVehicles();

    NPC getNPC(int id);

    List<NPC> getNPCs();

    WorldObject getObject(int id);

    List<WorldObject> getObjects();

    Text3D getText3D(int id);

    List<Text3D> getText3Ds();

    Pickup getPickup(int id);

    List<Pickup> getPickups();

    Light getLight(int id);

    List<Light> getLights();

    EventBus getEventBus();

    Dimension getDimension(int id);

    List<Dimension> getDimensions();

    Dimension createDimension();

    void registerCommand(String name, CommandExecutor executor);

    void registerKeys(String... keys);

    PluginManager getPluginManager();

    void addClientEventTransformer(ClientEventTransformer transformer);

    void addServerEventTransformer(ServerEventTransformer transformer);

}
