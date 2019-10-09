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
public class PlayerExitVehicleEvent extends Event implements Cancellable {
    Player player;
    Vehicle vehicle;
    @Setter
    boolean cancelled = false;
    public PlayerExitVehicleEvent(Player player, Vehicle vehicle){
        this.player = player;
        this.vehicle = vehicle;
    }
}
