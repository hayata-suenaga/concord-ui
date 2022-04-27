package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Client extends UnicastRemoteObject implements ConcordClient, Serializable {

    public Map<String, User> users = new HashMap<>();
    public Map<String, Room> rooms = new HashMap<>();
    public User user;

    public Client(ConcordAPI serverProxy, String userName, String password) throws RemoteException {
        User user = serverProxy.login(userName, password);
        if (user == null) return;
        this.user = user;
        serverProxy.registerObserver(this);
    }

    public Client(
            ConcordAPI serverProxy,
            String userName,
            String displayURL,
            String profile,
            String password
    ) throws RemoteException {
        User user = serverProxy.signup(userName, displayURL, profile, password);
        if (user == null) return;
        this.user = user;
        serverProxy.registerObserver(this);
    }

    @Override
    public User getUser() { return this.user; }

    @Override
    public void updateUsers(Map<String, User> users) { this.users = users; }

    @Override
    public void updateRooms(Map<String, Room> rooms) { this.rooms = rooms; }
}
