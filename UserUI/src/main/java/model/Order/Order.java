package model.Order;

import model.Databases.OrderDB;
import model.Databases.UserDB;
import model.Products.ProductInfo;
import model.Users.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private static int idCounter = 0;

	private int orderID;
	private User user;
	private LocalDate orderDate;
	private double totalCost;
	private ArrayList<ProductInfo> productInfoList;

	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_RESET = "\u001B[0m";

	public Order(User user, ArrayList<ProductInfo> productInfoList) throws Exception {
		OrderDB orderdb = new OrderDB();
		ArrayList<Order> allorder = orderdb.getAllOrders();
		for(Order o : allorder) {
			Order.idCounter = Math.max(Order.idCounter, o.getOrderID());
		}
		this.orderID = ++Order.idCounter;
		this.user = user;
		this.orderDate = LocalDate.now();
		this.totalCost = 0;
		this.productInfoList = new ArrayList<ProductInfo>();
		for(ProductInfo pq : productInfoList)
		{
			this.productInfoList.add(pq);
			this.totalCost += pq.getProduct().getPrice() * pq.getQuantity();
		}
		orderdb.update(this);
	}

	public int getOrderID() {
		return this.orderID;
	}

	public User getUser() throws Exception {
		this.syncUserWithDB();
		return this.user;
	}

	public LocalDate getOrderDate() throws Exception {
		return orderDate;
	}

	public ArrayList<ProductInfo> getProductInfoList() throws Exception {
		return this.productInfoList;
	}

	public double getTotalCost() throws Exception {
		return this.totalCost;
	}

	public void syncUserWithDB() throws Exception {
		UserDB userdb = new UserDB();
		this.user = userdb.getByUserID(this.user.getUserID());
	}

	public String getDetails() throws Exception {
		String optstr = "";
		optstr += "Order ID : " + this.getOrderID() + '\n';
		optstr += ANSI_BLUE + "Customer: "+ this.getUser().getUsername() + ANSI_RESET + "\n";
		optstr += "Date ordered: " + this.getOrderDate() + "\n";
		for (ProductInfo pq : this.getProductInfoList()) {
			optstr += (pq.getDetails()) + '\n' + '\n';
		}
		optstr += ("Total cost : " + this.getTotalCost()) + '\n';
		return optstr;
	}

	public void print() throws Exception {
		System.out.println(this.getDetails());
	}
}