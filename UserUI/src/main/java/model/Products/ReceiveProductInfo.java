package model.Products;

import java.io.Serializable;

public class ReceiveProductInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Product product;
    private int quantity;
    private double receivePrice;

    public ReceiveProductInfo(Product product, int quantity, double receivePrice) {
        this.product = product;
        this.quantity = quantity;
        this.receivePrice = receivePrice;
    }

    public String getDetails() {
        return this.getProduct().getDetails() + '\n' +
                "Quantity: " + this.getQuantity() + '\n' + 
                "Receive Price: " + this.getReceivePrice();
    }
    
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getReceivePrice() {
        return this.receivePrice;
    }

    public void setReceivePrice(double receivePrice) {
        this.receivePrice = receivePrice;
    }

    @Override
    public boolean equals(Object otherobj) {
        if (this == otherobj) return true; // the same reference
        if (otherobj == null || getClass() != otherobj.getClass()) return false; // null or different class
        ReceiveProductInfo otherimportproductinfo = (ReceiveProductInfo) otherobj; //
        return this.getProduct().equals(otherimportproductinfo.getProduct()) && 
                this.getQuantity() == otherimportproductinfo.getQuantity() && 
                this.getReceivePrice() == otherimportproductinfo.getReceivePrice()
                ;
    }

}
