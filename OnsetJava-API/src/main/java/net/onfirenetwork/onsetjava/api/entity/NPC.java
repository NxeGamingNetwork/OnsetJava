package net.onfirenetwork.onsetjava.api.entity;

import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.enums.CharacterAnimation;
import net.onfirenetwork.onsetjava.api.enums.CharacterModel;
import net.onfirenetwork.onsetjava.api.util.Location;

public interface NPC extends HitEntity, StickyEntity {
    int getId();

    Dimension getDimension();

    CharacterModel getModel();

    Location getLocation();

    void setLocation(Location location);

    void setHealth(double health);

    double getHealth();

    void startAnimation(CharacterAnimation animation, boolean loop);

    default void startAnimation(CharacterAnimation animation) {
        startAnimation(animation, false);
    }

    void stopAnimation();

    void setTargetLocation(Location location, double speed);

    void setTargetLocation(Location location);

    void followPlayer(Player player, double speed);

    void followPlayer(Player player);

    void followVehicle(Vehicle vehicle, double speed);

    void followVehicle(Vehicle vehicle);

    void remove();

    void setDimension(Dimension dimension);

    void setAttribute(String key, Object value);

    <T> T getAttribute(String key);

}
