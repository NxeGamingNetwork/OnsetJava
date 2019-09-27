package net.onfirenetwork.onsetjava.simple.entity;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleVehicle implements Vehicle {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    public void remove(){
        dimension.getServer().getVehicles().remove(this);
        dimension.getServer().call("DestroyVehicle", id).get();
    }
    public Location getLocation(){
        JsonElement[] loc = dimension.getServer().call("GetVehicleLocation", id).get();
        double heading = dimension.getServer().call("GetVehicleHeading", id).get()[0].getAsDouble();
        return new Location(loc[0].getAsDouble(), loc[1].getAsDouble(), loc[2].getAsDouble(), heading);
    }
    public void setLocation(Location location){
        dimension.getServer().call("SetVehicleLocation", id, location.getX(), location.getY(), location.getZ()).get();
        dimension.getServer().call("SetVehicleHeading", id, location.getHeading()).get();
    }
    public double getHealth(){
        return dimension.getServer().call("GetVehicleHealth", id).get()[0].getAsDouble();
    }
    public void setHealth(double health){
        dimension.getServer().call("SetVehicleHealth", id, health);
    }
    public void setLicensePlate(String licensePlate){
        dimension.getServer().call("SetLicensePlate", id, licensePlate);
    }
    public void enterPlayer(Player player, int seat){
        player.enterVehicle(this, seat);
    }
    public void enterPlayer(Player player){
        player.enterVehicle(this);
    }
}
