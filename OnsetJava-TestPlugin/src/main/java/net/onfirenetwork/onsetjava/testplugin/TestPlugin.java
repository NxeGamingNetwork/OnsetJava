package net.onfirenetwork.onsetjava.testplugin;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.api.enums.WeaponModel;
import net.onfirenetwork.onsetjava.api.plugin.Plugin;
import net.onfirenetwork.onsetjava.api.plugin.PluginInfo;
import net.onfirenetwork.onsetjava.testplugin.command.SoundCommand;
import net.onfirenetwork.onsetjava.testplugin.listener.ATMListener;
import net.onfirenetwork.onsetjava.testplugin.listener.WelcomeListener;

public class TestPlugin implements Plugin {

    public void onEnable() {
        OnsetJava.getServer().getEventBus().listen(new ATMListener());
        OnsetJava.getServer().getEventBus().listen(new WelcomeListener());
        OnsetJava.getServer().registerCommand("sound", new SoundCommand());
        OnsetJava.getServer().registerCommand("givew", (command, player, args) -> {
            if (args.length == 0) {
                player.sendMessage("/givew <id>");
                return;
            }
            player.setWeapon(1, WeaponModel.getModel(Integer.parseInt(args[0])), 100, true);
            player.sendMessage("Gave you the weapon!");
        });
        OnsetJava.getServer().registerKeys("E");
    }

    public PluginInfo info() {
        return new PluginInfo("TestPlugin", "1.0", "Onfire Network");
    }
}
