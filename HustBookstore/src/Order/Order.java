package Order;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import Cart.Cart;
import Products.Product;
import Products.ProductQuantity;
import Users.User;

import Databases.OrderDB;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private static int idCounter = 0;
    
    private int orderID;
	private User user;
	private LocalDate orderDate;
	private double totalCost;
	private Cart cart;
	
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";
    
	public Order(User user, Cart cart) throws Exception {
        OrderDB orderdb = new OrderDB();
        ArrayList<Order> allorder = orderdb.getAllOrders();
        for(Order o : allorder) {
            Order.idCounter = Math.max(Order.idCounter, o.getOrderID());
        }
        this.orderID = ++Order.idCounter;
		this.user = user;
		this.orderDate = LocalDate.now();
		this.totalCost = cart.getTotalCost();
		this.cart = cart;
        orderdb.update(this);
	}
	
    public int getOrderID() {
        return this.orderID;
    }

    public User getUser() throws Exception {
        return this.user;
    }

	public Cart getCart() throws Exception {
		return this.cart;
	}
	
	public double getTotalCost() throws Exception {
		return this.totalCost;
	}
	
	public String getDetails() throws Exception {
		String optstr = "";
        optstr += "Order ID : " + this.getOrderID() + '\n';
		optstr += ANSI_BLUE + "Customer: "+ this.getUser().getUsername() + ANSI_RESET + "\n";
		optstr += "Date ordered: " + orderDate + "\n"; 
        for (ProductQuantity pq : this.getCart().getItemsInCart()) {
            optstr += (pq.getDetails()) + '\n' + '\n';
        }
        optstr += ("Total cost : " + this.getTotalCost()) + '\n';
        return optstr;
	}
	
	public void print() throws Exception {
		System.out.println(this.getDetails());
	}
}