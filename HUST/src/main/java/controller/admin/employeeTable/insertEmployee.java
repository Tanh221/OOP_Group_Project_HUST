package controller.admin.employeeTable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Users.Staff;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class insertEmployee{

    @FXML
    private TextField dateTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField passTF;

    @FXML
    private TextField phoneTF;

    @FXML
    private TextField salaryTF;

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

    public void handleOrderButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/order.fxml"));
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

    public void handleEmployeeButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/employeeTable/employee.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleReturnButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/employeeTable/employee.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleInsertButton(ActionEvent event) throws Exception {
        if (salaryTF.getText() == "" || dateTF.getText() == "" || nameTF.getText() == ""
                || passTF.getText() == "" || phoneTF.getText() == "" ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Fields");
            alert.setHeaderText("Input Error");
            alert.setContentText("Please fill in all the fields before submitting.");
            alert.showAndWait();
            return; // Stop further processing
        }
        try {
            float salary = Float.parseFloat(salaryTF.getText().trim());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dateTF.getText(), formatter);

            new Staff(
                    nameTF.getText().trim(),
                    passTF.getText().trim(),
                    phoneTF.getText().trim(),
                    date,
                    salary
            );

            // Show confirmation alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirm Insertion");
            alert.setHeaderText("New staff has been added!");
            alert.showAndWait().ifPresent(response -> {
                try {
                    // Reload the employee table
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/employeeTable/employee.fxml"));
                    Parent root = fxmlLoader.load();
                    dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    dialogStage.setScene(scene);
                    dialogStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (NumberFormatException ex) {
            // Handle invalid salary input
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Invalid Input");
            errorAlert.setHeaderText("Salary must be a valid number.");
            errorAlert.show();
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Date Format");
            alert.setContentText("Please enter the date in the format dd/MM/yyyy.");
            alert.show();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Input");
            alert.show();
        }
    }



}
