package net.onfirenetwork.onsetjava.api.util;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Location extends Vector3d {
    double heading;
    public Location(double x, double y, double z, double heading){
        super(x, y, z);
        this.heading = heading;
    }
    public Location(double x, double y, double z){
        super(x, y, z);
        this.heading = 0;
    }
    public Location(Vector3d vector, double heading) {
        super(vector.getX(), vector.getY(), vector.getZ());
        this.heading = heading;
    }
}
