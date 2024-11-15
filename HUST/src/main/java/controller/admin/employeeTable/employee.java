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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.*;

public class employee implements Initializable {
    @FXML
    private Button dashboardButton;

    @FXML
    private TableColumn<model.employee, Integer> id;

    @FXML
    private TableColumn<model.employee, String> name;

    @FXML
    private TableColumn<model.employee, String> phone;

    @FXML
    private TableColumn<model.employee, String> date;

    @FXML
    private TableColumn<model.employee, String> salary;

    @FXML
    private TableView<model.employee> table;

    @FXML
    private TextField searchTextField;

    Stage dialogStage = new Stage();
    Scene scene;

    ObservableList<model.employee> employeeObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeObservableList.clear();  // Ensure no leftover data
        model.employee e = null;

        // Load employee data from file
        try (FileInputStream fis = new FileInputStream("src/main/java/app/data/employee.data");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (fis.available() > 0) {
                e = (model.employee) ois.readObject();
                employeeObservableList.add(e);
            }
            System.out.println("Loaded employees: " + employeeObservableList.size()); // Debug line

        } catch (EOFException ex) {
            System.out.println("Finished loading employees.");
        } catch (Exception tmp) {
            System.out.println("Error loading employees: " + tmp);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateJoined"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // Set up FilteredList to allow filtering based on search text
        FilteredList<model.employee> employeeFilteredList = new FilteredList<>(employeeObservableList, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println("Filtering for: " + newValue);  // Debug line
            employeeFilteredList.setPredicate(employee -> {
                // Show all if search field is empty
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                // Check fields for a match
                return employee.getId().toString().contains(keyword)
                        || employee.getSalary().toString().contains(keyword)
                        || employee.getName().toLowerCase().contains(keyword)
//                        || employee.getPhone().toLowerCase().contains(keyword)
//                           (set the phone first, if null can cause error)
                        || employee.getDateJoined().toLowerCase().contains(keyword);
            });
        });

        // Set up SortedList to allow sorting and bind it to the table view
        SortedList<model.employee> employeeSortedList = new SortedList<>(employeeFilteredList);
        employeeSortedList.comparatorProperty().bind(table.comparatorProperty());

        // Set the final sorted and filtered list as the data source for the TableView
        table.setItems(employeeSortedList);
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/storage.fxml"));
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
