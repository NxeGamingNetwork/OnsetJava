package net.onfirenetwork.onsetjava.simple.adapter;

import net.onfirenetwork.onsetjava.api.entity.Player;

public interface ActionAdapterListener {
    void onAction(InboundAction action);

    void onClientAction(Player player, InboundAction action);
}
