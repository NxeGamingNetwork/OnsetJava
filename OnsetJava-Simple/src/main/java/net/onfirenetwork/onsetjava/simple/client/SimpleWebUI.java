package net.onfirenetwork.onsetjava.simple.client;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.client.WebUI;
import net.onfirenetwork.onsetjava.api.enums.WebVisibility;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleWebUI implements WebUI {
    SimplePlayer player;
    @Getter
    int id;
    WebVisibility visibility = WebVisibility.VISIBLE;

    public SimpleWebUI(SimplePlayer player, int id) {
        this.player = player;
        this.id = id;
    }

    public void setUrl(String url) {
        call("SetWebURL", id, url);
    }

    public void executeJS(String js) {
        call("ExecuteWebJS", id, js);
    }

    public void setAlignment(double x, double y) {
        call("SetWebAlignment", id, x, y);
    }

    public void setAnchors(double minX, double minY, double maxX, double maxY) {
        call("SetWebAnchors", id, minX, minY, maxX, maxY);
    }

    public void loadFile(String file) {
        call("LoadWebFile", id, file);
    }

    public void setLocation(double x, double y, double z) {
        if (z == 0) {
            call("SetWebLocation", id, x, y);
        } else {
            call("SetWebLocation", id, x, y, z);
        }
    }

    public void setRotation(double rx, double ry, double rz) {
        call("SetWebRotation", rx, ry, rz);
    }

    public void setSize(double x, double y) {
        call("SetWebSize", id, x, y);
    }

    public void setVisibility(WebVisibility visibility) {
        this.visibility = visibility;
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "SetWebVisibility", id, visibility.getIdentifier());
    }

    public WebVisibility getVisibility() {
        return visibility;
    }

    public void remove() {
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        player.getWebUIs().remove(this);
        server.callClient(player.getId(), "DestroyWebUI", id).get();
    }

    private Completable<JsonElement[]> call(String name, Object... params) {
        return player.getDimension().getServer().callClient(player.getId(), name, params);
    }
}
