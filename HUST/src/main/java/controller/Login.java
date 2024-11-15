package controller;

import app.utils.HelperMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login {
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;

    Stage dialogStage = new Stage();
    Scene scene;

    public void handleLoginButton(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username.isEmpty()){
            HelperMethods.alertBox("Please enter the Username", null, "Login Failed!");
        }
        else if (password.isEmpty()){
            HelperMethods.alertBox("Please enter the Password", null, "Login Failed!");
        }
        else {
            try{
                FileReader file = new FileReader("src/main/resources/user&pass.txt");
                BufferedReader reader = new BufferedReader(file);
                String line;
                String[] data;
                while ((line = reader.readLine()) != null) {
                    data = line.split(",");
                    if(username.equals(data[0]) && password.equals(data[1]) && data[4].equals("1")){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/dashboard.fxml"));
                        Parent root = fxmlLoader.load();
                        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        dialogStage.setScene(scene);
                        dialogStage.show();
                    }

                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    public void handleRegisterButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/Register.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}









