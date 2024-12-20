package Products;

import java.io.Serial;
import java.io.Serializable;

import Users.User;

public abstract class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static int idCounter = 0;
    
    private int productID;
    private String name;
    private double price;
    private String description;

    public Product(String name, double price, String description) {
        this.productID = ++Product.idCounter;
        this.name = name != null ? name : "Unknown";
        this.price = price >= 0 ? price : 0.0;
        this.description = description != null ? description : "No description";
    }

    public String getDetails() {
        return "Product [" + this.getProductID() + "] :\n" + 
                "Name: " + this.getName() + '\n' + 
                "Price: " + this.getPrice() + '\n' + 
                "Description: " + this.getDescription()
                ;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	@Override
    public boolean equals(Object otherobj) {
        if (this == otherobj) return true; // the same reference
        if (otherobj == null || getClass() != otherobj.getClass()) return false; // null or different class
        Product otherproduct = (Product) otherobj; //
        return this.getProductID() == otherproduct.getProductID() && 
				this.getName().equals(otherproduct.getName()) && 
				this.getPrice() == otherproduct.getPrice() && 
				this.getDescription().equals(otherproduct.getDescription())
                ;
    }
}
