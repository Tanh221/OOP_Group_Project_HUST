package bookstore;

import java.io.*;
import java.util.ArrayList;

public class Database implements Serializable{

	private static final long serialVersionUID = 6869897969746594870L;
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Product> store= new ArrayList<Product>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	
	public void AddUser(User user) {
		users.add(user);
	}
	
	public boolean login(String username, String password) {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkRole(String username, String password, String role) {
		for (User user: users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password) && 
					user.getRole().equals(role)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean usedUserName(String username) {
		for (User user: users) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public void getAllProductsInStore() {
		if (store == null) {
			System.out.println("The store is empty!");
		}
		else {
			for (Product product: store) {
				System.out.println(product.getDetails());
			}
		}
	}
	
	public void addToStore(Product product) {
		store.add(product);
	}
	
	public void removeFromStore(Product product) {
        if (store.contains(product)) {
            store.remove(product);
        } else {
            System.out.println("This product is not in the store");
        }
	}
	
	public Product returnProductByID(int id) {
		if (store == null) {
			return null;
		}
		for (Product product: store) {
			if (id == product.getProductID()) {
				return product;
			}
		}
		return null;
	}
	
}
