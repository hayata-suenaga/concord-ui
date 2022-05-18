package server;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends UnicastRemoteObject implements ConcordAPI, Serializable {
    private Map<String, Room> rooms = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private Map<String, String> passwords = new HashMap<>();
    final private transient List<ConcordClient> observers = new ArrayList<>();

    public Server() throws RemoteException {}

    public User signup(String userName, String displayURL, String profile, String password) {
        /* Create a new user */
        User user = new User(userName, displayURL, profile);
        /* Add the new user to the list of users */
        users.put(userName, user);
        /* Record the new user's password */
        passwords.put(userName, password);
        usersChanged();
        return user;
    }

    public User login(String userName, String password) {
        /* Check if the user with the given username exits and if the password matches */
        if (!passwords.containsKey(userName) || !passwords.get(userName).equals(password))
            return null;
        /* Return the user */
        return users.get(userName);
    }

    public Map<String, Room> getRoomsForUser(String userName) {
        List<String> roomIds = users.get(userName).getRoomIds();
        Map<String, Room> roomsForUser = new HashMap<>();
        /* Get the list of rooms to which the user belongs */
        for (String roomId : rooms.keySet())
            if (roomIds.contains(roomId)) roomsForUser.put(roomId, rooms.get(roomId));
        return roomsForUser;
    }

    public void createRoom(String roomName, String adminName) {
        User admin = users.get(adminName);
        Room room = new Room(roomName, admin);
        rooms.put(room.getId(), room);
        admin.joinRoom(room.getId());
        roomsChanged();
    }

    public boolean deleteRoom(String actorName, String roomId) {
        if (!rooms.get(roomId).getRoleOfUser(actorName).canDeleteRoom) return false;
        rooms.remove(roomId);
        for (User user : users.values()) { user.getRoomIds().remove(roomId); }
        roomsChanged();
        return true;
    }

    public boolean addUserToRoom(String roomId, String actorName, String userName, Role role) {
        User userToAdd = users.get(userName);
        if (rooms.get(roomId).addUser(actorName, userToAdd, role)) {
            userToAdd.joinRoom(roomId);
            roomsChanged();
            return true;
        }
        return false;
    }

    public boolean removeUserFromRoom(UUID roomId, String actorName, String userName) {
        User userToRemove = users.get(userName);
        if (rooms.get(roomId).removeUser(actorName, userName)) {
            userToRemove.leaveRoom(roomId);
            roomsChanged();
            return true;
        }
        return false;
    }

    public boolean createChannel(String actorName, String roomId, String channelName) {
        if (rooms.get(roomId).addChannel(actorName, channelName)) {
            roomsChanged();
            return true;
        }
        return false;
    }

    public boolean deleteChannel(String actorName, String roomId, String channelId) {
        if (rooms.get(roomId).deleteChannel(actorName, channelId)) {
            roomsChanged();
            return true;
        }
        return false;
    }

    public boolean sendChat(String roomId, String channelId, String senderName, String text) {
        System.out.println(
                "send chat was invoked with the following arguments: roomId: "
                        + roomId + " channelId: "
                        + channelId + " senderName: "
                        + senderName + " text: "
                        + text
        );

        if (rooms.get(roomId).sendChat(channelId, senderName, text)) {
            roomsChanged();
            return true;
        }
        return false;
    }

    public boolean deleteChat(String actorName, String roomId, String channelId, String chatId) {
        if (rooms.get(roomId).deleteChat(actorName, channelId, chatId)) {
            roomsChanged();
            return true;
        }
        return false;
    }

    /* Encode the whole object graph to xml */
    private void encode() {
        try {
            /* Create the xml file if not exists */
            new File("backup.xml").createNewFile();
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("backup.xml")));
            encoder.writeObject(this);
            encoder.close();
        } catch (Exception e) { System.out.println("Error occurs while encoding"); }
    }

    /* Push updated users map to all clients (observers) */
    private void notifyUsersUpdate(){ observers.forEach(observer -> {
        try { observer.updateUsers(users); }
        catch (Exception e) { System.out.println("Error occurred while pushing update of users map ");}
    }); }

    /* Push updated users map to all clients (observers) */
    private void notifyRoomsUpdate() { observers.forEach(observer -> {
        try {
            /* Get the username of the client (observer */
            String userName = observer.getUser().getUserName();
            Map<String, Room> roomsForUser = getRoomsForUser(userName);
            System.out.println("Line 147 of Server.java");
            observer.updateRooms(roomsForUser);
            System.out.println("Line 149 of Server.java");
        } catch (Exception e) { System.out.println(e);}
    }); }

    /* If the users map is changed, encode the updated data and notify the new change to clients */
    private void usersChanged() { notifyUsersUpdate(); encode(); }

    /* If the rooms map is changed, encode the updated data and push the new change to clients */
    private void roomsChanged() { notifyRoomsUpdate(); encode(); }

    public void registerObserver(ConcordClient observer) { observers.add(observer); }

    public void removeObserver(ConcordClient observer) { observers.remove(observer); }

    @Override
    public boolean react(String roomId, String channelId, String chatId, String type) throws RemoteException {
        Chat chat = getChat(roomId, channelId, chatId);
        chat.react(type);
        return true;
    }

    @Override
    public List<Reaction> getReactions(String roomId, String channelId, String chatId) {
        Chat chat = getChat(roomId, channelId, roomId);
        return chat.getReactions();
    }

    private Chat getChat(String roomId, String channelId, String chatId) {
        Chat chat =
                rooms.get(roomId)
                        .getChannels()
                        .get(channelId)
                        .getChats()
                        .stream()
                        .filter(item -> item.getId().equals(chatId))
                        .findAny()
                        .orElse(null);

        return chat;
    }

    /* Java Beans getters and setters. Not intended to be used */
    public Map<String, Room> getRooms() { return rooms; }
    public Map<String, String> getPasswords() { return passwords; }
    public void setRooms(Map<String, Room> rooms) { this.rooms = rooms; }
    public void setUsers(Map<String, User> users) { this.users = users; }
    public void setPasswords(Map<String, String> passwords) { this.passwords = passwords; }
    /* Getter for use */
    public Map<String, User> getUsers() { return users; }
}
