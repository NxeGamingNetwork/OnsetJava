package net.onfirenetwork.onsetjava.api;

import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.entity.VehicleModel;
import net.onfirenetwork.onsetjava.api.util.Location;

import java.util.List;

public interface OnsetServer {
    void broadcast(String message);
    Player getPlayer(int id);
    List<Player> getPlayers();
    Vehicle getVehicle(int id);
    List<Vehicle> getVehicles();
    Vehicle spawnVehicle(Location location, VehicleModel model);
}
