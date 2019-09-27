package net.onfirenetwork.onsetjava.api;

import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.entity.models.CharacterModel;
import net.onfirenetwork.onsetjava.api.entity.models.LightType;
import net.onfirenetwork.onsetjava.api.entity.models.VehicleModel;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

import java.util.List;

public interface Dimension {
    int getId();
    List<Player> getPlayers();
    List<Vehicle> getVehicles();
    List<NPC> getNPCs();
    List<WorldObject> getObjects();
    List<Text3D> getText3Ds();
    List<Pickup> getPickups();
    List<Light> getLights();
    Vehicle spawnVehicle(Location location, VehicleModel model);
    NPC spawnNPC(Location location, CharacterModel model);
    WorldObject spawnObject(Location location, int model, Vector3d rotation, Vector3d scale);
    WorldObject spawnObject(Location location, int model, Vector3d rotation);
    WorldObject spawnObject(Location location, int model);
    Text3D spawnText3D(Location location, Vector3d rotation, double size, String text);
    Pickup spawnPickup(Location location, int model);
    Light spawnLight(Location location, LightType type, double intensity);
    Light spawnLight(Location location, LightType type, double intensity, Vector3d rotation);
}
