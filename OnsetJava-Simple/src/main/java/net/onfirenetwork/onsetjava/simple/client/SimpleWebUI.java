package net.onfirenetwork.onsetjava.simple.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.client.WebUI;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.enums.WebVisibility;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleWebUI implements WebUI {
    Player player;
    @Getter
    int id;
    WebVisibility visibility = WebVisibility.VISIBLE;
    public SimpleWebUI(Player player, int id){
        this.player = player;
        this.id = id;
    }
    public void setUrl(String url){
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "SetWebURL", id, url);
    }
    public void executeJS(String js){
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "ExecuteWebJS", id, js);
    }
    public void setAlignment(double x, double y){
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "SetWebAlignment", id, x, y);
    }
    public void setAnchors(double minX, double minY, double maxX, double maxY){
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "SetWebAnchors", id, minX, minY, maxX, maxY);
    }
    public void setVisibility(WebVisibility visibility){
        this.visibility = visibility;
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "SetWebVisibility", id, visibility.getIdentifier());
    }
    public WebVisibility getVisibility(){
        return visibility;
    }
    public void remove(){
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        player.getWebUIs().remove(this);
        server.callClient(player.getId(), "DestroyWebUI", id).get();
    }
}
