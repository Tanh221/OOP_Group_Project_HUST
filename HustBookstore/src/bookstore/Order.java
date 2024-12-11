package bookstore;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private Customer customer;
    private double totalAmount;
    private String orderDate;
    private String status;
    private List<Product> items;
    private String paymentMethod;
    private String paymentStatus;
    private String shippingAddress;
    private boolean canModify;

    public Order(Customer customer, List<Product> items) {
        this.customer = customer;
        this.items = items;
        this.totalAmount = calculateTotal();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.orderDate = dateFormat.format(new Date());
    }

    private double calculateTotal() {
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    public void processPayment(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "Paid";
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public boolean isCanModify() {
        return canModify;
    }
}