package net.onfirenetwork.onsetjava.api.entity;

public interface Player {
    int getId();
    String getSteamId();
    String getName();
    void kick(String message);
}
