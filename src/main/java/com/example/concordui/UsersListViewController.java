package com.example.concordui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersListViewController extends BaseController implements Initializable {
    @FXML
    private ListView<String> usersList;

    public UsersListViewController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersList.setItems(client.users);
    }
}
