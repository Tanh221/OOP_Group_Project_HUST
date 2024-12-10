module app {
    requires javafx.controls;
    requires javafx.fxml;

    opens app to javafx.fxml;
    opens controller to javafx.fxml;
    opens controller.user to javafx.fxml;


    exports app;
    exports controller;
    exports controller.user;



}