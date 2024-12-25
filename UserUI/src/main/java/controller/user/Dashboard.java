package controller.user;


import controller.Login;
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
import javafx.stage.Stage;
import model.Databases.ProductDB;
import model.Products.ProductInfo;
import model.Store.Store;
import model.Cart.Cart;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    Stage dialogStage = new Stage();
    Scene scene;
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
        static Store Store = new Store();
        public static Cart cart = new Cart();


        ObservableList<ProductInfo> ProductObservableList = FXCollections.observableArrayList();

        public void initialize(URL url, ResourceBundle resourceBundle) {

            ProductObservableList.clear();  // Ensure no leftover data
            try {
                ArrayList<ProductInfo> allProducts = Store.getItemsInStore();
                ProductObservableList.addAll(allProducts);
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
    private int handleAddtoCartButton(ActionEvent event) {
        // Get selected product
        ProductInfo selectedProduct = table.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            // Show an alert if no product is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Product Selected");
            alert.setHeaderText("No product was selected!");
            alert.setContentText("Please select a product from the list before adding to the cart.");
            alert.showAndWait();
            return -1;
        } else {
            // Proceed with adding to cart
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Add Product Quantity");
            dialog.setHeaderText("Specify the Quantity");
            dialog.setContentText("Please enter the quantity:");

            Optional<String> result = dialog.showAndWait();

            // Process the input
            if (result.isPresent()) {
                try {
                    int requestedQuantity = Integer.parseInt(result.get());

                    if (requestedQuantity <= 0) {
                        showError("Quantity must be greater than 0!");
                        return -1;
                    }

                    // Check if the product is already in the cart
                    ArrayList<ProductInfo> itemsInCart = Login.customer.getCart().getItemsInCart();
                    int currentQuantityInCart = 0;

                    for (ProductInfo cartItem : itemsInCart) {
                        if (cartItem.getProduct().getProductID() == selectedProduct.getProduct().getProductID()) {
                            currentQuantityInCart = cartItem.getQuantity();
                            break;
                        }
                    }

                    // Calculate the available stock
                    int availableStock = selectedProduct.getQuantity() - currentQuantityInCart;

                    if (requestedQuantity > availableStock) {
                        // Show an error if the requested quantity exceeds the available stock
                        showError("Requested quantity exceeds available stock! " +
                                "Available quantity: " + availableStock);
                        return -1;
                    }

                    // Add the product to the cart
                    Login.customer.addProductToCart(selectedProduct.getProduct(), requestedQuantity);

                    // Show success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Product Added");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Product has been successfully added to the cart!");
                    successAlert.showAndWait();
                    return requestedQuantity;
                } catch (NumberFormatException e) {
                    showError("Invalid input! Please enter a valid number.");
                    return -1;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                // User closed the dialog or pressed cancel
                return -1;
            }
        }
    }


    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

        // Process the input

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
