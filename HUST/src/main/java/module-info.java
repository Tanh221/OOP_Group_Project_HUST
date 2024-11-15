module app {
    requires javafx.controls;
    requires javafx.fxml;

    opens app to javafx.fxml;
    opens controller to javafx.fxml;
    opens controller.admin to javafx.fxml;
    opens model to javafx.fxml;
    opens controller.admin.employeeTable to javafx.fxml;

    exports app;
    exports controller;
    exports controller.admin;
    exports model;
    exports controller.admin.employeeTable;


}