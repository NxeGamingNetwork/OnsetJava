package net.onfirenetwork.onsetjava.api.event.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.event.Cancellable;
import net.onfirenetwork.onsetjava.api.event.Event;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PlayerEnterVehicleEvent extends Event implements Cancellable {
    Player player;
    Vehicle vehicle;
    int seat;
    @Setter
    boolean cancelled = false;
    public PlayerEnterVehicleEvent(Player player, Vehicle vehicle, int seat){
        this.player = player;
        this.vehicle = vehicle;
        this.seat = seat;
    }
}
