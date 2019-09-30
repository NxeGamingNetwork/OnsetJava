package net.onfirenetwork.onsetjava.simple.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.client.TextBox;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleTextBox implements TextBox {
    Player player;
    @Getter
    int id;

    public void setText(String text) {
        ((SimpleDimension) player.getDimension()).getServer().callClient(player.getId(), "SetTextBoxText", id, text);
    }

    public void setAlignment(double x, double y) {
        ((SimpleDimension) player.getDimension()).getServer().callClient(player.getId(), "SetTextBoxAlignment", id, x, y);
    }

    public void setAnchors(double minX, double minY, double maxX, double maxY) {
        ((SimpleDimension) player.getDimension()).getServer().callClient(player.getId(), "SetTextBoxAnchors", id, minX, minY, maxX, maxY);
    }

    public void remove() {
        player.getTextBoxes().remove(this);
        ((SimpleDimension) player.getDimension()).getServer().callClient(player.getId(), "DestroyTextBox", id).get();
    }
}
