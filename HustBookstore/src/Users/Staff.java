package Users;

import Order.Order;
import Store.Store;

import java.util.ArrayList;

import Databases.OrderDB;
import Databases.ReceiveNoteDB;
import Databases.UserDB;
import Products.Product;
import Products.ProductInfo;
import Products.Book;
import Products.Toy;
import ReceiveNote.ReceiveNote;

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
    public void removeProductFromStore(Product p) throws Exception
    {
        Store s = new Store();
        s.removeProduct(p);
    }

    public ReceiveNote addProductToStore(Product p, int quantity, double receivePrice) throws Exception
    {
        Store s = new Store();
        s.addProduct(p, quantity);
        ReceiveNote receiveNote = new ReceiveNote(this, p, quantity, receivePrice);
        return receiveNote;
    }

    public void removeProductFromStore(Product p, int quantity) throws Exception
    {
        Store s = new Store();
        s.removeProduct(p, quantity);
    }

    public void updateNameOfProduct(Product p, String newName) throws Exception
    {
        Store s = new Store();
        s.updateNameOfProduct(p, newName);
    }
    public void updatePriceOfProduct(Product p, double newPrice) throws Exception
    {
        Store s = new Store();
        s.updatePriceOfProduct(p, newPrice);
    }
    public void updateDescriptionOfProduct(Product p, String newDescription) throws Exception
    {
        Store s = new Store();
        s.updateDescriptionOfProduct(p, newDescription);
    }
    public void updateQuantityOfProduct(Product p, int newQuantity) throws Exception
    {
        Store s = new Store();
        s.updateQuantityOfProduct(p, newQuantity);
    }
    public void updateAuthorOfBook(Book b, String newAuthor) throws Exception
    {
        Store s = new Store();
        s.updateAuthorOfBook(b, newAuthor);
    }
    public void updateBrandOfToy(Toy t, String newBrand) throws Exception
    {
        Store s = new Store();
        s.updateBrandOfToy(t, newBrand);
    }

    public ArrayList<Order> getAllOrdersOfUser(User u) throws Exception
    {
        OrderDB orderdb = new OrderDB();
        return orderdb.getByUser(u);
    }

    public void printAllOrdersOfUser(User u) throws Exception
    {
        System.out.println("*********************** Orders of " + "[" + u.getUserID() + "] " + u.getUsername() + " ***********************");
        ArrayList<Order> ordersofuser = this.getAllOrdersOfUser(u);
        for(Order o : ordersofuser)
        {
            o.print();
        }
        System.out.println("***********************************************************");
    }

    public Order getUserOrderByOrderID(User u, int orderID) throws Exception
    {
        OrderDB orderdb = new OrderDB();
        Order o = orderdb.getByOrderID(orderID);
        if(o.getUser().equals(u))
        {
            return o;
        }
        else
        {
            return null;
        }
    }

    public void printUserOrderByOrderID(User u, int orderID) throws Exception
    {
        Order o = this.getUserOrderByOrderID(u, orderID);
        if(o != null)
        {
            o.print();
        }
        else
        {
            System.out.println("There is no other with id " + orderID + " in " + u.getUsername() + " orders history!");
        }
    }

    public ArrayList<Order> getAllOrders() throws Exception
    {
        OrderDB orderdb = new OrderDB();
        return orderdb.getAllOrders();
    }

    public void printAllOrders() throws Exception
    {
        System.out.println("*********************** All Orders In Database ***********************");
        ArrayList<Order> allordersindb = this.getAllOrders();
        for(Order o : allordersindb)
        {
            o.print();
        }
        System.out.println("***********************************************************");
    }

    public ArrayList<ReceiveNote> getAllReceiveNotesOfUser(User u) throws Exception
    {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        return receivenotedb.getByUser(u);
    }

    public void printAllReceiveNotesOfUser(User u) throws Exception
    {
        System.out.println("*********************** Receive Notes of " + "[" + u.getUserID() + "] " + u.getUsername() + " ***********************");
        ArrayList<ReceiveNote> receivenotesofuser = this.getAllReceiveNotesOfUser(u);
        for(ReceiveNote rn : receivenotesofuser)
        {
            rn.print();
        }
        System.out.println("***********************************************************");
    }

    public ReceiveNote getUserReceiveNoteByID(User u, int receiveNoteID) throws Exception
    {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        ReceiveNote rn = receivenotedb.getByReceiveNoteID(receiveNoteID);
        if(rn.getUser().equals(u))
        {
            return rn;
        }
        else
        {
            return null;
        }
    }

    public void printUserReceiveNoteByID(User u, int receiveNoteID) throws Exception
    {
        ReceiveNote rn = this.getUserReceiveNoteByID(u, receiveNoteID);
        if(rn != null)
        {
            rn.print();
        }
        else
        {
            System.out.println("There is no receive note with id " + receiveNoteID + " in " + u.getUsername() + " receive notes history!");
        }
    }

    public ArrayList<ReceiveNote> getAllReceiveNotes() throws Exception
    {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        return receivenotedb.getAllReceiveNotes();
    }

    public void printAllReceiveNotes() throws Exception
    {
        System.out.println("*********************** All Receive Notes In Database ***********************");
        ArrayList<ReceiveNote> allreceivenotesindb = this.getAllReceiveNotes();
        for(ReceiveNote rn : allreceivenotesindb)
        {
            rn.print();
        }
        System.out.println("***********************************************************");
    }

    public ArrayList<ReceiveNote> getAllMyReceiveNotes() throws Exception
    {
        return this.getAllReceiveNotesOfUser(this);
    }

    public void printAllMyReceiveNotes() throws Exception
    {
        this.printAllReceiveNotesOfUser(this);
    }

    public ReceiveNote getMyReceiveNoteByID(int receiveNoteID) throws Exception
    {
        return this.getUserReceiveNoteByID(this, receiveNoteID);
    }

    public void printMyReceiveNoteByID(int receiveNoteID) throws Exception
    {
        this.printUserReceiveNoteByID(this, receiveNoteID);
    }
}
