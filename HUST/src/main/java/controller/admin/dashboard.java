package controller.admin;


import controller.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class dashboard {
    @FXML
    public AnchorPane dashHead;
    @FXML
    public Button employeeButton;
    @FXML
    private Label name;



    public String holder;
    Stage dialogStage = new Stage();
    Scene scene;


    public void handleEmployeeButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/employeeTable/employee.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleStorageButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/storageTable/storage.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleOrderButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/order.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleStatisticButton(ActionEvent event) throws IOException{
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

    public void sendName(String name) throws Exception {
        holder = name;
        if (this.name != null) { // Ensure the FXML Label is initialized
            this.name.setText("Hello " + controller.Login.admin.getUsername() + "!");
        }
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        name.setText("Hello "+holder+"!");
//    }
}
