package net.onfirenetwork.onsetjava.api.entity;

import lombok.Getter;

public class VehicleModel {

    public static final VehicleModel SEDAN = new VehicleModel(1);
    public static final VehicleModel SEDAN_TAXI = new VehicleModel(2);
    public static final VehicleModel SEDAN_POLICE = new VehicleModel(3);
    public static final VehicleModel ROLLS_ROYCE = new VehicleModel(4);
    public static final VehicleModel SALOON = new VehicleModel(5);
    public static final VehicleModel NASCAR = new VehicleModel(6);
    public static final VehicleModel PICKUP = new VehicleModel(7);
    public static final VehicleModel AMBULANCE = new VehicleModel(8);
    public static final VehicleModel GARBAGE_TRUCK = new VehicleModel(9);
    public static final VehicleModel HELICOPTER = new VehicleModel(10);
    public static final VehicleModel COUPE = new VehicleModel(11);
    public static final VehicleModel RALLY = new VehicleModel(12);
    public static final VehicleModel HEAVY = new VehicleModel(13);
    public static final VehicleModel HEAVY_CAMO = new VehicleModel(14);
    public static final VehicleModel HEAVY_RESCUE = new VehicleModel(15);
    public static final VehicleModel HEAVY_MILITARY = new VehicleModel(16);
    public static final VehicleModel CARGO_TRUCK = new VehicleModel(17);
    public static final VehicleModel CARGO_TRUCK_CAMO = new VehicleModel(18);
    public static final VehicleModel SEDAN_ONECOLOR = new VehicleModel(19);
    public static final VehicleModel HELICOPTER_ONECOLOR = new VehicleModel(20);
    public static final VehicleModel HUMVEE = new VehicleModel(21);
    public static final VehicleModel LIGHT_TRUCK = new VehicleModel(22);
    public static final VehicleModel LIGHT_TRUCK_CAMO = new VehicleModel(23);

    @Getter
    private int id;

    private VehicleModel(int id){
        this.id = id;
    }

    public static VehicleModel[] values(){
        return new VehicleModel[]{
            SEDAN, SEDAN_TAXI, SEDAN_POLICE, ROLLS_ROYCE, SALOON, NASCAR, PICKUP, AMBULANCE, GARBAGE_TRUCK, HELICOPTER, COUPE, RALLY, HEAVY, HEAVY_CAMO, HEAVY_RESCUE, HEAVY_MILITARY, CARGO_TRUCK, CARGO_TRUCK_CAMO, SEDAN_ONECOLOR, HELICOPTER_ONECOLOR, HUMVEE, LIGHT_TRUCK, LIGHT_TRUCK_CAMO
        };
    }
    public static VehicleModel getModel(int id){
        return new VehicleModel(id);
    }
}
