package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface ConcordAPI extends Remote {
    User signup(String userName, String displayURL, String profile, String password) throws RemoteException;

    User login(String userName, String password) throws RemoteException;

    Map<String, User> getUsers() throws RemoteException;

    Map<String, Room> getRoomsForUser(String userName) throws RemoteException;

    void createRoom(String roomName, String adminName) throws RemoteException;

    boolean addUserToRoom(String roomId, String actorName, String userName, Role role) throws RemoteException;

    boolean createChannel(String actorName, String roomId, String channelName) throws RemoteException;

    boolean sendChat(String roomId, String channelId, String senderName, String text) throws RemoteException;

    boolean deleteRoom(String actorName, String roomId) throws RemoteException;

    boolean deleteChannel(String actorName, String roomId, String channelId) throws RemoteException;

    boolean deleteChat(String actorName, String roomId, String channelId, String chatId) throws RemoteException;

    void registerObserver(ConcordClient observer) throws RemoteException;

    void removeObserver(ConcordClient observer) throws RemoteException;
}
