package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.enums.WebVisibility;
import net.onfirenetwork.onsetjava.api.util.Vector2d;

public interface WebUI {
    int getId();
    void setUrl(String url);
    void executeJS(String js);
    void setAlignment(Vector2d alignment);
    void setAlignment(double x, double y);
    void setAnchors(Vector2d min, Vector2d max);
    void setAnchors(double minX, double minY, double maxX, double maxY);
    void setVisibility(WebVisibility visibility);
    WebVisibility getVisibility();
    void remove();
}
