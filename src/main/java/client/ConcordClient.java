package client;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.ConcordAPI;
import java.util.ArrayList;
import java.util.List;

public class ConcordClient {
    ConcordAPI server;
    StringProperty selectedRoom = new SimpleStringProperty();
    ObservableList<String> roomNames = FXCollections.observableArrayList();
    ObservableList<String> channelNames = FXCollections.observableArrayList();
    ObservableList<String> userNames = FXCollections.observableArrayList();
    ObservableList<TempChat> chats = FXCollections.observableArrayList();
    ObservableList<String> dms = FXCollections.observableArrayList();
    ObservableList<String> users = FXCollections.observableArrayList();

    public ConcordClient(ConcordAPI server) {
        this.server = server;
        tempInitializeDemoData();
    }

    public boolean login(String userName, String password) {
        try {
            return server.login(userName, password) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void setSelectedRoom(String selectedRoom) { this.selectedRoom.set(selectedRoom); }

    public void addRoom(String roomName) { this.roomNames.add(roomName); }

    private void tempInitializeDemoData() {
        List<String> roomNames = new ArrayList<>() {{
            add("room 1");
            add("room 2");
            add("room 3");
        }};

        this.roomNames.addAll(roomNames);

        List<String> channelNames = new ArrayList<>() {{
            add("channel 1");
            add("channel 2");
            add("channel 3");
        }};

        this.channelNames.addAll(channelNames);

        List<TempChat> chats = new ArrayList<>() {{
            new TempChat("tom", "yo!");
            new TempChat("alex", "what's up?");
            new TempChat("tom", "hey");
            new TempChat("bob", "hi");
        }};

        this.chats.addAll(chats);

        List<String> userNames = new ArrayList<>() {{
            add("tom");
            add("alex");
            add("bob");
        }};

        this.userNames.addAll(userNames);

        List<String> dms = new ArrayList<>() {{
            add("tom");
            add("alex");
            add("bob");
        }};

        this.dms.addAll(dms);

        List<String> users = new ArrayList<>() {{
            add("tom");
            add("alex");
            add("bob");
            add("another user 1");
            add("another user 2");
            add("another user 3");
        }};

        this.users.addAll(users);
    }
}
