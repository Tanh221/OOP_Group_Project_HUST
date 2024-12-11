package bookstore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class ShoppingCart implements Serializable {
	
	private static final long serialVersionUID = 1817377958822289264L;
	private List<Product> items;
    private double totalAmount;
    private int quantity;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product) {
        items.add(product);
        calculateTotal();
    }

    public void removeItem(Product product) {
        if (items.contains(product)) {
            items.remove(product);
        } else {
            System.out.println("This product is not in the shopping cart");
        }
        calculateTotal();
    }

    public void clear() {
        items.clear();
        totalAmount = 0;
        quantity = 0;
    }

    private void calculateTotal() {
        totalAmount = items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
   
}