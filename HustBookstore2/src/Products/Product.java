package Products;

import java.io.Serial;
import java.io.Serializable;

public abstract class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int productID;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private String description;
    private boolean isActive;
    private String manufacturer;
    private double weight;
    private String features;
    private String warranty;

    private static int idCounter = 0;

    public Product() {
        this("Unknown", "Uncategorized", 0.0, 0, "No description", false, "Unknown", 0.0, "No features", "No warranty");
    }

    public Product(String name, String category, double price, int quantity, String description, boolean isActive, String manufacturer, double weight, String features, String warranty) {
        this.productID = ++idCounter; // Automatically increment ID counter
        this.name = name != null ? name : "Unknown";
        this.category = category != null ? category : "Uncategorized";
        this.price = price >= 0 ? price : 0.0;
        this.quantity = quantity >= 0 ? quantity : 0;
        this.description = description != null ? description : "No description";
        this.isActive = isActive;
        this.manufacturer = manufacturer != null ? manufacturer : "Unknown";
        this.weight = weight >= 0 ? weight : 0.0;
        this.features = features != null ? features : "No features";
        this.warranty = warranty != null ? warranty : "No warranty";
    }

    public abstract String getDetails();

    public int getNumberOfProductsSold() {
        return 0;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getWeight() {
        return weight;
    }

    public String getFeatures() {
        return features;
    }

    public String getWarranty() {
        return warranty;
    }

    public static int getIdCounter() {
        return idCounter;
    }
}
