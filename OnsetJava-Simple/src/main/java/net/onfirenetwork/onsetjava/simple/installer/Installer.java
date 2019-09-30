package net.onfirenetwork.onsetjava.simple.installer;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.onfirenetwork.onsetjava.simple.plugin.SimplePluginManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Installer {

    public void install(File pluginFolder) {
        File packageFolder = new File("packages/java");
        if (packageFolder.exists()) {
            deleteRecursive(packageFolder);
        }
        extractBins(packageFolder);
        JsonArray files = new JsonArray();
        for (File file : getPlugins(pluginFolder)) {
            String id = SimplePluginManager.makeResourceId(file.getName());
            extract(file, new File(packageFolder, "" + id)).forEach(name -> {
                files.add("" + id + "/" + name);
            });
        }
        makePackageConfig(packageFolder, files);
    }

    private void makePackageConfig(File packageFolder, JsonArray files) {
        JsonArray client = new JsonArray();
        client.add("common/json.lua");
        client.add("common/helper.lua");
        client.add("client/get_global.lua");
        client.add("client/pcall.lua");
        client.add("client/client.lua");
        JsonArray server = new JsonArray();
        server.add("common/json.lua");
        server.add("common/helper.lua");
        server.add("server/get_global.lua");
        server.add("server/lua_io.lua");
        server.add("server/server.lua");
        JsonObject config = new JsonObject();
        config.addProperty("author", "Onfire Network");
        config.addProperty("version", "1.0");
        config.add("files", files);
        config.add("client_scripts", client);
        config.add("server_scripts", server);
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(config);
        try {
            FileOutputStream fos = new FileOutputStream(new File(packageFolder, "package.json"));
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.flush();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void extractBins(File packageFolder) {
        extractBin(packageFolder, "client/get_global.lua");
        extractBin(packageFolder, "client/pcall.lua");
        extractBin(packageFolder, "client/client.lua");
        extractBin(packageFolder, "common/helper.lua");
        extractBin(packageFolder, "common/json.lua");
        extractBin(packageFolder, "server/get_global.lua");
        extractBin(packageFolder, "server/lua_io.lua");
        extractBin(packageFolder, "server/server.lua");
    }

    private void deleteRecursive(File file) {
        if (!file.exists())
            return;
        if (file.isFile()) {
            file.delete();
            return;
        }
        for (File f : file.listFiles()) {
            deleteRecursive(f);
        }
        file.delete();
    }

    private void extractBin(File packageFolder, String name) {
        extractResource("lua/" + name, new File(packageFolder, name));
    }

    private void extractResource(String name, File out) {
        try {
            mkdir(out.getAbsoluteFile().getParentFile());
            InputStream in = getClass().getClassLoader().getResourceAsStream(name);
            FileOutputStream fos = new FileOutputStream(out);
            transfer(in, fos);
        } catch (Exception ex) {
        }
    }

    private List<String> extract(File jarFile, File folder) {
        List<String> names = new ArrayList<>();
        try {
            JarFile jf = new JarFile(jarFile);
            Enumeration<JarEntry> en = jf.entries();
            while (en.hasMoreElements()) {
                JarEntry element = en.nextElement();
                if (!element.getName().startsWith("files/") || element.isDirectory())
                    continue;
                String name = element.getName().substring(6);
                names.add(name);
                File targetFile = new File(folder, name);
                mkdir(targetFile.getAbsoluteFile().getParentFile());
                InputStream in = jf.getInputStream(element);
                FileOutputStream fos = new FileOutputStream(targetFile);
                transfer(in, fos);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return names;
    }

    private void mkdir(File file) {
        if (file.exists()) {
            return;
        }
        File parent = file.getAbsoluteFile().getParentFile();
        if (!parent.exists()) {
            mkdir(parent);
        }
        file.mkdir();
    }

    private List<File> getPlugins(File pluginFolder) {
        List<File> pluginFiles = new ArrayList<>();
        if (!pluginFolder.exists())
            return pluginFiles;
        for (File file : pluginFolder.listFiles()) {
            if (file.isDirectory())
                continue;
            if (!file.getName().endsWith(".jar"))
                continue;
            pluginFiles.add(file);
        }
        return pluginFiles;
    }

    private void transfer(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
                out.flush();
            }
            in.close();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
