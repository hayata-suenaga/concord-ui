package client;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import server.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFrameController extends BaseController implements Initializable {

    @FXML
    private ListView<Room> roomList;

    @FXML
    private Label tempRoomName;

    @FXML
    void onAddButtonClicked(ActionEvent event) { viewFactory.showRoomNamePopup(); }

    @FXML
    void onDMButtonClicked(ActionEvent event) {
        viewFactory.showDmView();
        /*TODO: Clear selection */
    }
    @FXML
    void onExploreButtonClicked(ActionEvent event) {
        viewFactory.showExploreView();
    }

    public MainFrameController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roomList.setItems(client.rooms);
        roomList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            /* Show the room view if not shown already */
            viewFactory.showRoomView();
            if (newValue == null) return;
            this.client.setSelectedRoom(newValue.getId());
        });
    }
}
