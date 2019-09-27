package net.onfirenetwork.onsetjava.simple.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePlayer implements Player {
    @Getter
    SimpleDimension dimension;
    @Getter
    int id;
    String steamId;
    String name;
}
