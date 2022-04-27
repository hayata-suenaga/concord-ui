package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ExploreViewController extends BaseController {

    @FXML
    private ListView<?> roomList;

    public ExploreViewController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }

    @FXML
    void onAddButtonClicked(ActionEvent event) {
        viewFactory.closeStageFromNode(roomList);
    }

    @FXML
    void onCancelButtonClicked(ActionEvent event) {
        viewFactory.closeStageFromNode(roomList);
    }
}
