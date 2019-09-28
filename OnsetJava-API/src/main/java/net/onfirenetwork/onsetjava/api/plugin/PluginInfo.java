package net.onfirenetwork.onsetjava.api.plugin;

import java.util.Objects;

public class PluginInfo {

    private String name;
    private String version;
    private String author;

    public PluginInfo(String name, String version, String author) {
        this.name = name;
        this.version = version;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PluginInfo)) return false;
        PluginInfo modInfo = (PluginInfo) o;
        return Objects.equals(this.name, modInfo.name) &&
                Objects.equals(this.version, modInfo.version);
    }

    public int hashCode() {
        return Objects.hash(name, version, author);
    }

    public String toString(){
        return name + " ("+version+" by "+author+")";
    }

}
