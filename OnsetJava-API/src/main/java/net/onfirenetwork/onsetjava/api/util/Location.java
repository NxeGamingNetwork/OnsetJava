package net.onfirenetwork.onsetjava.api.util;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Location {
    @NonNull
    double x;
    @NonNull
    double y;
    @NonNull
    double z;
    double heading = 0;
}
