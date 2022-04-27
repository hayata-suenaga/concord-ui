package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface ConcordClient extends Remote {
    User getUser() throws RemoteException;
    void updateUsers(Map<String, User> users) throws RemoteException;
    void updateRooms(Map<String, Room> rooms) throws RemoteException;
}
