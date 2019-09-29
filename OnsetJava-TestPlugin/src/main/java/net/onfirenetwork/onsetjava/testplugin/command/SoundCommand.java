package net.onfirenetwork.onsetjava.testplugin.command;

import net.onfirenetwork.onsetjava.api.CommandExecutor;
import net.onfirenetwork.onsetjava.api.entity.Player;

public class SoundCommand implements CommandExecutor {
    public void onCommand(String cmd, Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("/sound <file/url>");
            return;
        }
        player.createSound(args[0]);
        player.sendMessage("Sound started!");
    }
}
