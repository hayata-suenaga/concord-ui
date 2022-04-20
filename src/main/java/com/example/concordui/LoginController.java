package com.example.concordui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {

    @FXML
    private Label errorMsg;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    void onLoginButtonClicked(ActionEvent event) {
        String userName = userNameField.getText();
        String password = passwordField.getText();

        boolean canLogin = this.client.login(userName, password);
        if (!canLogin) {
            errorMsg.setText("Login failed. Attempt again!");
            return;
        }

        viewFactory.showRoomView();
        viewFactory.closeStageFromNode(passwordField);
    }

    public LoginController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }
}
