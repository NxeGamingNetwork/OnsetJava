package net.onfirenetwork.onsetjava.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.enums.*;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.entity.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SimpleDimension implements Dimension {

    @Getter
    private SimpleOnsetServer server;
    @Getter
    private int id;

    public List<Player> getPlayers() {
        return server.getPlayers().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Vehicle spawnVehicle(Location location, VehicleModel model) {
        Vehicle vehicle = new SimpleVehicle(this, server.call("CreateVehicle", model.getId(), location.getX(), location.getY(), location.getZ()).get()[0].getAsInt(), model);
        if(this.id != 0){
            server.call("SetVehicleDimension", id, this.id);
        }
        server.getVehicles().add(vehicle);
        return vehicle;
    }

    public List<Vehicle> getVehicles() {
        return server.getVehicles().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public NPC spawnNPC(Location location, CharacterModel model) {
        NPC npc = new SimpleNPC(this, server.call("CreateNPC", model.getId(), location.getX(), location.getY(), location.getZ(), location.getHeading()).get()[0].getAsInt(), model);
        if(this.id != 0){
            server.call("SetNPCDimension", id, this.id);
        }
        server.getNPCs().add(npc);
        return npc;
    }

    public List<NPC> getNPCs() {
        return server.getNPCs().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public WorldObject createObject(Location location, int model, Vector3d rotation, Vector3d scale) {
        int id;
        if (rotation != null) {
            if (scale != null) {
                id = server.call("CreateObject", model, location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY(), rotation.getZ(), scale.getX(), scale.getY(), scale.getZ()).get()[0].getAsInt();
            } else {
                id = server.call("CreateObject", model, location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY(), rotation.getZ()).get()[0].getAsInt();
            }
        } else {
            id = server.call("CreateObject", model, location.getX(), location.getY(), location.getZ()).get()[0].getAsInt();
        }
        if(this.id != 0){
            server.call("SetObjectDimension", id, this.id);
        }
        WorldObject object = new SimpleWorldObject(this, id, model);
        server.getObjects().add(object);
        return object;
    }

    public List<WorldObject> getObjects() {
        return server.getObjects().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Text3D spawnText3D(Location location, Vector3d rotation, double size, String text) {
        Text3D text3d = new SimpleText3D(this, server.call("CreateText3D", text, size, location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY(), rotation.getZ()).get()[0].getAsInt());
        if(this.id != 0){
            server.call("SetText3DDimension", id, this.id);
        }
        server.getText3Ds().add(text3d);
        return text3d;
    }

    public List<Text3D> getText3Ds() {
        return server.getText3Ds().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Pickup spawnPickup(Location location, int model) {
        Pickup pickup = new SimplePickup(this, server.call("CreatePickup", model, location.getX(), location.getY(), location.getZ()).get()[0].getAsInt());
        if(this.id != 0){
            server.call("SetPickupDimension", id, this.id);
        }
        server.getPickups().add(pickup);
        return pickup;
    }

    public List<Pickup> getPickups() {
        return server.getPickups().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Light spawnLight(Location location, LightType type, double intensity, Vector3d rotation) {
        int id;
        if (rotation != null) {
            id = server.call("CreateLight", type.getValue(), intensity, location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY(), rotation.getZ()).get()[0].getAsInt();
        } else {
            id = server.call("CreateLight", type.getValue(), intensity, location.getX(), location.getY(), location.getZ()).get()[0].getAsInt();
        }
        if(this.id != 0){
            server.call("SetLightDimension", id, this.id);
        }
        Light light = new SimpleLight(this, id);
        server.getLights().add(light);
        return light;
    }

    public List<Light> getLights() {
        return server.getLights().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Door createDoor(Location location, DoorModel model, boolean interactable){
        int id = server.call("CreateDoor", model.getId(), location.getX(), location.getY(), location.getZ(), location.getHeading(), interactable).get()[0].getAsInt();
        if(this.id != 0){
            server.call("SetDoorDimension", id, this.id);
        }
        Door door = new SimpleDoor(this, id, model, location);
        server.getDoors().add(door);
        return door;
    }

    public List<Door> getDoors(){
        return server.getDoors().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public void createExplosion(Location location, ExplosionType type, boolean sound, double camShakeRadius, double radialForce) {
        server.call("CreateExplosion", type.getIdentifier(), location.getX(), location.getY(), location.getZ(), id, sound, camShakeRadius, radialForce);
    }

    public void createExplosion(Location location, ExplosionType type, boolean sound, double camShakeRadius) {
        server.call("CreateExplosion", type.getIdentifier(), location.getX(), location.getY(), location.getZ(), id, sound, camShakeRadius);
    }

    public void createExplosion(Location location, ExplosionType type, boolean sound) {
        server.call("CreateExplosion", type.getIdentifier(), location.getX(), location.getY(), location.getZ(), id, sound);
    }

    public void createExplosion(Location location, ExplosionType type) {
        server.call("CreateExplosion", type.getIdentifier(), location.getX(), location.getY(), location.getZ(), id);
    }

    public void spawnFirework(Location location, Vector3d rotation, int type){
        getPlayers().forEach(player -> player.spawnFirework(location, rotation, type));
    }

}
