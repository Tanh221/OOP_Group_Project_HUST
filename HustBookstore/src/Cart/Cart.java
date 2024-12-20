package Cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Products.Product;
import Products.ProductQuantity;
import Products.Toy;
import Products.Book;

public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

    private ArrayList<ProductQuantity> itemsInCart = new ArrayList<ProductQuantity>();
    private double totalCost = 0;
    
    public ArrayList<ProductQuantity> getItemsInCart() {
        return this.itemsInCart;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public void addProduct(Product p) {
        // add 1 product p to Cart
        int id = this.indexOf(p);
        if(id >= 0)
        {
            ProductQuantity pq = this.itemsInCart.get(id);
            pq.setQuantity(pq.getQuantity() + 1);
            this.itemsInCart.set(id, pq);
            this.totalCost += p.getPrice();
            System.out.println("Successfully added 1 [" + p.getClass() + "] " + p.getName() + " to the Cart!");
        }
        else
        {
            ProductQuantity pq = new ProductQuantity(p, 1);
            this.itemsInCart.add(pq);
            this.totalCost += p.getPrice();
            System.out.println("Successfully added 1 [" + p.getClass() + "] " + p.getName() + " to the Cart!");
        }
    }

    public void removeProduct(Product p) {
        // remove all product p from Cart
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Cart!");
        }
        else
        {
            ProductQuantity pq = this.itemsInCart.get(id);
            if(1 > pq.getQuantity())
            {
                System.err.println("There are only " + pq.getQuantity() + " [" + p.getClass() + "] " + p.getName() + " in the Cart!");
            }
            else
            {
                pq.setQuantity(pq.getQuantity() - 1);
                this.itemsInCart.set(id, pq);
                this.totalCost -= p.getPrice();
                System.out.println("Successfully removed 1 [" + p.getClass() + "] " + p.getName() + " from the Cart!");
            }
        }
    }

    public void addProduct(Product p, int quantity) {
        // add {quantity} product p to Cart
        int id = this.indexOf(p);
        if(id >= 0)
        {
            ProductQuantity pq = this.itemsInCart.get(id);
            pq.setQuantity(pq.getQuantity() + quantity);
            this.itemsInCart.set(id, pq);
            this.totalCost += p.getPrice() * quantity;
            System.out.println("Successfully added " + quantity + " [" + p.getClass() + "] " + p.getName() + " to the Cart!");
        }
        else
        {
            ProductQuantity pq = new ProductQuantity(p, quantity);
            this.itemsInCart.add(pq);
            this.totalCost += p.getPrice() * quantity;
            System.out.println("Successfully added " + quantity + " [" + p.getClass() + "] " + p.getName() + " to the Cart!");
        }
    }

    public void removeProduct(Product p, int quantity) {
        // remove {quantity} product p from Cart
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Cart!");
        }
        else
        {
            ProductQuantity pq = this.itemsInCart.get(id);
            if(quantity > pq.getQuantity())
            {
                System.err.println("There are only " + pq.getQuantity() + " [" + p.getClass() + "] " + p.getName() + " in the Cart!");
            }
            else
            {
                pq.setQuantity(pq.getQuantity() - quantity);
                this.itemsInCart.set(id, pq);
                this.totalCost -= p.getPrice() * quantity;
                System.out.println("Successfully removed " + quantity + " [" + p.getClass() + "] " + p.getName() + " from the Cart!");
            }
        }
    }

    public int indexOf(Product p) {
        // index of element in itemsInCart that have .product = p
        // return -1 if not found
        for(int id = 0; id < this.itemsInCart.size(); id++)
        {
            if(this.itemsInCart.get(id).getProduct().equals(p))
            {
                return id;
            }
        }
        return -1;
    }

    public void clear() {
        this.itemsInCart.clear();
    }

    public String getDetails() {
        String optstr = "";
        optstr += ("*********************** CART ***********************") + '\n';
        for (ProductQuantity pq : this.itemsInCart) {
            optstr += (pq.getDetails()) + '\n' + '\n';
        }
        optstr += ("*****************************************************") + '\n';
        return optstr;
    }
    public void print() {
        System.out.println(this.getDetails());
    }
}