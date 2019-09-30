package net.onfirenetwork.onsetjava.api.plugin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class PluginInfo {

    String name;
    String version;
    String author;
    String[] dependencies;

    public PluginInfo(String name, String version, String author, String... dependencies){
        this.name = name;
        this.version = version;
        this.author = author;
        this.dependencies = dependencies;
    }

    public String toString() {
        return name + " (" + version + " by " + author + ")";
    }

}
