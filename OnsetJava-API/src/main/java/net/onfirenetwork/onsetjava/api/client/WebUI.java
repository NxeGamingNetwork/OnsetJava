package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.enums.WebVisibility;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.api.util.Vector2i;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

public interface WebUI {
    int getId();

    void setUrl(String url);

    void executeJS(String js);

    default void setAlignment(Vector2d alignment) {
        setAlignment(alignment.getX(), alignment.getY());
    }

    void setAlignment(double x, double y);

    default void setAnchors(Vector2d min, Vector2d max) {
        setAnchors(min.getX(), min.getY(), max.getX(), max.getY());
    }

    void setAnchors(double minX, double minY, double maxX, double maxY);

    void loadFile(String file);

    void setLocation(double x, double y, double z);

    void setLocation(int x, int y);

    default void setLocation(Vector2i position) {
        setLocation(position.getX(), position.getY());
    }

    default void setLocation(Vector3d location) {
        setLocation(location.getX(), location.getY(), location.getZ());
    }

    void setRotation(double rx, double ry, double rz);

    default void setRotation(Vector3d rotation) {
        setRotation(rotation.getX(), rotation.getY(), rotation.getZ());
    }

    void setSize(int x, int y);

    default void setSize(Vector2i size) {
        setSize(size.getX(), size.getY());
    }

    void setVisibility(WebVisibility visibility);

    WebVisibility getVisibility();

    void remove();
}
