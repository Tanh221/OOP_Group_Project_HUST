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


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Register {
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    TextField emailField;
    @FXML
    TextField fullnameField;

    Stage dialogStage = new Stage();
    Scene scene;

    public void handleLoginButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/Login.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleRegisterButton(ActionEvent event) throws IOException{
        String fullname = fullnameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(fullname.isEmpty()){
            HelperMethods.alertBox("Please enter your full name", null, "Login Failed!");
        }
        else if (email.isEmpty()){
            HelperMethods.alertBox("Please enter your email", null, "Login Failed!");
        }
        else if(username.isEmpty()){
            HelperMethods.alertBox("Please enter your username", null, "Login Failed!");
        }
        else if (password.isEmpty()){
            HelperMethods.alertBox("Please enter your password", null, "Login Failed!");
        }
        else{
            try{
                FileWriter file = new FileWriter("src/main/resources/user&pass.txt",true);
                BufferedWriter writer = new BufferedWriter(file);

                writer.write("\n"+username+","+password+","+fullname+","+email);
                writer.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/Login.fxml"));
                Parent root = fxmlLoader.load();
                dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                dialogStage.setScene(scene);
                dialogStage.show();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
