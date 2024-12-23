package controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class order {


    Stage dialogStage = new Stage();
    Scene scene;

    public void handleDashboardButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/dashboard.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleStorageButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/storageTable/storage.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleEmployeeButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/employeeTable/employee.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleStatisticButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/statistic.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleLogoutButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/Login.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
