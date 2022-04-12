package com.example.concordui;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {

        Server server = new Server();

        ConcordClient client = new ConcordClient(server);

        ViewFactory viewFactory = new ViewFactory(client);

        viewFactory.showLoginWindow();

    }

    public static void main(String[] args) {
        launch();
    }
}