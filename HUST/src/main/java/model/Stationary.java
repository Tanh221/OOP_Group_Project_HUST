package model;

public class Stationary extends Product {
    private String type;
    private String material;
    private int packSize;
    private String schoolLevel;
    private String category;

    public Stationary(String name, String category, double price, int quantity, String description, boolean isActive, String manufacturer, double weight, String features, String warranty,
                      String type, String material, int packSize, String schoolLevel, String stationaryCategory) {
        super(name, category, price, quantity, description, isActive, manufacturer, weight, features, warranty);
        this.type = type != null ? type : "";
        this.material = material != null ? material : "";
        this.packSize = Math.max(0, packSize);
        this.schoolLevel = schoolLevel != null ? schoolLevel : "";
        this.category = stationaryCategory != null ? stationaryCategory : "";
    }

    public void updateStationaryDetails(String type, String material, int packSize, String schoolLevel, String category) {
        if (!type.equals(this.type)) {
            this.type = type;
        }
        if (!material.equals(this.material)) {
            this.material = material;
        }
        if (packSize != this.packSize) {
            this.packSize = packSize;
        }
        if (!schoolLevel.equals(this.schoolLevel)) {
            this.schoolLevel = schoolLevel;
        }
        if (!category.equals(this.category)) {
            this.category = category;
        }
    }

    @Override
    public String getDetails() {
        return "Stationary: " + getName() + ", Type: " + getType() + ", Material: " + getMaterial();
    }

    public double calculateBulkPrice(int quantity) {
        return 0.0f;
    }

    public String getType() {
        return type;
    }

    public String getMaterial() {
        return material;
    }

    public int getPackSize() {
        return packSize;
    }

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public String getCategory() {
        return category;
    }
}
