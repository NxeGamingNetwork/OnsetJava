package net.onfirenetwork.onsetjava.simple.client;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.client.PlayerGraphics;
import net.onfirenetwork.onsetjava.api.enums.PostEffect;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.Location;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.api.util.Vector3d;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePlayerGraphics implements PlayerGraphics {
    @Getter
    SimplePlayer player;
    int fov = 90;
    @Getter
    double cameraDistance = 350;
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

    public Vector2d getScreenSize() {
        JsonElement[] returns = call("GetScreenSize").get();
        return new Vector2d(returns[0].getAsInt(), returns[1].getAsInt());
    }

    public void setCameraDistance(double distance){
        cameraDistance = distance;
        call("SetPlayerCameraViewDistance", distance);
    }

    public void setCameraLocation(Location location){
        call("SetPlayerCameraLocation", location.getX(), location.getY(), location.getZ());
    }

    public Completable<Location> getCameraLocation(){
        Completable<Location> completable = new Completable<>();
        call("GetPlayerCameraLocation").then(ret -> {
            completable.complete(new Location(ret[0].getAsDouble(), ret[1].getAsDouble(), ret[2].getAsDouble()));
        });
        return completable;
    }

    public void setCameraRotation(Vector3d rotation){
        call("SetPlayerCameraRotation", rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public Completable<Vector3d> getCameraRotation(){
        Completable<Vector3d> completable = new Completable<>();
        call("GetPlayerCameraRotation").then(ret -> {
            completable.complete(new Vector3d(ret[0].getAsDouble(), ret[1].getAsDouble(), ret[2].getAsDouble()));
        });
        return completable;
    }

    public void startCameraFade(double fromAlpha, double toAlpha, double duration, int color){
        call("StartCameraFade", fromAlpha, toAlpha, duration, color);
    }

    public void startCameraFade(double fromAlpha, double toAlpha, double duration){
        call("StartCameraFade", fromAlpha, toAlpha, duration);
    }

    public void stopCameraFade(){
        call("StopCameraFade");
    }

    public void setCameraShakeLocation(Vector3d amplifier, Vector3d frequency){
        call("SetCameraShakeLocation", amplifier.getX(), frequency.getX(), amplifier.getY(), frequency.getY(), amplifier.getZ(), frequency.getZ());
    }

    public void setCameraShakeRotation(Vector3d amplifier, Vector3d frequency){
        call("SetCameraShakeRotation", amplifier.getX(), frequency.getX(), amplifier.getY(), frequency.getY(), amplifier.getZ(), frequency.getZ());
    }

    public void setCameraShakeFOV(double amplifier, double frequency){
        call("SetCameraShakeFOV", amplifier, frequency);
    }

    public void playCameraShake(double duration, double blendInTime, double blendOutTime, double scale){
        call("PlayCameraShake", duration, blendInTime, blendOutTime, scale);
    }

    public void playCameraShake(double duration, double blendInTime, double blendOutTime){
        call("PlayCameraShake", duration, blendInTime, blendOutTime);
    }

    public void playCameraShake(double duration){
        call("PlayCameraShake", duration);
    }

    public void stopCameraShake(){
        call("StopCameraShake");
    }

    public void setPostEffect(PostEffect effect, float... value){
        Object[] params = new Object[2+value.length];
        params[0] = effect.getCategory();
        params[1] = effect.getSetting();
        for(int i=0; i<value.length; i++){
            params[i+2] = value[i];
        }
        call("SetPostEffect", params);
    }

    private Completable<JsonElement[]> call(String name, Object... params) {
        return player.getDimension().getServer().callClient(player.getId(), name, params);
    }
}