package net.onfirenetwork.onsetjava.simple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class Completable<T> {

    private CompletableFuture<T> future = new CompletableFuture<>();

    public void complete(T value){
        future.complete(value);
    }

    public T get(){
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void then(Consumer<T> consumer){
        future.thenAccept(consumer);
    }

}
