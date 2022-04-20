package com.example.concordui;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
class AppTest {

    @Start
    private void start(Stage stage) {
        Server server = new Server();

        ConcordClient client = new ConcordClient(server);

        ViewFactory viewFactory = new ViewFactory(client);

        viewFactory.showLoginWindow();
    }

    @Test
    public void testLogin(FxRobot robot) throws InterruptedException {
        robot.clickOn("#loginButton");
        /* If the username and password fields are empty, don't let the user login */
        Assertions.assertThat(robot.lookup("#errorMsg") != null);

        /* If a user that doesn't exist try to log in, reject */
        enterValue("#userNameField", "non_existing_user", robot);
        enterValue("#passwordField", "random_password", robot);
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#errorMsg") != null);

        /* If the password is wrong, reject */
        enterValue("#userNameField", "bob", robot);
        enterValue("#passwordField", "wrong_password", robot);
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#errorMsg") != null);

        /* When password is correct, let user login */
        enterValue("#passwordField", "bob's password", robot);
        robot.clickOn("#loginButton");

        Assertions.assertThat(robot.lookup("#mainFrame") != null);
        robot.clickOn("#dmButton");
        Assertions.assertThat(robot.lookup("#dmFrame") != null);
        robot.clickOn("#usersButton");

        selectListItem("#roomList", 1, robot);
        Assertions.assertThat(robot.lookup("#mainFrame"));
    }

    private void enterValue(String fieldId, String text, FxRobot robot) {
        robot.clickOn(fieldId);
        clearTextField(fieldId, robot);
        robot.write(text);
    }

    private void selectListItem(String listId, int index, FxRobot robot) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) robot.lookup(listId).query();
                listView.getSelectionModel().clearSelection();
                listView.getSelectionModel().select(index);
            }
        });
    }

    private void clearTextField(String fieldId, FxRobot robot) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TextField textField = (TextField) robot.lookup(fieldId).query();
                textField.clear();
            }
        });
    }

}