package net.onfirenetwork.onsetjava.api.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Vector3i {
    int x;
    int y;
    int z;
    public Location toLocation() {
        return new Location(x, y, z);
    }
    public double distance(Vector3i other){
        return Math.sqrt(
                Math.pow(Math.max(x, other.x) - Math.min(x, other.x), 2) +
                Math.pow(Math.max(y, other.y) - Math.min(y, other.y), 2) +
                Math.pow(Math.max(z, other.z) - Math.min(z, other.z), 2)
        );
    }
}
