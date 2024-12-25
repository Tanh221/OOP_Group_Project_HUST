module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens app to javafx.fxml;
    opens controller to javafx.fxml;
    opens controller.user to javafx.fxml;
    opens controller.user.ProductTable to javafx.fxml;
    opens model.Users to javafx.fxml;
    opens model.Cart to javafx.fxml;
    opens model.Databases to javafx.fxml;
    opens model.exception to javafx.fxml;
    opens model.Order to javafx.fxml;
    opens model.Products to javafx.fxml;
    opens model.ReceiveNote to javafx.fxml;
    opens model.Store to javafx.fxml;

    exports app;
    exports controller;
    exports controller.user;
    exports controller.user.ProductTable;
    exports model.Products;
    exports model.Cart;
    exports model.Databases;
    exports model.Users;
    exports model.exception;
    exports model.ReceiveNote;
    exports model.Store;
    exports model.Order;


//    opens app to javafx.fxml;
//    opens controller to javafx.fxml;
//    opens controller.admin to javafx.fxml;
//    opens controller.user to javafx.fxml;
//    opens controller.admin.employeeTable to javafx.fxml;
//    opens model.Users to javafx.fxml;
//
//    opens model to
//
//
//    exports app;
//    exports controller;
//    exports controller.admin;
//    exports controller.user;
//    exports model.Users;
//    exports controller.admin.employeeTable;
//    exports controller.admin.storageTable;
//    opens controller.admin.storageTable to javafx.fxml;


}