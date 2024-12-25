package model.Products;

import model.Databases.ProductDB;

public class Toy extends Product{
	
    private static final long serialVersionUID = 1L;
    private String brand;
    private String type = "Toy";

    public Toy(String name, double price, String description, String brand) throws Exception {
        super(name, price, description);
        this.brand = brand != null ? brand : "";
        ProductDB productdb = new ProductDB();
        productdb.update(this);
    }
    @Override
    public String getDetails() {
        return "Toy [" + this.getProductID() + "] :\n" + 
                "Name: " + this.getName() + '\n' + 
                "Price: " + this.getPrice() + '\n' + 
                "Description: " + this.getDescription() + '\n' + 
                "Brand: " + this.getBrand()
                ;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) throws Exception {
        this.brand = brand;
        ProductDB productdb = new ProductDB();
        productdb.update(this);
    }
    // @Override
    // public boolean equals(Object otherobj) {
    //     if (this == otherobj) return true; // the same reference
    //     if (otherobj == null || getClass() != otherobj.getClass()) return false; // null or different class
    //     Toy othertoy = (Toy) otherobj; //
    //     return this.getProductID() == othertoy.getProductID() && 
	// 			this.getName().equals(othertoy.getName()) && 
	// 			this.getPrice() == othertoy.getPrice() && 
	// 			this.getDescription().equals(othertoy.getDescription()) && 
    //             this.getBrand().equals(othertoy.getBrand())
    //             ;
    // }
}