package com.example.concordui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {

    @FXML
    private TextField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    void onLoginButtonClicked(ActionEvent event) {
        viewFactory.showRoomView();
        viewFactory.closeStageFromNode(passwordField);
    }

    public LoginController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }
}
