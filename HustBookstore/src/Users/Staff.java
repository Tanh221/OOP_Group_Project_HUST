package Users;

import Store.Store;
import Products.Product;
import Products.ProductQuantity;

public class Staff extends User {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

	public Staff(String username, String password) {
		super(username, password, "Staff");
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
}
