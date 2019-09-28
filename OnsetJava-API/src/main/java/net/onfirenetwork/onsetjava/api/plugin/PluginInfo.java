package net.onfirenetwork.onsetjava.api.plugin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PluginInfo {

    String name;
    String version;
    String author;

    public String toString(){
        return name + " ("+version+" by "+author+")";
    }

}
