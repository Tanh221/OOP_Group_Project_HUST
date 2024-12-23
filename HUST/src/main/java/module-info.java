module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens app to javafx.fxml;
    opens controller to javafx.fxml;
    opens controller.admin to javafx.fxml;
    opens controller.user to javafx.fxml;
    opens model to javafx.fxml;
    opens controller.admin.employeeTable to javafx.fxml;

    exports app;
    exports controller;
    exports controller.admin;
    exports controller.user;
    exports model;
    exports controller.admin.employeeTable;
    exports controller.admin.storageTable;
    opens controller.admin.storageTable to javafx.fxml;


}