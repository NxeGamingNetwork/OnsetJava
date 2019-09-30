package net.onfirenetwork.onsetjava.api.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Location extends Vector3i {
    double heading;

    public Location(int x, int y, int z, double heading) {
        super(x, y, z);
        this.heading = heading;
    }

    public Location(int x, int y, int z) {
        super(x, y, z);
        this.heading = 0;
    }

    public Location(Vector3i vector, double heading) {
        super(vector.getX(), vector.getY(), vector.getZ());
        this.heading = heading;
    }
}
