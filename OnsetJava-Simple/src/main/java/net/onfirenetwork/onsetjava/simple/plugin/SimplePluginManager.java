package net.onfirenetwork.onsetjava.simple.plugin;

import net.onfirenetwork.onsetjava.api.plugin.Plugin;
import net.onfirenetwork.onsetjava.api.plugin.PluginInfo;
import net.onfirenetwork.onsetjava.api.plugin.PluginManager;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;
import net.onfirenetwork.onsetjava.simple.installer.Installer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SimplePluginManager implements PluginManager {

    private SimpleOnsetServer server;
    private List<Plugin> plugins = new ArrayList<>();
    private Map<Plugin, File> files = new HashMap<>();

    public SimplePluginManager(SimpleOnsetServer server){
        this.server = server;
    }

    public void load(File pluginFolder){
        new Installer().install(pluginFolder);
        List<File> pluginFiles = new ArrayList<>();
        for(File file : pluginFolder.listFiles()){
            if(file.isDirectory())
                continue;
            if(!file.getName().endsWith(".jar"))
                continue;
            pluginFiles.add(file);
        }
        try {
            URL[] urls = new URL[pluginFiles.size()];
            for(int i=0; i<urls.length; i++){
                urls[i] = pluginFiles.get(i).toURI().toURL();
            }
            URLClassLoader classLoader = new URLClassLoader(urls);
            server.print("Found "+pluginFiles.size()+" Plugin Files!");
            for(File file: pluginFiles){
                try {
                    Class<Plugin> mainClass = null;
                    JarFile jf = new JarFile(file);
                    Enumeration<JarEntry> en = jf.entries();
                    while (en.hasMoreElements()) {
                        JarEntry element = en.nextElement();
                        if (element.getName().endsWith(".class")) {
                            Class<?> clazz = classLoader.loadClass(element.getName().replace("/", ".").substring(0, element.getName().length() - 6));
                            if (Arrays.asList(clazz.getInterfaces()).contains(Plugin.class)) {
                                mainClass = (Class<Plugin>) clazz;
                            }
                        }
                    }
                    if (mainClass != null) {
                        Plugin instance = mainClass.newInstance();
                        plugins.add(instance);
                        files.put(instance, file);
                        PluginInfo info = instance.info();
                        instance.onLoad();
                        server.print("Loaded "+info);
                    }
                }catch (Exception ex){
                    server.print("Failed to load '"+file.getName()+"'!");
                }
            }
        } catch (MalformedURLException ex) {}
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public Plugin getPlugin(String name) {
        for(Plugin plugin : plugins){
            if(plugin.info().getName().equalsIgnoreCase(name)){
                return plugin;
            }
        }
        return null;
    }

    public File getFile(Plugin plugin){
        return files.get(plugin);
    }

    public String getResourceId(Plugin plugin){
        File file = getFile(plugin);
        if(file == null)
            return null;
        return makeResourceId(file.getName());
    }

    public static String makeResourceId(String fileName){
        String id = fileName.substring(0, fileName.length()-4);
        id = id.toLowerCase(Locale.ENGLISH);
        id = id.replace(".", "-");
        for(int i=0; i<id.length()-1; i++){
            if(id.charAt(i) == '-' && Character.isDigit(id.charAt(i+1))){
                id = id.substring(0, i);
                break;
            }
        }
        return id;
    }

}
