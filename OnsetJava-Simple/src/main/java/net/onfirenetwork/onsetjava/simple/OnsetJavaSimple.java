package net.onfirenetwork.onsetjava.simple;

import net.onfirenetwork.onsetjava.api.OnsetJava;

public class OnsetJavaSimple {

    public static void main(String [] args){
        SimpleOnsetServer server = new SimpleOnsetServer();
        OnsetJava.setServer(server);
        server.start(true);
    }

}
