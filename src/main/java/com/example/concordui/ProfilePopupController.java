package com.example.concordui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePopupController extends BaseController implements Initializable {
    @FXML
    private Label name;

    @FXML
    private Label profile;

    String userName;

    public ProfilePopupController(ConcordClient client, ViewFactory viewFactory, String fxmlName, String userName) {
        super(client, viewFactory, fxmlName);
        this.userName = userName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(userName);
    }
}
