package client;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomViewController extends BaseController implements Initializable {

    @FXML
    private ListView<String> channelList;

    @FXML
    private ListView<TempChat> chatList;

    @FXML
    private ListView<String> userList;

    @FXML
    private MenuButton menuButton;

    public RoomViewController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* After the controller is bound to the view, set the list values */
        channelList.setItems(client.channelNames);

        //TODO: Figure out how to render a view inside each cell
        chatList.setItems(client.chats);
        userList.setItems(client.userNames);

        userList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            viewFactory.showProfilePopup(newValue);
        });

        Bindings.bindBidirectional(client.selectedRoom, menuButton.textProperty());
    }
}
