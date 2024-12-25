package model.Products;

import model.Databases.ProductDB;

import java.io.Serializable;

public class Stationary extends Product{
    private static final long serialVersionUID = 1L;
    private String brand;
    private String stationaryType;
    private String type = "Stationary";

    public Stationary(String name, double price, String description, String brand, String stationaryType) throws Exception {
        super(name, price, description);
        this.brand = brand;
        this.stationaryType = stationaryType;
        ProductDB productDB = new ProductDB();
        productDB.update(this);
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
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStationaryType() {
        return stationaryType;
    }

    public void setStationaryType(String stationaryType) {
        this.stationaryType = stationaryType;
    }

    @Override
    public String getDetails() {
        return "Stationary [" +this.getProductID() +"] :\n" +
                "Name: " + this.getName() + '\n' +
                "Price: " + this.getPrice() + '\n' +
                "Brand: " + this.getBrand() + '\n' +
                "Stationary's type: " + this.getStationaryType() + '\n' +
                "Description: " + this.getDescription();
    }
}
