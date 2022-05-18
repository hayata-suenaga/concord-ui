package client;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import server.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observer;

public class ConcordClient extends UnicastRemoteObject implements server.ConcordClient, Serializable {
    ConcordAPI server;
    StringProperty selectedRoom = new SimpleStringProperty();
    StringProperty selectedChannel = new SimpleStringProperty("");
    ObservableList<Room> rooms = FXCollections.observableArrayList();
    ObservableList<Channel> channels = FXCollections.observableArrayList();
    ObservableList<String> userNames = FXCollections.observableArrayList();
    ObservableList<Chat> chats = FXCollections.observableArrayList();
    ObservableList<String> dms = FXCollections.observableArrayList();
    ObservableMap<String, User> users = FXCollections.observableHashMap();

    User user;

    public ConcordClient(ConcordAPI server) throws RemoteException {
        super();
        this.server = server;
        selectedRoom.addListener((observable, oldValue, newValue) -> {
            Room room = getRoom(newValue);

            /* Set channels */
            channels.setAll(room.getChannels().values());

            /* Set the first channel in the newly selected room as the selected channel */
            selectedChannel.setValue(channels.get(0).getId());
        });

        selectedChannel.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            Channel channel = getChannel(newValue);

            System.out.println("Inside selectedChannel listener # of channels: " + channel.getChats().size());
            this.chats.setAll(channel.getChats());
        });
    }

    public boolean login(String userName, String password) {
        try {
            user = server.login(userName, password);
            server.registerObserver(this);
            if (user != null) {
                users.putAll(server.getUsers());
                rooms.addAll(server.getRoomsForUser(user.getUserName()).values());
                this.selectedRoom.setValue(rooms.get(0).getId());
                Channel channel = (Channel) new ArrayList(rooms.get(0).getChannels().values()).get(0);
                this.selectedChannel.setValue(channel.getId());
            }
            return user != null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSelectedRoom(String roomId) {
        this.selectedRoom.setValue(roomId);
    }

    public void setSelectedChannel(String selectedChannel) {
        this.selectedChannel.set(selectedChannel);
    }

    public void addRoom(String roomName) {
        try {
            server.createRoom(roomName, user.getUserName());
        } catch (Exception e) { System.exit(-1); }
    }

    public void sendChat(String text) {
        try {
            server.sendChat(selectedRoom.getValue(), selectedChannel.getValue(), user.getUserName(), text);
        } catch (Exception e) { System.exit(-1); }
    }

    private Room getRoom(String roomId) {
        Room room = rooms.stream()
                .filter(currRoom -> currRoom.getId().equals(roomId))
                .findAny()
                .orElse(null);
        return room;
    }

    private Channel getChannel(String channelId) {
        System.out.println(channelId);

        Channel channel = this.channels
                .stream()
                .filter(c -> c.getId().equals(channelId))
                .findAny()
                .orElse(null);

        return channel;
    }

    @Override
    public User getUser() throws RemoteException { return user; }

    @Override
    public void updateUsers(Map<String, User> users) throws RemoteException {
        List<String> strUsers = users.values().stream().map(user -> user.getUserName()).toList();
        Platform.runLater(() -> {
            userNames.setAll(strUsers);
        });
    }

    @Override
    public void updateRooms(Map<String, Room> rooms) throws RemoteException {
        Platform.runLater(() -> {
            System.out.println("Line 1");
            this.rooms.setAll(rooms.values());
            System.out.println("Line 2");
            Room room = rooms.get(this.selectedRoom.getValue());
            System.out.println("Line 3");
            this.channels.setAll(room.getChannels().values());
            System.out.println("Line 4");
            Channel channel = room.getChannels().get(selectedChannel.getValue());
            System.out.println("Line 5");
            this.chats.setAll(channel.getChats());
            System.out.println("Line 6");
        });
    }
}
