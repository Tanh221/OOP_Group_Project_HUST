package model;

import java.util.ArrayList;

public class Toy extends Product{
    private int suitableAgeMin;
    private String dimensions;
    private String brand;
    private String material;
    private String safetyInfo;

    public Toy(String name, String category, double price, int quantity, String description, boolean isActive, String manufacturer, double weight, String features, String warranty,
               int suitableAgeMin, String dimensions, String brand, String material, String safetyInfo) {
        super(name, category, price, quantity, description, isActive, manufacturer, weight, features, warranty);
        this.suitableAgeMin = Math.max(0, suitableAgeMin);
        this.dimensions = dimensions != null ? dimensions : "";
        this.brand = brand != null ? brand : "";
        this.material = material != null ? material : "";
        this.safetyInfo = safetyInfo != null ? safetyInfo : "";
    }

    public void updateToyDetails(int suitableAgeMin, String dimensions, String brand, String material, String safetyInfo) {
        if (suitableAgeMin != this.suitableAgeMin) {
            this.suitableAgeMin = suitableAgeMin;
        }
        if (!dimensions.equals(this.dimensions)) {
            this.dimensions = dimensions;
        }
        if (!brand.equals(this.brand)) {
            this.brand = brand;
        }
        if (!material.equals(this.material)) {
            this.material = material;
        }
        if (!safetyInfo.equals(this.safetyInfo)) {
            this.safetyInfo = safetyInfo;
        }
    }

    @Override
    public String getDetails() {
        return "Toy: " + getName() + ", Suitable Age: " + getSuitableAgeMin() + "+, Brand: " + getBrand();
    }

    public int getSuitableAgeMin() {
        return suitableAgeMin;
    }

    public String getDimensions() {
        return dimensions;
    }

    public String getBrand() {
        return brand;
    }

    public String getMaterial() {
        return material;
    }

    public String getSafetyInfo() {
        return safetyInfo;
    }
}
