package Users;

import Order.Order;
import Store.Store;

import java.util.ArrayList;

import Databases.OrderDB;
import Databases.UserDB;
import Products.Product;
import Products.ProductQuantity;
import Products.Book;
import Products.Toy;

public class Staff extends User {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

	public Staff(String username, String password) throws Exception {
		super(username, password, "Staff");
        UserDB userdb = new UserDB();
        userdb.update(this);
		// TODO Auto-generated constructor stub
	}
    public void addProductToStore(Store s, Product p) throws Exception
    {
        s.addProduct(p);
    }

    public void removeProductFromStore(Store s, Product p) throws Exception
    {
        s.removeProduct(p);
    }

    public void addProductToStore(Store s, Product p, int quantity) throws Exception
    {
        s.addProduct(p, quantity);
    }

    public void removeProductFromStore(Store s, Product p, int quantity) throws Exception
    {
        s.removeProduct(p, quantity);
    }

    public void updateNameOfProduct(Store s, Product p, String newName) throws Exception
    {
        s.updateNameOfProduct(p, newName);
    }
    public void updatePriceOfProduct(Store s, Product p, double newPrice) throws Exception
    {
        s.updatePriceOfProduct(p, newPrice);
    }
    public void updateDescriptionOfProduct(Store s, Product p, String newDescription) throws Exception
    {
        s.updateDescriptionOfProduct(p, newDescription);
    }
    public void updateQuantityOfProduct(Store s, Product p, int newQuantity) throws Exception
    {
        s.updateQuantityOfProduct(p, newQuantity);
    }
    public void updateAuthorOfBook(Store s, Book b, String newAuthor) throws Exception
    {
        s.updateAuthorOfBook(b, newAuthor);
    }
    public void updateBrandOfToy(Store s, Toy t, String newBrand) throws Exception
    {
        s.updateBrandOfToy(t, newBrand);
    }
    public void getOrdersOfUser(User u) throws Exception
    {
        System.out.println("*********************** Order of " + "[" + u.getUserID() + "] " + u.getUsername() + " ***********************");
        OrderDB orderdb = new OrderDB();
        ArrayList<Order> ordersofuser = orderdb.getByUser(u);
        for(Order o : ordersofuser)
        {
            o.print();
        }
        System.out.println("***********************************************************");
    }
}
