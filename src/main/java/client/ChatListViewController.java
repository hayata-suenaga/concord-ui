package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ChatListViewController extends BaseController {

    @FXML
    private TextField msgInput;

    @FXML
    void onSendClicked(ActionEvent event) {
        client.sendChat(msgInput.getText());
    }

    public ChatListViewController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }
}
