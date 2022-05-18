package client;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import server.Chat;

public class ChatCellFactory implements Callback<ListView<Chat>, ListCell<Chat>> {
    @Override
    public ListCell<Chat> call(ListView<Chat> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(Chat chat, boolean empty) {
                super.updateItem(chat, empty);
                if (empty || chat == null) setText(null);
                else setText(chat.getSenderName() + ": " + chat.getMessage());
            }
        };
    }
}
