package net.onfirenetwork.onsetjava.api;

public interface Scheduler {

    void unschedule(int id);

    int schedule(long delay, Runnable run);

    int scheduleRepeating(long delay, long interval, Runnable run);

}
