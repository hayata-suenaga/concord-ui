package client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import server.Channel;
import server.Chat;
import server.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomViewController extends BaseController implements Initializable {

    @FXML
    private ListView<Channel> channelList;

    @FXML
    private ListView<Chat> chatList;

    @FXML
    private ListView<String> userList;

    @FXML
    private MenuButton menuButton;

    @FXML
    private TextField msgField;
    @FXML
    void sendBtnClicked(ActionEvent event) {
        System.out.println("Send button was clicked");
        client.sendChat(msgField.getText());
    }

    public RoomViewController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.client.selectedRoom.addListener(
                /* Change the label of the menu button whenever the room selection changes */
                (observable, oldValue, newValue) -> {
                    Room selectedRoom = this.client.rooms.stream()
                            .filter((room) -> room.getId().equals(newValue))
                            .findAny()
                            .orElse(null);
                    menuButton.textProperty().setValue(selectedRoom.getName());
                }
        );

        /* Set the channels */
        channelList.setItems(client.channels);
        channelList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            /* When new channel is selected, change the selected channel id of the model */
            this.client.selectedChannel.setValue(newValue.getId());
        });

        /* Set the cell factory for Chat List */
        chatList.setCellFactory(new ChatCellFactory());
        chatList.setItems(client.chats);

        /* Set the users */
        userList.setItems(client.userNames);
        /* When new user is selected (clicked), show the user profile */
        userList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            viewFactory.showProfilePopup(newValue);
        });
    }
}
