package server;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {
    public static void main(String[] args) throws Exception {
        ConcordAPI server;

        if (new File("backup.xml").exists()) {
            XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("backup.xml")));
            server = (ConcordAPI) decoder.readObject();
        } else {
            server = new Server();
        }

        Registry registry = LocateRegistry.createRegistry(2500);
        registry.rebind("CONCORD", server);

//        //TODO: remove this
//        server.signup("tom", "tom's url", "I'm tom", "password");
    }
}
