module com.example.concordui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.concordui to javafx.fxml;
    exports com.example.concordui;
}