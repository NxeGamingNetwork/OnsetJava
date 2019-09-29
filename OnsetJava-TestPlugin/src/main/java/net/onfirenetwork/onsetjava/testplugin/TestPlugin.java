package net.onfirenetwork.onsetjava.testplugin;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.plugin.Plugin;
import net.onfirenetwork.onsetjava.api.plugin.PluginInfo;
import net.onfirenetwork.onsetjava.testplugin.command.SoundCommand;
import net.onfirenetwork.onsetjava.testplugin.listener.ATMListener;

public class TestPlugin implements Plugin {

    public void onEnable(){
        OnsetJava.getServer().getEventBus().listen(new ATMListener());
        OnsetJava.getServer().registerCommand("sound", new SoundCommand());
        OnsetJava.getServer().registerKeys("E");
    }

    public PluginInfo info() {
        return new PluginInfo("TestPlugin", "1.0", "Onfire Network");
    }
}
