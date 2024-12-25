package controller.user.ProductTable;



import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;
import model.Databases.ProductDB;
import model.Products.ProductInfo;
import model.Users.Staff;
import model.Store.Store;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class Product implements Initializable {
    @FXML
    private Button dashboardButton;

    @FXML
    private TableColumn<ProductInfo, Integer> ProductID;

    @FXML
    private TableColumn<ProductInfo, String> Name;

    @FXML
    private TableColumn<ProductInfo, Double> Price;

    @FXML
    private TableColumn<ProductInfo, String> Type;

    @FXML
    private TableColumn<ProductInfo, Integer> Quantity;

    @FXML
    private TableView<ProductInfo> table;

    @FXML
    private TextField searchTextField;

    private static ProductDB Productdb = new ProductDB();
    private static Store Store = new Store();

    Stage dialogStage = new Stage();
    Scene scene;

    ObservableList<ProductInfo> ProductObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ProductObservableList.clear();  // Ensure no leftover data
        ProductInfo p = null;
        try {
            ArrayList<ProductInfo> allProducts = Store.getItemsInStore();
            for(ProductInfo x : allProducts){
                    ProductObservableList.add(x);

            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        ProductID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getProductID()).asObject());
        Name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        Price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduct().getPrice()).asObject());
        Quantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        Type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getType()));


        // Set up FilteredList to allow filtering based on search text
        FilteredList<ProductInfo> ProductInfoFilteredList = new FilteredList<>(ProductObservableList, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            ProductInfoFilteredList.setPredicate(product -> {
                // Show all if search field is empty
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                // Check fields for a match
                try {
                    return String.valueOf(product.getProduct().getProductID()).contains(keyword)
                            || product.getProduct().getName().toLowerCase().contains(keyword)
                            || product.getProduct().getType().toLowerCase().contains(keyword)
                            || String.valueOf(product.getProduct().getPrice()).contains(keyword);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        });

        // Set up SortedList to allow sorting and bind it to the table view
        SortedList<ProductInfo> ProductSortedList = new SortedList<>(ProductInfoFilteredList);
        ProductSortedList.comparatorProperty().bind((ObservableValue<? extends Comparator<? super ProductInfo>>) table.comparatorProperty());

        // Set the final sorted and filtered list as the data source for the TableView
        table.setItems(ProductSortedList);
        table.refresh();


    }

    @FXML
    private void handleRemoveButton(ActionEvent event){
        TableView.TableViewSelectionModel<ProductInfo> selectionModel = table.getSelectionModel();
        ObservableList<ProductInfo> selectedItems = selectionModel.getSelectedItems();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete the selected employee?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Remove the selected items from the original employeeObservableList
                for(ProductInfo x : selectedItems){
                    try {
                        Productdb.remove(x.getQuantity());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                ProductObservableList.removeAll(selectedItems);
                // Clear the selection
                selectionModel.clearSelection();
            }
        });
    }


    public void handleCartButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/user/Cart.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleCheckoutButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/user/Checkout.fxml"));
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
