package net.onfirenetwork.onsetjava.api;

import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.enums.*;
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

    WorldObject createObject(Location location, int model, Vector3d rotation, Vector3d scale);

    default WorldObject createObject(Location location, int model, Vector3d rotation) {
        return createObject(location, model, rotation, null);
    }

    default WorldObject createObject(Location location, int model) {
        return createObject(location, model, null);
    }

    Text3D spawnText3D(Location location, Vector3d rotation, double size, String text);

    Pickup spawnPickup(Location location, int model);

    Light spawnLight(Location location, LightType type, double intensity, Vector3d rotation);

    default Light spawnLight(Location location, LightType type, double intensity) {
        return spawnLight(location, type, intensity, null);
    }

    Door createDoor(Location location, DoorModel model, boolean interactable);

    List<Door> getDoors();

    default Door createDoor(Location location, DoorModel model){
        return createDoor(location, model, false);
    }

    void createExplosion(Location location, ExplosionType type, boolean sound, double camShakeRadius, double radialForce);

    void createExplosion(Location location, ExplosionType type, boolean sound, double camShakeRadius);

    void createExplosion(Location location, ExplosionType type, boolean sound);

    void createExplosion(Location location, ExplosionType type);

}
