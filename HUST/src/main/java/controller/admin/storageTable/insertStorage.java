package controller.admin.storageTable;

import controller.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Products.Book;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class insertStorage implements Initializable {

    Stage dialogStage = new Stage();
    Scene scene;

    @FXML
    private Button dashboardButton;

    @FXML
    private Label lb1;

    @FXML
    private Label lb2;

    @FXML
    private Label lb3;

    @FXML
    private Label lb4;

    @FXML
    private Label lb5;

    @FXML
    private Label lb6;

    @FXML
    private Label lb7;

    @FXML
    private TextField tf1;

    @FXML
    private TextField tf2;

    @FXML
    private TextField tf3;

    @FXML
    private TextField tf4;

    @FXML
    private TextField tf5;

    @FXML
    private TextField tf6;

    @FXML
    private TextField tf7;

    @FXML
    private ChoiceBox<String> typeField;

    private String[] typeChoice = {"Book","Toy","Stationary"};

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/storageTable/storage.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleInsertButton(ActionEvent event) throws Exception {
        if (Objects.equals(typeField.getValue(), "Book")){
            if (tf1.getText().isEmpty() || tf2.getText().isEmpty() ||tf3.getText().isEmpty() ||
                    tf6.getText().isEmpty() ||tf5.getText().isEmpty() ||tf4.getText().isEmpty() ||
                    tf7.getText().isEmpty()){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText("Please fill in all field!");
                errorAlert.show();
                return;
            }
            try {
                Login.admin.addProductToStore(new Book(tf1.getText(), Float.parseFloat(tf2.getText()), tf6.getText(),
                        tf3.getText(), tf4.getText(), Integer.parseInt(tf5.getText())), 1, Float.parseFloat(tf7.getText()));
            } catch (NumberFormatException e){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText("Price, ISBN and import price must be a valid number.");
                errorAlert.show();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add product successfully");
            alert.setHeaderText("Add book successfully");
            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/storageTable/storage.fxml"));
            Parent root = fxmlLoader.load();
            dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.show();
        }

        else if (Objects.equals(typeField.getValue(), "Toy")){
            if (tf1.getText().isEmpty() || tf2.getText().isEmpty() ||tf3.getText().isEmpty() ||
                    tf6.getText().isEmpty() ||tf5.getText().isEmpty()){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText("Please fill in all field!");
                errorAlert.show();
                return;
            }
            try {
                Login.admin.addProductToStore(new Book(tf1.getText(), Float.parseFloat(tf2.getText()), tf6.getText(),
                        tf3.getText(), tf4.getText(), Integer.parseInt(tf5.getText())), 1, Float.parseFloat(tf7.getText()));
            } catch (NumberFormatException e){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText("Price, ISBN and import price must be a valid number.");
                errorAlert.show();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add product successfully");
            alert.setHeaderText("Add toy successfully");
            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/storageTable/storage.fxml"));
            Parent root = fxmlLoader.load();
            dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.show();
        }



    }

    public void handleChooseButton(ActionEvent event) throws IOException {
        if (Objects.equals(typeField.getValue(), "Book")){
            tf1.setVisible(true);
            tf2.setVisible(true);
            tf3.setVisible(true);
            tf4.setVisible(true);
            tf5.setVisible(true);
            tf6.setVisible(true);
            tf7.setVisible(true);

            lb1.setVisible(true);
            lb2.setVisible(true);
            lb3.setVisible(true);
            lb4.setVisible(true);
            lb5.setVisible(true);
            lb6.setVisible(true);
            lb7.setVisible(true);

            lb1.setText("Name");
            lb2.setText("Price");
            lb3.setText("Publisher");
            lb4.setText("Author");
            lb5.setText("ISBN");
            lb6.setText("Description");
            lb7.setText("Import price");
        }

        else if (Objects.equals(typeField.getValue(), "Toy")){
            tf1.setVisible(true);
            tf2.setVisible(true);
            tf3.setVisible(true);
            tf4.setVisible(true);
            tf5.setVisible(true);
            tf6.setVisible(false);
            tf7.setVisible(false);

            lb1.setVisible(true);
            lb2.setVisible(true);
            lb3.setVisible(true);
            lb4.setVisible(true);
            lb5.setVisible(true);
            lb6.setVisible(false);
            lb7.setVisible(false);

            lb1.setText("Name");
            lb2.setText("Price");
            lb3.setText("Brand");
            lb4.setText("Description");
            lb5.setText("Import price");
        }

        else if(Objects.equals(typeField.getValue(), "Stationary")){
            tf1.setVisible(true);
            tf2.setVisible(true);
            tf3.setVisible(true);
            tf4.setVisible(true);
            tf5.setVisible(true);
            tf6.setVisible(true);
            tf7.setVisible(false);

            lb1.setVisible(true);
            lb2.setVisible(true);
            lb3.setVisible(true);
            lb4.setVisible(true);
            lb5.setVisible(true);
            lb6.setVisible(true);
            lb7.setVisible(false);

            lb1.setText("Name");
            lb2.setText("Price");
            lb3.setText("Brand");
            lb4.setText("Stationary Type");
            lb5.setText("Description");
            lb6.setText("Import price");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeField.getItems().addAll(typeChoice);
    }
}
