package model.Products;

import model.Databases.ProductDB;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static int idCounter = 0;
    
    private int productID;
    private String name;
    private double price;
    private String description;
    private String type;

    public Product(String name, double price, String description) throws Exception {
        ProductDB productdb = new ProductDB();
        ArrayList<Product> allproduct = productdb.getAllProducts();
        for(Product p : allproduct) {
            Product.idCounter = Math.max(Product.idCounter, p.getProductID());
        }
        this.productID = ++Product.idCounter;
        this.name = name != null ? name : "Unknown";
        this.price = price >= 0 ? price : 0.0;
        this.description = description != null ? description : "No description";
        productdb.update(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return "Product [" + this.getProductID() + "] :\n" + 
                "Name: " + this.getName() + '\n' + 
                "Price: " + this.getPrice() + '\n' + 
                "Description: " + this.getDescription()
                ;
    }

    public int getProductID() {
        return this.productID;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) throws Exception {
        this.name = name;
        ProductDB productdb = new ProductDB();
        productdb.update(this);
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) throws Exception {
        this.price = price;
        ProductDB productdb = new ProductDB();
        productdb.update(this);
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) throws Exception {
        this.description = description;
        ProductDB productdb = new ProductDB();
        productdb.update(this);
    }

	@Override
    public boolean equals(Object otherobj) {
        if (this == otherobj) return true; // the same reference
        if (otherobj == null || getClass() != otherobj.getClass()) return false; // null or different class
        Product otherproduct = (Product) otherobj; //
        // return this.getProductID() == otherproduct.getProductID() && 
		// 		this.getName().equals(otherproduct.getName()) && 
		// 		this.getPrice() == otherproduct.getPrice() && 
		// 		this.getDescription().equals(otherproduct.getDescription())
        //         ;
        return this.getProductID() == otherproduct.getProductID();
    }
}
