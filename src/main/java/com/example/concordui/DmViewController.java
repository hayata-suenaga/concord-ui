package com.example.concordui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class DmViewController extends BaseController implements Initializable {

    @FXML
    private ListView<String> dmLists;

    @FXML
    void onUsersButtonClicked(ActionEvent event) {
        viewFactory.showUsersList();
    }

    public DmViewController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dmLists.setItems(client.dms);

        dmLists.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            viewFactory.showChatListView("dm");
        });
    }
}
