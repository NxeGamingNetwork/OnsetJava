package net.onfirenetwork.onsetjava.api;

import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.event.EventBus;
import net.onfirenetwork.onsetjava.api.plugin.PluginManager;

import java.util.List;

public interface OnsetServer {
    void broadcast(String message);
    void print(String text);
    void shutdown();
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
    PluginManager getPluginManager();
}
