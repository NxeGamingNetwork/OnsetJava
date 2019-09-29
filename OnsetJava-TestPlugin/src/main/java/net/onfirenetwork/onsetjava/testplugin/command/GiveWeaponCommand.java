package net.onfirenetwork.onsetjava.testplugin.command;

import net.onfirenetwork.onsetjava.api.CommandExecutor;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.enums.WeaponModel;

public class GiveWeaponCommand implements CommandExecutor {
    public void onCommand(String cmd, Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("/givew <id>");
            return;
        }
        try {
            int weaponId = Integer.parseInt(args[0]);
            player.setWeapon(1, WeaponModel.getModel(weaponId), 100, true);
            player.sendMessage("Gave you the weapon!");
        } catch (Exception e) {
            player.sendMessage("The id has to be a number!");
        }
    }
}
