package net.onfirenetwork.onsetjava.api;

import net.onfirenetwork.onsetjava.api.entity.Player;

public interface CommandExecutor {
    void onCommand(String cmd, Player sender, String[] args);
}
