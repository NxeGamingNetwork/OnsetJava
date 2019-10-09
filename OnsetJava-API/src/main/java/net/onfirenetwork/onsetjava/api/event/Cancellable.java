package net.onfirenetwork.onsetjava.api.event;

public interface Cancellable {
    void setCancelled(boolean cancelled);
    boolean isCancelled();
}
