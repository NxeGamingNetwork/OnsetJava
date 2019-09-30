package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.util.Completable;

public interface Sound {
    int getId();

    void setVolume(float volume);

    float getVolume();

    void setPitch(float pitch);

    float getPitch();

    void fadeIn(int duration, float volume, int startTime);

    void fadeOut(int duration, float volume);

    Completable<Integer> getDuration();

    void remove();
}
