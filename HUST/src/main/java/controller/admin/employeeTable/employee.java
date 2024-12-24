package controller.admin.employeeTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import model.Databases.UserDB;
import model.Store.Store;
import model.Users.Staff;
import model.Users.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class employee implements Initializable {
    @FXML
    private Button dashboardButton;

    @FXML
    private TableColumn<Staff, String> userID;

    @FXML
    private TableColumn<Staff, String> username;

    @FXML
    private TableColumn<Staff, String> phone;

    @FXML
    private TableColumn<Staff, LocalDate> date;

    @FXML
    private TableColumn<Staff, Float> salary;

    @FXML
    private TableView<Staff> table;

    @FXML
    private TextField searchTextField;

    private static UserDB userdb = new UserDB();
//    private static Store store = new Store();

    Stage dialogStage = new Stage();
    Scene scene;

    ObservableList<Staff> employeeObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editData();
        employeeObservableList.clear();  // Ensure no leftover data
        Staff e = null;
        try {
            ArrayList<User> allStaff = userdb.getAllUsers();
            for(User x : allStaff){
                if (x instanceof Staff){
                    employeeObservableList.add((Staff) x);
                    System.out.println(x.getUsername());
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateJoined"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        userID.setCellValueFactory(new PropertyValueFactory<Staff, String>("userID"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));



        // Set up FilteredList to allow filtering based on search text
        FilteredList<Staff> employeeFilteredList = new FilteredList<>(employeeObservableList, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            employeeFilteredList.setPredicate(employee -> {
                // Show all if search field is empty
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                // Check fields for a match
                try {
                    return String.valueOf(employee.getUserID()).contains(keyword)
                            || String.valueOf(employee.getSalary()).contains(keyword)
                            || employee.getUsername().toLowerCase().contains(keyword)
                            || employee.getPhone().toLowerCase().contains(keyword)
                            || String.valueOf(employee.getDateJoined()).contains(keyword);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        });

        // Set up SortedList to allow sorting and bind it to the table view
        SortedList<Staff> employeeSortedList = new SortedList<>(employeeFilteredList);
        employeeSortedList.comparatorProperty().bind(table.comparatorProperty());

        // Set the final sorted and filtered list as the data source for the TableView
        table.setItems(employeeSortedList);


    }

    private void editData(){
        username.setCellFactory(TextFieldTableCell.<Staff>forTableColumn());
        username.setOnEditCommit(e ->{
            Staff person = e.getTableView().getItems().get(e.getTablePosition().getRow());
            try {
                person.setUsername(e.getNewValue());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

        phone.setCellFactory(TextFieldTableCell.<Staff>forTableColumn());
        phone.setOnEditCommit(e ->{
            Staff person = e.getTableView().getItems().get(e.getTablePosition().getRow());
            try {
                person.setPhone(e.getNewValue());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

        date.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        date.setOnEditCommit((e) -> {
            Staff person = e.getTableView().getItems().get(e.getTablePosition().getRow());
            try {
                // Convert the new value to LocalDate and set it
                LocalDate newDate = e.getNewValue(); // Automatically converted by the converter
                person.setDateJoined(newDate);
            } catch (Exception ex) {
                throw new RuntimeException("Invalid date format. Please use yyyy-MM-dd.", ex);
            }
        });

        salary.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.FloatStringConverter()));
        salary.setOnEditCommit((TableColumn.CellEditEvent<Staff, Float> e) -> {
            Staff person = e.getTableView().getItems().get(e.getTablePosition().getRow());
            try {
                System.out.println(e.getNewValue()); // Debug line to check the new value
                person.setSalary(e.getNewValue());  // Update the salary
            } catch (Exception ex) {
                throw new RuntimeException("Error updating salary: " + ex.getMessage(), ex);
            }
        });

    }

    @FXML
    private void handleRemoveButton(ActionEvent event){
//        TableView.TableViewSelectionModel<model.employee> selectionModel = table.getSelectionModel();
//        ObservableList<Integer> list = selectionModel.getSelectedIndices();
//        Integer[] selectedIndices = new Integer[list.size()];
//        selectedIndices = list.toArray(selectedIndices);
//
//        Arrays.sort(selectedIndices);
//
//        for (int i = selectedIndices.length - 1; i >= 0; i--){
//            selectionModel.clearAndSelect(selectedIndices[i].intValue());
//            table.getItems().remove(selectedIndices[i].intValue());
//        }
//        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableView.TableViewSelectionModel<Staff> selectionModel = table.getSelectionModel();
        ObservableList<Staff> selectedItems = selectionModel.getSelectedItems();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete the selected employee?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Remove the selected items from the original employeeObservableList
                for(Staff x : selectedItems){
                    try {
                        userdb.remove(x);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                employeeObservableList.removeAll(selectedItems);
                // Clear the selection
                selectionModel.clearSelection();
            }
        });




    }


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

    public void handleInsertButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/employeeTable/insertEmployee.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

}
