package net.onfirenetwork.onsetjava.api.plugin;

import java.io.File;
import java.util.List;

public interface PluginManager {
    List<Plugin> getPlugins();

    Plugin getPlugin(String name);

    File getFile(Plugin plugin);

    PluginInfo getInfo(Plugin plugin);

    String getResourceId(Plugin plugin);
}
