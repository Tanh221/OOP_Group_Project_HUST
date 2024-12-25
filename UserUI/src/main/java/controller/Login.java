package controller;

import app.utils.HelperMethods;
import controller.user.Dashboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Databases.UserDB;
import model.Users.Admin;
import model.Users.Customer;
import model.Users.Staff;
import model.Users.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login {
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;


    private static UserDB userdb = new UserDB();
    public static Staff staff;
    public static Admin admin;
    public static Customer customer;

    Stage dialogStage = new Stage();

    Scene scene;

    public void handleLoginButton(ActionEvent event) throws Exception {
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
                User user = userdb.getByUsernameAndPassword(username,password);

                if (user instanceof Admin){
                    admin = (Admin) user;
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/dashboard.fxml"));
                    Parent root = fxmlLoader.load();

                    Dashboard controller = fxmlLoader.getController();


                    dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    dialogStage.setScene(scene);
                    dialogStage.show();
                }
                else {
                    customer = (Customer) user;
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/user/Dashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    dialogStage.setScene(scene);
                    dialogStage.show();
                }
            }catch (Exception e) {
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

    public void handleGuestButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/user/Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

}








