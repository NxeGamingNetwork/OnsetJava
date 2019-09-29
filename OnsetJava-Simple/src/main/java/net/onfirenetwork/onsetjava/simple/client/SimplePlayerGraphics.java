package net.onfirenetwork.onsetjava.simple.client;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.client.PlayerGraphics;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePlayerGraphics implements PlayerGraphics {
    @Getter
    SimplePlayer player;
    int fov = 90;
    Vector2d chatLocation;

    public SimplePlayerGraphics(SimplePlayer player) {
        this.player = player;
    }

    public int getFOV() {
        return fov;
    }

    public void setFOV(int fov) {
        this.fov = fov;
        call("SetPlayerFOV", fov);
    }

    public void showChat(boolean show) {
        call("ShowChat", show);
    }

    public void setChatLocation(Vector2d location) {
        this.chatLocation = location;
        call("SetChatLocation", location.getX(), location.getY());
    }

    public Vector2d getChatLocation() {
        if (chatLocation == null) {
            JsonElement[] returns = call("GetChatLocation").get();
            chatLocation = new Vector2d(returns[0].getAsInt(), returns[1].getAsInt());
        }
        return chatLocation;
    }

    public Vector2d getScreenSize() {
        JsonElement[] returns = call("GetScreenSize").get();
        return new Vector2d(returns[0].getAsInt(), returns[1].getAsInt());
    }

    private Completable<JsonElement[]> call(String name, Object... params) {
        return player.getDimension().getServer().callClient(player.getId(), name, params);
    }
}
