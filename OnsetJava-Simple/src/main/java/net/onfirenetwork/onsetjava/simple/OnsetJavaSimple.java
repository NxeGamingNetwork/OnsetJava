package net.onfirenetwork.onsetjava.simple;

import net.onfirenetwork.onsetjava.api.OnsetJava;
import net.onfirenetwork.onsetjava.simple.installer.Installer;

import java.io.File;

public class OnsetJavaSimple {

    public static void main(String [] args){
        if(args.length==1){
            if(args[0].equals("runtheserver")){
                SimpleOnsetServer server = new SimpleOnsetServer();
                OnsetJava.setServer(server);
                server.run();
                return;
            }
        }
        new Installer().install(new File("java_plugins"));
    }

}
