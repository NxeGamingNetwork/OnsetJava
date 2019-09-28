package net.onfirenetwork.onsetjava.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.entity.*;
import net.onfirenetwork.onsetjava.api.enums.CharacterModel;
import net.onfirenetwork.onsetjava.api.enums.LightType;
import net.onfirenetwork.onsetjava.api.enums.VehicleModel;
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
        NPC npc = new SimpleNPC(this, server.call("CreateNPC", model.getId(), location.getX(), location.getY(), location.getZ(), location.getHeading()).get()[0].getAsInt());
        server.getNPCs().add(npc);
        return npc;
    }
    public List<NPC> getNPCs(){
        return server.getNPCs().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public WorldObject spawnObject(Location location, int model, Vector3d rotation, Vector3d scale){
        int id;
        if(rotation != null){
            if(scale != null){
                id = server.call("CreateObject", model, location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY(), rotation.getZ(), scale.getX(), scale.getY(), scale.getZ()).get()[0].getAsInt();
            }else{
                id = server.call("CreateObject", model, location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY(), rotation.getZ()).get()[0].getAsInt();
            }
        }else{
            id = server.call("CreateObject", model, location.getX(), location.getY(), location.getZ()).get()[0].getAsInt();
        }
        WorldObject object = new SimpleWorldObject(this, id);
        server.getObjects().add(object);
        return object;
    }

    public List<WorldObject> getObjects(){
        return server.getObjects().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Text3D spawnText3D(Location location, Vector3d rotation, double size, String text){
        Text3D text3d = new SimpleText3D(this, server.call("CreateText3D", text, size, location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY(), rotation.getZ()).get()[0].getAsInt());
        server.getText3Ds().add(text3d);
        return text3d;
    }
    public List<Text3D> getText3Ds(){
        return server.getText3Ds().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Pickup spawnPickup(Location location, int model){
        Pickup pickup = new SimplePickup(this, server.call("CreatePickup", model, location.getX(), location.getY(), location.getZ()).get()[0].getAsInt());
        server.getPickups().add(pickup);
        return pickup;
    }
    public List<Pickup> getPickups(){
        return server.getPickups().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }

    public Light spawnLight(Location location, LightType type, double intensity, Vector3d rotation){
        int id;
        if(rotation != null){
            id = server.call("CreateLight", type.getValue(), intensity, location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY(), rotation.getZ()).get()[0].getAsInt();
        }else{
            id = server.call("CreateLight", type.getValue(), intensity, location.getX(), location.getY(), location.getZ()).get()[0].getAsInt();
        }
        Light light = new SimpleLight(this, id);
        server.getLights().add(light);
        return light;
    }

    public List<Light> getLights(){
        return server.getLights().stream().filter(entity -> entity.getDimension().getId() == id).collect(Collectors.toList());
    }
}
