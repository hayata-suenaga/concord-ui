package client;
import javafx.application.Application;
import javafx.stage.Stage;
import server.ConcordAPI;
import java.rmi.Naming;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        ConcordAPI server = null;
        try {
            server = (ConcordAPI) Naming.lookup("rmi://127.0.0.1:2500/CONCORD");
        } catch (Exception e) {
            System.exit(-1);
        }

        ConcordClient client = new ConcordClient(server);

        ViewFactory viewFactory = new ViewFactory(client);

        viewFactory.showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}