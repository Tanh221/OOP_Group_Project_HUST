package model.db;

import model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class DBManager {
    private final String filePath;
    private List<Product> products;

    public DBManager(String filePath) {
        this.filePath = filePath;
        this.products = new ArrayList<>();
        loadData();
    }

    // Load data from file
    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                products = (List<Product>) ois.readObject();
                System.out.println("Data loaded from file.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Failed to load data: " + e.getMessage());
            }
        } else {
            System.out.println("No existing data found. Starting fresh.");
        }
    }

    // Save data to file
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(products);
            System.out.println("Data saved to file.");
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
    }

    // Add product
    public void addProduct(Product product) {
        products.add(product);
    }

    // Get all products
    public List<Product> getProducts() {
        return products;
    }

    // Display all products
    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            products.forEach(product -> System.out.println(product.getDetails()));
        }
    }
}
