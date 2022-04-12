package com.example.concordui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFrameController extends BaseController implements Initializable {

    @FXML
    private ListView<String> roomList;

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
        roomList.setItems(client.roomNames);
        roomList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            viewFactory.showRoomView();
            client.setSelectedRoom(newValue);
        });
    }
}
