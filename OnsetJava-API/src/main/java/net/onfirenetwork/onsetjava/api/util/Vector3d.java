package net.onfirenetwork.onsetjava.api.util;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Vector3d {
    double x;
    double y;
    double z;
}
