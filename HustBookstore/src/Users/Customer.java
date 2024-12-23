package Users;

import java.util.ArrayList;

import Cart.Cart;
import Databases.OrderDB;
import Databases.UserDB;
import Products.Product;
import Products.ProductInfo;
import Store.Store;
import Order.Order;

public class Customer extends User {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

	private Cart cart;
	public Customer(String username, String password) throws Exception {
		super(username, password, "Customer");
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
        Store s = new Store();
        this.cart.syncWithStore(s);
        UserDB userdb = new UserDB();
        userdb.update(this);
    }

    public void pay() throws Exception {
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
            Order order = new Order(this, this.getCart());
            order.print();
            for(ProductInfo pq: this.cart.getItemsInCart())
            {
                s.removeProduct(pq.getProduct(), pq.getQuantity());
            }
            this.cart.clear();
            UserDB userdb = new UserDB();
            userdb.update(this);
        }
        else
        {
            System.err.println(ANSI_RED + errstr + ANSI_RESET);
        }
    }

    public void getMyOrders() throws Exception
    {
        System.out.println("*********************** Order of " + "[" + this.getUserID() + "] " + this.getUsername() + " ***********************");
        OrderDB orderdb = new OrderDB();
        ArrayList<Order> ordersofuser = orderdb.getByUser(this);
        for(Order o : ordersofuser)
        {
            o.print();
        }
        System.out.println("***********************************************************");
    }
}
