package net.onfirenetwork.onsetjava.api.plugin;

public interface Plugin {
    default void onLoad() {}
    default void onEnable() {}
    default void onDisable() {}
    PluginInfo info();
}
