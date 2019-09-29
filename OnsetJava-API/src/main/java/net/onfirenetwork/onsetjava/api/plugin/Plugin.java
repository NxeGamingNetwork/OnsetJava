package net.onfirenetwork.onsetjava.api.plugin;

import net.onfirenetwork.onsetjava.api.OnsetJava;

import java.io.File;

public interface Plugin {
    default void onLoad() {}
    default void onEnable() {}
    default void onDisable() {}
    PluginInfo info();
    default File getFile(){
        return OnsetJava.getServer().getPluginManager().getFile(this);
    }
    default String getResourceId(){
        return OnsetJava.getServer().getPluginManager().getResourceId(this);
    }
    default String getResourcePath(String name){
        return "plugin/"+getResourceId()+"/"+name;
    }
    default String getResourceUrl(String name){
        return "http://asset/java/"+getResourcePath(name);
    }

}
