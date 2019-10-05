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
public class Vector3d {
    double x;
    double y;
    double z;

    public double getDistance(Vector3d otherVector) {
        return Math.abs(Math.sqrt(Math.pow(x - otherVector.x, 2) + Math.pow(y - otherVector.y, 2) + Math.pow(z - otherVector.z, 2)));
    }
}
