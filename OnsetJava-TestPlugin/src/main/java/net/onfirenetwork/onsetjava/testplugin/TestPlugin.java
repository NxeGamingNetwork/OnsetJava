package net.onfirenetwork.onsetjava.testplugin;

import net.onfirenetwork.onsetjava.api.plugin.Plugin;
import net.onfirenetwork.onsetjava.api.plugin.PluginInfo;
import net.onfirenetwork.onsetjava.testplugin.command.GiveWeaponCommand;
import net.onfirenetwork.onsetjava.testplugin.command.SoundCommand;
import net.onfirenetwork.onsetjava.testplugin.listener.ATMListener;
import net.onfirenetwork.onsetjava.testplugin.listener.WelcomeListener;

public class TestPlugin implements Plugin {

    public void onEnable() {
        getServer().getEventBus().listen(new ATMListener(this));
        getServer().getEventBus().listen(new WelcomeListener());
        getServer().registerCommand("sound", new SoundCommand());
        getServer().registerCommand("givew", new GiveWeaponCommand());
        getServer().registerCommand("dbg", (cmd, player, args) -> {

        });
        getServer().registerKeys("E");
    }

    public PluginInfo info() {
        return new PluginInfo("TestPlugin", "1.0", "Onfire Network");
    }
}
