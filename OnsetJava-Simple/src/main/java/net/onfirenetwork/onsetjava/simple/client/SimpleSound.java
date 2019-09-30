package net.onfirenetwork.onsetjava.simple.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.client.Sound;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;
import net.onfirenetwork.onsetjava.simple.SimpleOnsetServer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleSound implements Sound {
    Player player;
    @Getter
    int id;
    @Getter
    float volume = 1;
    @Getter
    float pitch = 1;

    public SimpleSound(Player player, int id){
        this.player = player;
        this.id = id;
    }

    public void setVolume(float volume){
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "SetSoundVolume", id, volume);
    }

    public void setPitch(float pitch){
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "SetSoundPitch", id, pitch);
    }

    public void fadeIn(int duration, float volume, int startTime){
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "SetSoundFadeIn", id, duration, volume, startTime);
    }

    public void fadeOut(int duration, float volume){
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "SetSoundFadeOut", id, duration, volume);
    }

    public Completable<Integer> getDuration(){
        Completable<Integer> completable = new Completable<>();
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        server.callClient(player.getId(), "GetSoundDuration", id).then(ret -> completable.complete(ret[0].getAsInt()));
        return completable;
    }

    public void remove() {
        SimpleOnsetServer server = ((SimpleDimension) player.getDimension()).getServer();
        player.getSounds().remove(this);
        server.callClient(player.getId(), "DestroySound", id);
    }
}
