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
import model.Products.Product;
import model.Products.ProductInfo;
import model.Store.Store;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class Checkout implements Initializable {
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
    @FXML
    private Label totalPriceLabel;

    private static ProductDB Productdb = new ProductDB();
    private static model.Store.Store Store = new Store();

    ObservableList<ProductInfo> ProductObservableList = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaymentMethods.getItems().addAll(methods);
        ProductObservableList.clear();  // Ensure no leftover data
        if (totalPriceLabel != null) {
            totalPriceLabel.setText("0.00$");
        }
        try {
            ArrayList<ProductInfo> allProducts = Login.customer.getCart().getItemsInCart();
            table.refresh();
            ProductObservableList.addAll(allProducts);
            updateTotalPriceLabel();
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
    private ChoiceBox<String> PaymentMethods;

    private String[] methods = {"Cash", "Mobile Banking", "Card"};

    @FXML
    public int handleOrderButton(ActionEvent event) throws Exception {
            String selectedPaymentMethod = PaymentMethods.getValue(); // Get selected value

            if (selectedPaymentMethod == null || selectedPaymentMethod.isEmpty()) {
                // Show an error if no payment method is selected
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Payment Method Required");
                alert.setHeaderText("No Payment Method Selected");
                alert.setContentText("Please select a payment method before placing your order.");
                alert.showAndWait();
            } else {
                // Proceed with the order
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setTitle("Order Placed");
                successAlert.setHeaderText("Are you sure to place an order");
                Optional<ButtonType> result = successAlert.showAndWait();
                Alert confirmalert = new Alert(Alert.AlertType.INFORMATION);
                confirmalert.setTitle("Order Placed");
                confirmalert.setHeaderText("Order Placed Successfully");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    confirmalert.setContentText("Your order has been placed successfully using: " + selectedPaymentMethod);
                    confirmalert.showAndWait();
                    processOrder(selectedPaymentMethod);
                }



            }
        return 0;
    }

        private void processOrder(String paymentMethod) throws Exception {
            for (ProductInfo x: Login.customer.getCart().getItemsInCart()){
                Store.removeProduct(x.getProduct(), x.getQuantity());
                ProductObservableList.removeIf(productInfo -> productInfo.getQuantity() <= 0);
                table.refresh();//{
            }
            // Clear the observable list to remove items from the table
            ProductObservableList.clear();
            Login.customer.getCart().getItemsInCart().clear();
            // Refresh the table view to reflect changes
            table.refresh();

            // Update the total price label
            updateTotalPriceLabel();
                // Logic to handle the order based on the selected payment method
            System.out.println("Processing order with payment method: " + paymentMethod);
        }
    private void updateTotalPriceLabel() {
        double totalPrice = 0.0;

        for (ProductInfo productInfo : ProductObservableList) {
            totalPrice += productInfo.getProduct().getPrice() * productInfo.getQuantity();
        }

        totalPriceLabel.setText(String.format("%.2f$", totalPrice));
    }

    public void handleCartButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/user/Cart.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void handleDashboardButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/user/Dashboard.fxml"));
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
