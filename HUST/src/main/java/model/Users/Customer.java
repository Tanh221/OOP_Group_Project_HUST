package model.Users;

import model.Cart.Cart;
import model.Databases.OrderDB;
import model.Databases.UserDB;
import model.Order.Order;
import model.Products.Product;
import model.Products.ProductInfo;
import model.Store.Store;

import java.util.ArrayList;

public class Customer extends User {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private Cart cart;
    public Customer(String username, String password) throws Exception {
        super(username, password);
        this.cart = new Cart();
        UserDB userdb = new UserDB();
        userdb.update(this);
    }

    public Cart getCart() throws Exception {
        this.syncCartWithStore();
        return this.cart;
    }

    public void setCart(Cart c) throws Exception {
        this.cart = c;
        this.syncCartWithStore();
        UserDB userdb = new UserDB();
        userdb.update(this);
    }

    public void addProductToCart(Product p) throws Exception {
        // add 1 product p to Store s
        this.cart.addProduct(p);
        this.syncCartWithStore();
        UserDB userdb = new UserDB();
        userdb.update(this);
    }

    public void removeProductFromCart(Product p) throws Exception {
        // remove all product p from Store s
        this.cart.removeProduct(p);
        this.syncCartWithStore();
        UserDB userdb = new UserDB();
        userdb.update(this);
    }

    public void addProductToCart(Product p, int quantity) throws Exception {
        // add {quantity} product p to Store s
        this.cart.addProduct(p, quantity);
        this.syncCartWithStore();
        UserDB userdb = new UserDB();
        userdb.update(this);
    }

    public void removeProductFromCart(Product p, int quantity) throws Exception {
        // remove {quantity} product p from Store s
        this.cart.removeProduct(p, quantity);
        this.syncCartWithStore();
        UserDB userdb = new UserDB();
        userdb.update(this);
    }

    public void syncCartWithStore() throws Exception {
        // userdb.syncWithDB(this);
        this.cart.syncWithStore();
        UserDB userdb = new UserDB();
        userdb.update(this);
    }

    public Order pay() throws Exception {
        Store s = new Store();
        this.syncCartWithStore();
        Boolean check = true;
        String errstr = "";
        for(ProductInfo pq: this.cart.getItemsInCart())
        {
            ProductInfo spq = s.getByProduct(pq.getProduct());
            if(spq.getQuantity() < pq.getQuantity())
            {
                check = false;
                errstr += "There are only " + spq.getQuantity() + " [" + spq.getProduct().getName() + "] in the Store" + " " +
                        "while there are " + pq.getQuantity() + " [" + pq.getProduct().getName() + "] in your cart" + '\n';
            }
        }
        if(check)
        {
            Order order = new Order(this, this.getCart().getItemsInCart());
            order.print();
            for(ProductInfo pq: this.cart.getItemsInCart())
            {
                s.removeProduct(pq.getProduct(), pq.getQuantity());
            }
            this.cart.clear();
            UserDB userdb = new UserDB();
            userdb.update(this);
            return order;
        }
        else
        {
            System.err.println(ANSI_RED + errstr + ANSI_RESET);
            return null;
        }
    }

    public ArrayList<Order> getAllMyOrders() throws Exception
    {
        OrderDB orderdb = new OrderDB();
        return orderdb.getByUser(this);
    }

    public void printAllMyOrders() throws Exception
    {
        System.out.println("*********************** Orders of " + "[" + this.getUserID() + "] " + this.getUsername() + " ***********************");
        ArrayList<Order> ordersofuser = this.getAllMyOrders();
        for(Order o : ordersofuser)
        {
            o.print();
        }
        System.out.println("***********************************************************");
    }

    public Order getMyOrderByID(int orderID) throws Exception
    {
        OrderDB orderdb = new OrderDB();
        Order o = orderdb.getByOrderID(orderID);
        if(o.getUser().equals(this))
        {
            return o;
        }
        else
        {
            return null;
        }
    }

    public void printMyOrderByID(int orderID) throws Exception
    {
        Order o = this.getMyOrderByID(orderID);
        if(o != null)
        {
            o.print();
        }
        else
        {
            System.out.println("There is no other with id " + orderID + " in " + this.getUsername() + " orders history!");
        }
    }
}

