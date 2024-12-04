package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inventory {
    private int itemId;
    private int currentStock;
    private int minimumStock;
    private int maximumStock;
    private String lastRestockDate;
    private String shippingManifest;

    public Inventory() {
        this.itemId = 0;
        this.currentStock = 0;
        this.minimumStock = 0;
        this.maximumStock = 0;
        this.lastRestockDate = "";
        this.shippingManifest = "";
    }

    public Inventory(int itemId, int currentStock, int minimumStock, int maximumStock) {
        this.itemId = itemId;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
        this.maximumStock = maximumStock;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.lastRestockDate = dateFormat.format(new Date());
    }

    public boolean checkStockLevel() {
        return currentStock >= minimumStock;
    }

    public void updateStock(int newStock) {
        this.currentStock = newStock;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public int getMaximumStock() {
        return maximumStock;
    }

    public String getLastRestockDate() {
        return lastRestockDate;
    }

    public String getShippingManifest() {
        return shippingManifest;
    }
}
