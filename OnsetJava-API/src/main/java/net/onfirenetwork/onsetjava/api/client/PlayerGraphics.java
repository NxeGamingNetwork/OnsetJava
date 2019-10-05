package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.enums.PostEffect;
import net.onfirenetwork.onsetjava.api.util.*;

public interface PlayerGraphics {

    Player getPlayer();

    int getFOV();

    void setFOV(int fov);

    Completable<Vector2i> getScreenSize();

    void setCameraDistance(double distance);

    double getCameraDistance();

    void setCameraLocation(Location location);

    Completable<Location> getCameraLocation();

    void setCameraRotation(Vector3d rotation);

    Completable<Vector3d> getCameraRotation();

    void startCameraFade(double fromAlpha, double toAlpha, double duration, int color);

    void startCameraFade(double fromAlpha, double toAlpha, double duration);

    void stopCameraFade();

    void setCameraShakeLocation(Vector3d amplifier, Vector3d frequency);

    void setCameraShakeRotation(Vector3d amplifier, Vector3d frequency);

    void setCameraShakeFOV(double amplifier, double frequency);

    void playCameraShake(double duration, double blendInTime, double blendOutTime, double scale);

    void playCameraShake(double duration, double blendInTime, double blendOutTime);

    void playCameraShake(double duration);

    void stopCameraShake();

    void setPostEffect(PostEffect effect, float... value);


}
