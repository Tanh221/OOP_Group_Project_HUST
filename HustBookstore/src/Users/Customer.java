package Users;

import Cart.Cart;
import Products.Product;
import Products.ProductQuantity;
import Store.Store;

public class Customer extends User {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

	private Cart cart;
	public Customer(String username, String password) {
		super(username, password, "Customer");
		this.cart = new Cart();
	}

    public Cart getCart() {
        return this.cart;
    }

    public void addProductToCart(Product p) {
        // add 1 product p to Store s
		this.cart.addProduct(p);
    }

    public void removeProductFromCart(Product p) {
        // remove all product p from Store s
		this.cart.removeProduct(p);
    }

    public void addProductToCart(Product p, int quantity) {
        // add {quantity} product p to Store s
		this.cart.addProduct(p, quantity);
    }

    public void removeProductFromCart(Product p, int quantity) {
        // remove {quantity} product p from Store s
		this.cart.removeProduct(p, quantity);
    }

    public void pay(Store s) throws Exception {
        Boolean check = true;
        String errstr = "";
        for(ProductQuantity pq: this.cart.getItemsInCart())
        {
            ProductQuantity spq = s.getByProduct(pq.getProduct());
            if(spq.getQuantity() < pq.getQuantity())
            {
                check = false;
                errstr += "There are only " + spq.getQuantity() + " [" + spq.getProduct().getName() + "] in the Store" + " " +
                            "while there are " + pq.getQuantity() + " [" + pq.getProduct().getName() + "] in your cart" + '\n';
            }
        }
        if(check)
        {
            this.cart.print();
            for(ProductQuantity pq: this.cart.getItemsInCart())
            {
                s.removeProduct(pq.getProduct(), pq.getQuantity());
            }
            this.cart.clear();
        }
        else
        {
            System.err.println(ANSI_RED + errstr + ANSI_RESET);
        }
    }
}
