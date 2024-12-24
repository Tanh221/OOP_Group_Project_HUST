package model.Users;

import model.Databases.OrderDB;
import model.Databases.ReceiveNoteDB;
import model.Databases.UserDB;
import model.Order.Order;
import model.Products.Book;
import model.Products.Product;
import model.Products.Toy;
import model.ReceiveNote.ReceiveNote;
import model.Store.Store;

import java.time.LocalDate;
import java.util.ArrayList;

public class Staff extends User {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private String phone;
    private LocalDate dateJoined;
    private float salary;

    public Staff(String username, String password, String phone, LocalDate dateJoined, float salary) throws Exception {
        super(username, password);
        this.phone = phone;
        this.dateJoined = dateJoined;
        this.salary = salary;
        UserDB userdb = new UserDB();
        userdb.update(this);
        // TODO Auto-generated constructor stub
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public String getPhone() {
        return phone;
    }

    public float getSalary() {
        return salary;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void removeProductFromStore(Product p) throws Exception {
        Store s = new Store();
        s.removeProduct(p);
    }

    public ReceiveNote addProductToStore(Product p, int quantity, double receivePrice) throws Exception {
        Store s = new Store();
        s.addProduct(p, quantity);
        ReceiveNote receiveNote = new ReceiveNote(this, p, quantity, receivePrice);
        return receiveNote;
    }

    public void removeProductFromStore(Product p, int quantity) throws Exception {
        Store s = new Store();
        s.removeProduct(p, quantity);
    }

    public void updateNameOfProduct(Product p, String newName) throws Exception {
        Store s = new Store();
        s.updateNameOfProduct(p, newName);
    }

    public void updatePriceOfProduct(Product p, double newPrice) throws Exception {
        Store s = new Store();
        s.updatePriceOfProduct(p, newPrice);
    }

    public void updateDescriptionOfProduct(Product p, String newDescription) throws Exception {
        Store s = new Store();
        s.updateDescriptionOfProduct(p, newDescription);
    }

    public void updateQuantityOfProduct(Product p, int newQuantity) throws Exception {
        Store s = new Store();
        s.updateQuantityOfProduct(p, newQuantity);
    }

    public void updateAuthorOfBook(Book b, String newAuthor) throws Exception {
        Store s = new Store();
        s.updateAuthorOfBook(b, newAuthor);
    }

    public void updateBrandOfToy(Toy t, String newBrand) throws Exception {
        Store s = new Store();
        s.updateBrandOfToy(t, newBrand);
    }

    public ArrayList<Order> getAllOrdersOfUser(User u) throws Exception {
        OrderDB orderdb = new OrderDB();
        return orderdb.getByUser(u);
    }

    public void printAllOrdersOfUser(User u) throws Exception {
        System.out.println("*********************** Orders of User " + "[" + u.getUserID() + "] " + u.getUsername() + " ***********************");
        ArrayList<Order> ordersofuser = this.getAllOrdersOfUser(u);
        for (Order o : ordersofuser) {
            o.print();
        }
        System.out.println("***********************************************************");
    }

    public Order getOrderByOrderID(int orderID) throws Exception {
        OrderDB orderdb = new OrderDB();
        Order o = orderdb.getByOrderID(orderID);
        return o;
    }

    public void printOrderByOrderID(int orderID) throws Exception {
        Order o = this.getOrderByOrderID(orderID);
        if (o != null) {
            o.print();
        } else {
            System.out.println("There is no other with id " + orderID + " in orders history!");
        }
    }

    public ArrayList<Order> getAllOrders() throws Exception {
        OrderDB orderdb = new OrderDB();
        return orderdb.getAllOrders();
    }

    public void printAllOrders() throws Exception {
        System.out.println("*********************** All Orders In Database ***********************");
        ArrayList<Order> allordersindb = this.getAllOrders();
        for (Order o : allordersindb) {
            o.print();
        }
        System.out.println("***********************************************************");
    }

    public ArrayList<ReceiveNote> getAllReceiveNotesOfUser(User u) throws Exception {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        return receivenotedb.getByUser(u);
    }

    public void printAllReceiveNotesOfUser(User u) throws Exception {
        System.out.println("*********************** Receive Notes of User " + "[" + u.getUserID() + "] " + u.getUsername() + " ***********************");
        ArrayList<ReceiveNote> receivenotesofuser = this.getAllReceiveNotesOfUser(u);
        for (ReceiveNote rn : receivenotesofuser) {
            rn.print();
        }
        System.out.println("***********************************************************");
    }

    public ReceiveNote getReceiveNoteByID(int receiveNoteID) throws Exception {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        ReceiveNote rn = receivenotedb.getByReceiveNoteID(receiveNoteID);
        return rn;
    }

    public void printReceiveNoteByID(int receiveNoteID) throws Exception {
        ReceiveNote rn = this.getReceiveNoteByID(receiveNoteID);
        if (rn != null) {
            rn.print();
        } else {
            System.out.println("There is no receive note with id " + receiveNoteID + " in receive notes history!");
        }
    }

    public ArrayList<ReceiveNote> getAllReceiveNotes() throws Exception {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        return receivenotedb.getAllReceiveNotes();
    }

    public void printAllReceiveNotes() throws Exception {
        System.out.println("*********************** All Receive Notes In Database ***********************");
        ArrayList<ReceiveNote> allreceivenotesindb = this.getAllReceiveNotes();
        for (ReceiveNote rn : allreceivenotesindb) {
            rn.print();
        }
        System.out.println("***********************************************************");
    }

    public ArrayList<ReceiveNote> getAllMyReceiveNotes() throws Exception {
        return this.getAllReceiveNotesOfUser(this);
    }

    public void printAllMyReceiveNotes() throws Exception {
        this.printAllReceiveNotesOfUser(this);
    }

    public ArrayList<ReceiveNote> getAllReceiveNotesOfProduct(Product p) throws Exception {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        return receivenotedb.getByProduct(p);
    }

    public void printAllReceiveNotesOfProduct(Product p) throws Exception {
        System.out.println("*********************** Receive Notes of Product " + "[" + p.getProductID() + "] " + p.getName() + " ***********************");
        ArrayList<ReceiveNote> receivenotesofproduct = this.getAllReceiveNotesOfProduct(p);
        for (ReceiveNote rn : receivenotesofproduct) {
            rn.print();
        }
        System.out.println("***********************************************************");
    }

    public ArrayList<ReceiveNote> getUserReceiveNoteByProduct(User u, Product p) throws Exception {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        ArrayList<ReceiveNote> userreceivenotebyproduct = receivenotedb.getByProduct(p);
        ArrayList<ReceiveNote> res = new ArrayList<ReceiveNote>();
        for (ReceiveNote rn : userreceivenotebyproduct) {
            if (rn.getUser().equals(u)) {
                res.add(rn);
            }
        }
        return res;
    }

    public void printUserReceiveNoteByProduct(User u, Product p) throws Exception {
        System.out.println("*********************** Receive Notes of Product " + "[" + p.getProductID() + "] " + p.getName() + " and User " + "[" + u.getUserID() + "] " + u.getUsername() + " ***********************");
        ArrayList<ReceiveNote> userreceivenotebyproduct = this.getUserReceiveNoteByProduct(u, p);
        for (ReceiveNote rn : userreceivenotebyproduct) {
            rn.print();
        }
        System.out.println("***********************************************************");
    }
}