package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

public interface WorldObject extends HitEntity {
    int getId();

    Dimension getDimension();

    int getModel();

    void setStreamDistance(double distance);

    void setLocation(Location location);

    Location getLocation();

    void setRotation(Vector3d rotation);

    Vector3d getRotation();

    void setScale(Vector3d scale);

    Vector3d getScale();

    boolean isMoving();

    void startMoving(Location location, double speed);

    void startMoving(Location location);

    void stopMoving();

    void setColor(int color);

    void setTexture(String file, int slot);

    void setTexture(String file);

    void setAnimatedTexture(String file, int rows, int columns, int slot);

    void setAnimatedTexture(String file, int rows, int columns);

    void remove();

    void setDimension(Dimension dimension);

    boolean isAttached();

    void setRotateAxis(Vector3d rotateAxis);

    void setAttribute(String key, Object value);

    <T> T getAttribute(String key);

}
