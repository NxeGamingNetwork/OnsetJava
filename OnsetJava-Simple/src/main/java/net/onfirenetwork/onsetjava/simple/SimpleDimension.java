package net.onfirenetwork.onsetjava.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.OnsetServer;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.entity.enums.CharacterModel;
import net.onfirenetwork.onsetjava.api.entity.enums.LightType;
import net.onfirenetwork.onsetjava.api.entity.enums.VehicleModel;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.entity.SimpleVehicle;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SimpleDimension implements Dimension {

    @Getter
    private SimpleOnsetServer server;
    @Getter
    private int id;

    public List<Player> getPlayers(){
        return server.getPlayers().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Vehicle spawnVehicle(Location location, VehicleModel model){
        Vehicle vehicle = new SimpleVehicle(this, server.call("CreateVehicle", model.getId(), location.getX(), location.getY(), location.getZ()).get()[0].getAsInt());
        server.getVehicles().add(vehicle);
        return vehicle;
    }
    public List<Vehicle> getVehicles(){
        return server.getVehicles().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public NPC spawnNPC(Location location, CharacterModel model){
        return null;
    }
    public List<NPC> getNPCs(){
        return server.getNPCs().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public WorldObject spawnObject(Location location, int model, Vector3d rotation, Vector3d scale){
        return null;
    }
    public WorldObject spawnObject(Location location, int model, Vector3d rotation){
        return spawnObject(location, model, rotation, null);
    }
    public WorldObject spawnObject(Location location, int model){
        return spawnObject(location, model, null);
    }
    public List<WorldObject> getObjects(){
        return server.getObjects().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Text3D spawnText3D(Location location, Vector3d rotation, double size, String text){
        return null;
    }
    public List<Text3D> getText3Ds(){
        return server.getText3Ds().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Pickup spawnPickup(Location location, int model){
        return null;
    }
    public List<Pickup> getPickups(){
        return server.getPickups().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Light spawnLight(Location location, LightType type, double intensity, Vector3d rotation){
        return null;
    }
    public Light spawnLight(Location location, LightType type, double intensity){
        return spawnLight(location, type, intensity, null);
    }
    public List<Light> getLights(){
        return server.getLights().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }
}
