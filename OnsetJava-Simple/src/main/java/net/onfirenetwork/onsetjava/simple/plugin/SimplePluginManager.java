package net.onfirenetwork.onsetjava.simple.plugin;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.plugin.Plugin;
import net.onfirenetwork.onsetjava.api.plugin.PluginInfo;
import net.onfirenetwork.onsetjava.api.plugin.PluginManager;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@RequiredArgsConstructor
public class SimplePluginManager implements PluginManager {
    @NonNull
    private SimpleOnsetServer server;
    @Getter
    private List<Plugin> plugins = new ArrayList<>();
    private Map<Plugin, PluginInfo> infos = new HashMap<>();
    private Map<Plugin, File> files = new HashMap<>();

    public void load(File pluginFolder) {
        List<File> pluginFiles = new ArrayList<>();
        for (File file : pluginFolder.listFiles()) {
            if (file.isDirectory())
                continue;
            if (!file.getName().endsWith(".jar"))
                continue;
            pluginFiles.add(file);
        }
        try {
            URL[] urls = new URL[pluginFiles.size()];
            for (int i = 0; i < urls.length; i++) {
                urls[i] = pluginFiles.get(i).toURI().toURL();
            }
            URLClassLoader classLoader = new URLClassLoader(urls);
            server.print("Found " + pluginFiles.size() + " Plugin Files!");
            for (File file : pluginFiles) {
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
                        Plugin instance = mainClass.getConstructor().newInstance();
                        plugins.add(instance);
                        files.put(instance, file);
                        infos.put(instance, instance.info());
                    }
                } catch (Exception ex) {
                    server.print("Failed to load '" + file.getName() + "'!");
                }
            }
        } catch (MalformedURLException ex) {
        }
        int lastSize = -1;
        List<String> loaded = new ArrayList<>();
        while (loaded.size() < plugins.size() && lastSize != loaded.size()){
            for(Plugin plugin : plugins){
                PluginInfo info = infos.get(plugin);
                if(loaded.contains(info.getName()))
                    continue;
                boolean con = false;
                for(String d : info.getDependencies()){
                    if(!loaded.contains(d)){
                        con = true;
                        break;
                    }
                }
                if(con)
                    continue;
                plugin.onLoad();
                loaded.add(info.getName());
            }
            lastSize = loaded.size();
        }
        if(loaded.size() < plugins.size()){
            for(Plugin plugin : new ArrayList<>(plugins)){
                PluginInfo info = infos.get(plugin);
                if(!loaded.contains(info.getName())){
                    List<String> missing = new ArrayList<>();
                    for(String d : info.getDependencies()){
                        if(!loaded.contains(d)){
                            missing.add(d);
                        }
                    }
                    plugins.remove(plugin);
                    infos.remove(plugin);
                    files.remove(plugin);
                    OnsetJava.getServer().print("Could not load '"+info.getName()+"' (missing: "+String.join(", ", missing)+")");
                }
            }
        }
    }

    public Plugin getPlugin(String name) {
        return plugins.stream().filter(plugin -> plugin.info().getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public File getFile(Plugin plugin) {
        return files.get(plugin);
    }

    public PluginInfo getInfo(Plugin plugin){
        return infos.get(plugin);
    }

    public String getResourceId(Plugin plugin) {
        File file = getFile(plugin);
        if (file == null)
            return null;
        return makeResourceId(file.getName());
    }

    public static String makeResourceId(String fileName) {
        String id = fileName.substring(0, fileName.length() - 4);
        id = id.toLowerCase(Locale.ENGLISH);
        id = id.replace(".", "-");
        for (int i = 0; i < id.length() - 1; i++) {
            if (id.charAt(i) == '-' && Character.isDigit(id.charAt(i + 1))) {
                id = id.substring(0, i);
                break;
            }
        }
        id = md5(id).substring(0, 8);
        return id;
    }

    private static String md5(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            return Base64.getEncoder().encodeToString(md.digest(source.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
