package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.util.Vector2d;

public interface PlayerGraphics {
    Player getPlayer();

    int getFOV();

    void setFOV(int fov);

    void showChat(boolean show);

    void setChatLocation(Vector2d location);

    Vector2d getChatLocation();

    Vector2d getScreenSize();
}
