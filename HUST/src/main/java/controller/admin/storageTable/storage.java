package controller.admin.storageTable;

import controller.Login;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Products.Book;
import model.Products.Product;
import model.Products.ProductInfo;
import model.Store.Store;
import model.Users.Staff;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class storage implements Initializable {
    Stage dialogStage = new Stage();
    Scene scene;

    private static Store store = new Store();
    ObservableList<ProductInfo> productObservableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<ProductInfo, Integer> id;

    @FXML
    private TableColumn<ProductInfo, String> name;

    @FXML
    private TableColumn<ProductInfo, Double> price;

    @FXML
    private TableColumn<ProductInfo, Integer> quantity;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<ProductInfo> table;

    @FXML
    private TableColumn<ProductInfo, String> type;

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

    public void handleEmployeeButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/employeeTable/employee.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/admin/storageTable/insertStorage.fxml"));
        Parent root = fxmlLoader.load();
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        productObservableList.clear();
        table.refresh();
        editData();

        ProductInfo p = null;
        try {
            ArrayList<ProductInfo> allProducts = store.getItemsInStore();
            for(ProductInfo x : allProducts){
                productObservableList.add(x);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

//        id.setCellValueFactory(new PropertyValueFactory<>("id"));
//        name.setCellValueFactory(new PropertyValueFactory<>("name"));
//        price.setCellValueFactory(new PropertyValueFactory<>("price"));
//        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getProductID()).asObject());
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduct().getPrice()).asObject());
        quantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getType()));


        FilteredList<ProductInfo> productInfoFilteredList =
                new FilteredList<>(productObservableList, b -> true);
        searchTextField.textProperty().addListener(((observableValue, oldValue, newValue) ->
                productInfoFilteredList.setPredicate(product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String keyword = newValue.toLowerCase();

                    try {
                        System.out.println(keyword);
                        System.out.println(String.valueOf(product.getProduct().getName()));
                        return String.valueOf(product.getProduct().getProductID()).contains(keyword)
                                || product.getProduct().getName().toLowerCase().contains(keyword)
                                || product.getProduct().getType().toLowerCase().contains(keyword)
                                || String.valueOf(product.getQuantity()).contains(keyword)
                                || String.valueOf(product.getProduct().getPrice()).contains(keyword);

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                })
        ));
        SortedList<ProductInfo> productInfoSortedList = new SortedList<>(productInfoFilteredList);
        productInfoSortedList.comparatorProperty().bind(table.comparatorProperty());

        // Set the final sorted and filtered list as the data source for the TableView
        table.setItems(productInfoSortedList);
        table.refresh();
    }


    private void editData() {
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(e -> {
            ProductInfo productInfo = e.getTableView().getItems().get(e.getTablePosition().getRow());
            String newName = e.getNewValue();

            if (newName == null || newName.trim().isEmpty()) {
                System.err.println("Invalid input: Name cannot be empty.");
                return;
            }
            try {
                Login.admin.updateNameOfProduct(productInfo.getProduct(),newName);;
                productInfo.getProduct().setName(newName);
//                productInfo = store.getByProduct(productInfo.getProduct());
                table.refresh();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

        price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        price.setOnEditCommit(e -> {
            ProductInfo productInfo = e.getTableView().getItems().get(e.getTablePosition().getRow());
            Double newName = e.getNewValue();

            try {
                Login.admin.updatePriceOfProduct(productInfo.getProduct(),newName);;
                productInfo.getProduct().setPrice(newName);
//                productInfo = store.getByProduct(productInfo.getProduct());
                table.refresh();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantity.setOnEditCommit(e -> {
            ProductInfo productInfo = e.getTableView().getItems().get(e.getTablePosition().getRow());
            Integer newName = e.getNewValue();

            try {
                Login.admin.updateQuantityOfProduct(productInfo.getProduct(),newName);;
                productInfo.setQuantity(newName);
//                productInfo = store.getByProduct(productInfo.getProduct());
                table.refresh();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {

        TableView.TableViewSelectionModel<ProductInfo> selectionModel = table.getSelectionModel();
        ObservableList<ProductInfo> selectedItems = selectionModel.getSelectedItems();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete the selected product?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Remove the selected items from the original employeeObservableList
                for (ProductInfo x : selectedItems) {
                    try {
                        Login.admin.removeProductFromStore(x.getProduct(), x.getQuantity());
//                        store.removeProduct(x.getProduct(), x.getQuantity());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                productObservableList.removeAll(selectedItems);
                // Clear the selection
                selectionModel.clearSelection();
            }
        });
    }
}

