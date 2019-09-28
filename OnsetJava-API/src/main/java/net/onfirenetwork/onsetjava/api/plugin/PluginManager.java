package net.onfirenetwork.onsetjava.api.plugin;

import java.util.List;

public interface PluginManager {
    List<Plugin> getPlugins();
    Plugin getPlugin(String name);
}
