package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RoomNamePopupController extends BaseController {
    @FXML
    private TextField nameField;

    @FXML
    void onCancelButtonClicked(ActionEvent event) { viewFactory.closeStageFromNode(nameField); }

    @FXML
    void onCreateButtonClicked(ActionEvent event) {
        client.addRoom(nameField.getText());
        viewFactory.closeStageFromNode(nameField);
    }

    public RoomNamePopupController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }
}
