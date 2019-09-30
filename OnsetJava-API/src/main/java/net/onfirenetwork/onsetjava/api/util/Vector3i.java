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
}
