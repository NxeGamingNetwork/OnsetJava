package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.util.Vector2d;

public interface TextBox {
    int getId();

    void setText(String text);

    void setAlignment(double x, double y);

    default void setAlignment(Vector2d alignment) {
        setAlignment(alignment.getX(), alignment.getY());
    }

    void setAnchors(double minX, double minY, double maxX, double maxY);

    default void setAnchors(Vector2d min, Vector2d max) {
        setAnchors(min.getX(), min.getY(), max.getX(), max.getY());
    }

    void remove();

    enum Justification {
        LEFT,
        CENTER,
        RIGHT
    }
}
