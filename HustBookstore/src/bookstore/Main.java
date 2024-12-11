package bookstore;

import java.io.*;

import java.util.Scanner;

public class Main {
	static Scanner s;
	static Database database = new Database();
	static ShoppingCart shoppingCart = new ShoppingCart();
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {	
		File data = new File("C:\\Users\\buico\\eclipse-workspace\\HustBookstore\\Database.dat");
		File store  = new File("C:\\Users\\buico\\eclipse-workspace\\HustBookstore\\Cart.dat");
		
		if (data.exists()) {
			FileInputStream fileIn1 = new FileInputStream("Database.dat");
			ObjectInputStream in1 = new ObjectInputStream(fileIn1);
			database = (Database)in1.readObject();
			in1.close();
			fileIn1.close();
		}
		
		if (store.exists()) {
			FileInputStream fileIn2 = new FileInputStream("Cart.dat");
			ObjectInputStream in2 = new ObjectInputStream(fileIn2);
			shoppingCart = (ShoppingCart)in2.readObject();
			in2.close();
			fileIn2.close();
		}
		
		System.out.println("Welcome to HUST Bookstore.");
		System.out.println("Please choose an option");
		System.out.println("1. Log in\n2. Sign up\n3. Exit");
		
		s = new Scanner(System.in);
		int n = s.nextInt();
		
		switch(n) {
		case 1:
			login();
			break;
		case 2:
			signup();
			break;
		case 3:
			System.out.println("Bye!");
			FileOutputStream fileOut1 = new FileOutputStream("Database.dat");
			ObjectOutputStream out1 = new ObjectOutputStream(fileOut1);
			out1.writeObject(database);
			out1.close();
			fileOut1.close();
			
			FileOutputStream fileOut2 = new FileOutputStream("Cart.dat");
			ObjectOutputStream out2 = new ObjectOutputStream(fileOut2);
			out2.writeObject(shoppingCart);
			out2.close();
			fileOut2.close();
			System.exit(0);
		}
				
		FileOutputStream fileOut1 = new FileOutputStream("Database.dat");
		ObjectOutputStream out1 = new ObjectOutputStream(fileOut1);
		out1.writeObject(database);
		out1.close();
		fileOut1.close();
		
		FileOutputStream fileOut2 = new FileOutputStream("Cart.dat");
		ObjectOutputStream out2 = new ObjectOutputStream(fileOut2);
		out2.writeObject(shoppingCart);
		out2.close();
		fileOut2.close();
	}
	
	public static void login() throws IOException {
		while (true) {
			System.out.println("Enter Username: ");
			s = new Scanner(System.in);
			String username = s.next();
			System.out.println("Enter Password: ");
			s = new Scanner(System.in);
			String password = s.next();
			
			if (database.login(username, password) == true) {
				while (true) {
					System.out.println("Please choose your role:");
					System.out.println("1. Customer\n2. Staff\n3. Admin");
					s = new Scanner(System.in);
					int role = s.nextInt();
					switch(role) {
					case 1:
						if(database.checkRole(username, password, "Customer")){
							System.out.println("Login Successfully!");
							showCustomerMenu();
							break;
						}
						else {
							System.out.println("You are not a customer!");
							break;
						}
					case 2:
						if (database.checkRole(username, password, "Staff")) {
							System.out.println("Login Successfully!");
							break;
						}
						else {
							System.out.println("You are not a staff!");
							break;
						}
					case 3:
						if (database.checkRole(username, password, "Admin")) {
							System.out.println("Login Successfully!");
							break;
						}
						else {
							System.out.println("You are not an admin!");
							break;
						}
					}
					break;
				}
				break;
			}
			else {
				System.out.println("Wrong username or password");
			}
		}
	}

	
	public static void signup() throws IOException {
		User user;
		String username;

		while (true) {
			System.out.println("Enter Username: ");
			s = new Scanner(System.in);
			username = s.next();
			if (database.usedUserName(username)) {
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
		database.AddUser(user);
		login();
	}
		
		
		public static void showCustomerMenu() throws IOException {
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
					break;
				case 2:
					viewCart();
					break;
				case 3:
					login();
					break;
				case 4:
					System.out.println("Bye!");
					FileOutputStream fileOut1 = new FileOutputStream("Database.dat");
					ObjectOutputStream out1 = new ObjectOutputStream(fileOut1);
					out1.writeObject(database);
					out1.close();
					fileOut1.close();
					
					FileOutputStream fileOut2 = new FileOutputStream("Cart.dat");
					ObjectOutputStream out2 = new ObjectOutputStream(fileOut2);
					out2.writeObject(shoppingCart);
					out2.close();
					fileOut2.close();
					System.exit(0);
			}	
		}
		
		public static void viewStore() throws IOException {
			System.out.println("Store:");
			
//			Adding items to store (Only for testing! You have to create a method to do that)
//			Product book = new Book("GT3", "Education", 40000, 50, null, true, null, 0, null,
//					"Bui Xuan Dieu", null, null, "Vietnamese", null, null, null, null, 300);
//			database.addToStore(book);
//			Product toy = new Toy("PlayDoh", "Creativity", 100000, 20, null, true, null, 0, null, null,
//					3, null, null, null, null);
//			database.addToStore(toy);
//			Product stationary = new Stationary("Pencil case", "Education", 15000, 100, null, true, null, 0, null, null,
//					null, null, 10, null, null);
//			database.addToStore(stationary);
			
			database.getAllProductsInStore();
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
					Product addedItem = database.returnProductByID(id);
					if (addedItem == null) {
						System.out.println("This product is not in the store");
						viewStore();
					}
					else {
						shoppingCart.addItem(database.returnProductByID(id));
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
