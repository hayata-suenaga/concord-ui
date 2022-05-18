package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    ConcordClient client;
    BorderPane mainFrame;
    BorderPane roomView;
    BorderPane dmView;

    public ViewFactory(ConcordClient client) { this.client = client; }

    public void closeStageFromNode(Node node) { getStageForNode(node).close(); }

    public void showLoginWindow() {
        BaseController loginController = new LoginController(client, this, "login-view.fxml");
        showStage(loginController);
    }

    public void showRoomView() {
        /* Make sure that the main frame exists */
        showMainFrame();
        BaseController roomViewController = new RoomViewController(client, this, "room-view.fxml");
        Parent roomView = getView(roomViewController);
        mainFrame.setCenter(roomView);
    }

    public void showDmView() {
        /* Make sure that the main frame exists */
        showMainFrame();
        BaseController dmViewController = new DmViewController(client, this, "dm-view.fxml");
        BorderPane dmView = (BorderPane) getView(dmViewController);
        this.dmView = dmView;
        mainFrame.setCenter(dmView);
    }

    public void showUsersList() {
        BaseController usersListViewController =
                new UsersListViewController(client, this, "users-list-view.fxml");
        Parent usersListView = getView(usersListViewController);
        dmView.setCenter(usersListView);
    }

    public void showChatListView(String view) {
        BaseController chatListViewController = new ChatListViewController(client, this, "chat-list-view.fxml");
        Parent chatListView = getView(chatListViewController);
        if (view == "room") roomView.setCenter(dmView);
        else dmView.setCenter(chatListView);
    }

    public void showExploreView() {
        BaseController exploreViewController = new ExploreViewController(client, this, "explore-view.fxml");
        showStage(exploreViewController);
    }

    public void showRoomNamePopup() {
        BaseController roomNamePopupController =
                new RoomNamePopupController(client, this, "room-name-popup.fxml");
        showStage(roomNamePopupController);
    }

    public void showProfilePopup(String userName) {
        BaseController profilePopupController =
                new ProfilePopupController(client, this, "profile-popup.fxml", userName);
        showStage(profilePopupController);
    }

    private void showMainFrame() {
        /* If main frame is already shown, do nothing */
        if (mainFrame != null) return;
        BaseController mainFrameController =
                new MainFrameController(client, this, "main-frame.fxml");
        mainFrame = (BorderPane) showStage(mainFrameController);
    }

    private Stage getStageForNode(Node node) { return (Stage) node.getScene().getWindow(); }

    private Parent showStage(BaseController controller) {
        Parent parent = getView(controller);
        /* Initialize scene and stage */
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
        return parent;
    }

    private Parent getView(BaseController controller) {
        /* Initialize loader and set the controller */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.fxmlName));
        fxmlLoader.setController(controller);
        /* Load the fxml file and get the parent (top level) node */
        try { return fxmlLoader.load();  }
        catch (Exception e) { System.exit(-1); }
        return null;
    }
}
