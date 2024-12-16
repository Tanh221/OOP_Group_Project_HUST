package Bookstore;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

import Databases.usingarraylist.ProductDB;
import Databases.usingarraylist.UserDB;
import Products.Product;
import Users.Customer;
import Users.User;

public class Main {
	static Scanner s;
	static UserDB userdb = new UserDB();
	static ProductDB productdb = new ProductDB();
	static ShoppingCart shoppingCart = new ShoppingCart();
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		while(true)
		{
			System.out.println("Welcome to HUST Bookstore.");
			System.out.println("Please choose an option");
			System.out.println("1. Log in\n2. Sign up\n3. Exit");
			
			s = new Scanner(System.in);
			int n = s.nextInt();
			
			switch(n) {
				case 1:
					login();
					continue;
				case 2:
					signup();
					continue;
				case 3:
					System.out.println("Bye!");
					break;
				default:
					continue;
			}
			break;
		}
		System.exit(0);
	}
	
	public static void login() throws IOException {
		System.out.println("Enter Username: ");
		s = new Scanner(System.in);
		String username = s.next();
		System.out.println("Enter Password: ");
		s = new Scanner(System.in);
		String password = s.next();
		User user_obj = userdb.getByUsername(username);
		if (!Objects.isNull(user_obj)) {
			if(user_obj.getPassword().equals(password))
			{
				while (true) {
					System.out.println("Please choose your role:");
					System.out.println("1. Customer\n2. Staff\n3. Admin");
					s = new Scanner(System.in);
					int role = s.nextInt();
					switch(role) {
						case 1:
							if(user_obj.getRole().equals("Customer")){
								System.out.println("Login Successfully!");
								showCustomerMenu();
								continue;
							}
							else {
								System.out.println("You are not a customer!");
								continue;
							}
						case 2:
							if (user_obj.getRole().equals("Staff")) {
								System.out.println("Login Successfully!");
								continue;
							}
							else {
								System.out.println("You are not a staff!");
								continue;
							}
						case 3:
							if (user_obj.getRole().equals("Admin")) {
								System.out.println("Login Successfully!");
								continue;
							}
							else {
								System.out.println("You are not an admin!");
								continue;
							}
						default:
							System.out.println("Not a valid choice");
					}
					break;
				}
			}
			else
			{
				System.out.println("Wrong username or password");
			}
		}
		else {
			System.out.println("User does not exist");
		}
	}

	
	public static void signup() throws IOException, ClassNotFoundException {
		User user;
		String username;

		while (true) {
			System.out.println("Enter Username: ");
			s = new Scanner(System.in);
			username = s.next();
			User user_obj = userdb.getByUsername(username);
			if (!Objects.isNull(user_obj)) {
				System.out.println("This username has been used");
			}
			else {
				break;
			}
		}
		
		System.out.println("Enter Email: ");
		s = new Scanner(System.in);
		String email = s.next();
		System.out.println("Enter Phone Number: ");
		s = new Scanner(System.in);
		String phonenumber = s.next();
		System.out.println("Enter Password: ");
		s = new Scanner(System.in);
		String password = s.next();
		System.out.println("Your account has been successfully created");
		
		user = new Customer(username, email, phonenumber, password);
		user.setRole("Customer");
		userdb.add(user);
		login();
	}
		
		
		public static void showCustomerMenu() throws IOException {
			while(true)
			{
				System.out.println("Welcome!");
				System.out.println("Please choose an option:");
				System.out.println("1. View Store");
				System.out.println("2. View Shopping Cart");
				System.out.println("3. Log out");
				System.out.println("4. Exit");
				
				s = new Scanner(System.in);
				int n = s.nextInt();
				
				switch(n) {
					case 1:
						viewStore();
						continue;
					case 2:
						viewCart();
						continue;
					case 3:
						login();
						continue;
					case 4:
						System.out.println("Bye!");
						break;
					default:
						continue;
				}
				break;
			}
		}
		
		public static void viewStore() throws IOException {
			System.out.println("Store:");
			
			ArrayList<Product> allProduct = productdb.getAllProducts();
			if(allProduct.isEmpty())
			{
				System.out.println("The store is empty!");
			}
			else
			{
				for(Product p: allProduct)
				{
					System.out.println(p.getDetails());
				}
			}
			System.out.println("Please choose an option:");
			System.out.println("1. Add an item to cart");
			System.out.println("2. Remove an item from cart");
			System.out.println("3. Return");
			
			s = new Scanner(System.in);
			int n = s.nextInt();
			
			switch(n) {
				case 1:
					System.out.println("Please enter the product ID: ");
					s = new Scanner(System.in);
					int id = s.nextInt();
					Product addedItem = productdb.getByProductID(id);
					if (addedItem == null) {
						System.out.println("This product is not in the store");
						viewStore();
					}
					else {
						shoppingCart.addItem(productdb.getByProductID(id));
						System.out.println("Product added to cart");
						viewStore();
					}
					break;
				case 2:
					System.out.println("In Progress!"); //Cai nay duy giup minh nhe
					viewStore();
					break;
				case 3:
					showCustomerMenu();
			}
		}
		
		public static void viewCart() {
			//Duy giup minh nhe
		}
}
