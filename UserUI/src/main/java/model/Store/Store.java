package model.Store;

import model.Products.Book;
import model.Products.Product;
import model.Products.ProductInfo;
import model.Products.Toy;
import model.exception.DatabaseNotAvailableException;
import model.exception.StoreNotAvailableException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Store {
    private String path;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean avail;
    private ArrayList<ProductInfo> itemsInStore = new ArrayList<ProductInfo>();
    private boolean DEBUG_MODE;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public Store()
    {
        this.path = "./src/main/java/app/data/storedb.dat";
        this.avail = false;
        this.DEBUG_MODE = false;
        this.init();
    }

    public Store(String path)
    {
        this.path = path;
        this.avail = false;
        this.DEBUG_MODE = false;
        this.init();
    }

    public Store(boolean DEBUG_MODE)
    {
        this.path = "./storedb.dat";
        this.avail = false;
        this.DEBUG_MODE = DEBUG_MODE;
        this.init();
    }

    public Store(String path, boolean DEBUG_MODE)
    {
        this.path = path;
        this.avail = false;
        this.DEBUG_MODE = DEBUG_MODE;
        this.init();
    }
    public void init()
    {
        try {
            File file = new File(this.path);
            if(!file.exists() || !file.isFile())
            {
                this.itemsInStore = new ArrayList<ProductInfo>();
                this.avail = true;
            }
            else
            {
                FileInputStream fis = new FileInputStream(file);
                while(fis.available() > 0)
                {
                    this.ois = new ObjectInputStream(fis);
                    this.itemsInStore = (ArrayList<ProductInfo>)(this.ois.readObject());
                }
                if(this.ois != null)
                {
                    this.ois.close();
                    fis.close();
                }
                if(this.itemsInStore == null)
                {
                    this.itemsInStore = new ArrayList<ProductInfo>();
                }
                this.avail = true;
            }

            this.oos = new ObjectOutputStream(new FileOutputStream(new File(this.path)));
            this.oos.writeObject(this.itemsInStore);
            this.oos.close();

            if(this.DEBUG_MODE)
            {
                System.out.println(ANSI_GREEN + "Initialized database at " + this.path + ANSI_RESET);
            }

        } catch (Exception e) {
            System.err.println(ANSI_RED + "An error occurred." + ANSI_RESET);
            e.printStackTrace();
            this.avail = false;
        }
    }

    public  ArrayList<ProductInfo> getItemsInStore() throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        return this.itemsInStore;
    }

    public void addProduct(Product p) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // add 1 product p to Store
        int id = this.indexOf(p);
        if(id >= 0)
        {
            ProductInfo pq = this.itemsInStore.get(id);
            pq.setQuantity(pq.getQuantity() + 1);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully added 1 [" + p.getClass() + "] " + p.getName() + " to the Store!");
        }
        else
        {
            ProductInfo pq = new ProductInfo(p, 1);
            this.itemsInStore.add(pq);
            this.save();
            System.out.println("Successfully added 1 [" + p.getClass() + "] " + p.getName() + " to the Store!");
        }
    }

    public void removeProduct(Product p) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // remove all product p from Store
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Store!");
        }
        else
        {
            ProductInfo pq = this.itemsInStore.get(id);
            if(1 > pq.getQuantity())
            {
                System.err.println("There are only " + pq.getQuantity() + " [" + p.getClass() + "] " + p.getName() + " in the Store!");
            }
            else
            {
                pq.setQuantity(pq.getQuantity() - 1);
                this.itemsInStore.set(id, pq);
                this.save();
                System.out.println("Successfully removed 1 [" + p.getClass() + "] " + p.getName() + " from the Store!");
            }
        }
    }

    public void addProduct(Product p, int quantity) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // add {quantity} product p to Store
        int id = this.indexOf(p);
        if(id >= 0)
        {
            ProductInfo pq = this.itemsInStore.get(id);
            pq.setQuantity(pq.getQuantity() + quantity);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully added " + quantity + " [" + p.getClass() + "] " + p.getName() + " to the Store!");
        }
        else
        {
            ProductInfo pq = new ProductInfo(p, quantity);
            this.itemsInStore.add(pq);
            this.save();
            System.out.println("Successfully added " + quantity + " [" + p.getClass() + "] " + p.getName() + " to the Store!");
        }
    }

    public void removeProduct(Product p, int quantity) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // remove {quantity} product p from Store
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Store!");
        }
        else
        {
            ProductInfo pq = this.itemsInStore.get(id);
            if(quantity > pq.getQuantity())
            {
                System.err.println("There are only " + pq.getQuantity() + " [" + p.getClass() + "] " + p.getName() + " in the Store!");
            }
            else
            {
                pq.setQuantity(pq.getQuantity() - quantity);
                this.itemsInStore.set(id, pq);
                this.save();
                System.out.println("Successfully removed " + quantity + " [" + p.getClass() + "] " + p.getName() + " from the Store!");
            }
        }
    }
    public void updateNameOfProduct(Product p, String newName) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // remove {quantity} product p from Store
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Store!");
        }
        else
        {
            ProductInfo pq = this.itemsInStore.get(id);
            String OldName = pq.getProduct().getName();
            pq.getProduct().setName(newName);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully updated name of " + " [" + p.getClass() + "] " + OldName + " to " + newName + "!");
        }
    }
    public void updatePriceOfProduct(Product p, double newPrice) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // remove {quantity} product p from Store
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Store!");
        }
        else
        {
            ProductInfo pq = this.itemsInStore.get(id);
            double OldPrice = pq.getProduct().getPrice();
            pq.getProduct().setPrice(newPrice);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully updated price of " + " [" + p.getClass() + "] " + OldPrice + " to " + newPrice + "!");
        }
    }
    public void updateDescriptionOfProduct(Product p, String newDescription) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // remove {quantity} product p from Store
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Store!");
        }
        else
        {
            ProductInfo pq = this.itemsInStore.get(id);
            String oldDescription = pq.getProduct().getDescription();
            pq.getProduct().setDescription(newDescription);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully updated description of " + " [" + p.getClass() + "] " + oldDescription + " to " + newDescription + "!");
        }
    }
    public void updateQuantityOfProduct(Product p, int newQuantity) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // remove {quantity} product p from Store
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Store!");
        }
        else
        {
            ProductInfo pq = this.itemsInStore.get(id);
            int oldQuantity = pq.getQuantity();
            pq.setQuantity(newQuantity);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully updated quantity of " + " [" + p.getClass() + "] " + oldQuantity + " to " + newQuantity + "!");
        }
    }
    public void updateAuthorOfBook(Book b, String newAuthor) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // remove {quantity} product p from Store
        int id = this.indexOf(b);
        if(id == -1)
        {
            System.out.println("[" + b.getClass() + "] " + b.getName() + " is not in the Store!");
        }
        else
        {
            ProductInfo pq = this.itemsInStore.get(id);
            String oldAuthor = ((Book)(pq.getProduct())).getAuthor();
            ((Book)(pq.getProduct())).setAuthor(newAuthor);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully updated author of " + " [" + b.getClass() + "] " + oldAuthor + " to " + newAuthor + "!");
        }
    }
    public void updateBrandOfToy(Toy t, String newBrand) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // remove {quantity} product p from Store
        int id = this.indexOf(t);
        if(id == -1)
        {
            System.out.println("[" + t.getClass() + "] " + t.getName() + " is not in the Store!");
        }
        else
        {
            ProductInfo pq = this.itemsInStore.get(id);
            String oldBrand = ((Toy)(pq.getProduct())).getBrand();
            ((Toy)(pq.getProduct())).setBrand(newBrand);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully updated brand of " + " [" + t.getClass() + "] " + oldBrand + " to " + newBrand + "!");
        }
    }
    public int indexOf(Product p) throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        // index of element in itemsInStore that have .product = p
        // return -1 if not found
        for(int id = 0; id < this.itemsInStore.size(); id++)
        {
            if(this.itemsInStore.get(id).getProduct().equals(p))
            {
                return id;
            }
        }
        return -1;
    }
    public ProductInfo getByProduct(Product p) throws Exception {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        for(ProductInfo e : this.itemsInStore) {
            if(e.getProduct().equals(p)) {
                return e;
            }
        }
        return null;
    }
    public ProductInfo getByProductID(int id) throws Exception {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        for(ProductInfo e : this.itemsInStore) {
            if(e.getProduct().getProductID() == id) {
                return e;
            }
        }
        return null;
    }
    public ProductInfo getByProductName(String productname) throws Exception {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        for(ProductInfo e : this.itemsInStore) {
            if(e.getProduct().getName().equals(productname)) {
                return e;
            }
        }
        return null;
    }
    public boolean isEmpty() throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        return this.itemsInStore.isEmpty();
    }
    public int size() throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        return this.itemsInStore.size();
    }
    public boolean clear() throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        this.itemsInStore.clear();
        this.save();
        if(this.DEBUG_MODE)
        {
            System.out.println(ANSI_GREEN + "Successfully cleared database to " + this.path + " at " + ANSI_BLUE + (new Date()).toString() + ANSI_RESET);
        }
        return true;
    }
    public boolean read() throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.ois = new ObjectInputStream(new FileInputStream(new File(this.path)));
        this.itemsInStore = (ArrayList<ProductInfo>)(this.ois.readObject());
        this.ois.close();
        if(this.DEBUG_MODE)
        {
            System.out.println(ANSI_GREEN + "Successfully read data from " + this.path + " at " + ANSI_BLUE + (new Date()).toString() + ANSI_RESET);
        }
        return true;
    }
    public boolean save() throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.oos = new ObjectOutputStream(new FileOutputStream(new File(this.path)));
        this.oos.writeObject(this.itemsInStore);
        this.oos.close();
        if(this.DEBUG_MODE)
        {
            System.out.println(ANSI_GREEN + "Successfully saved database to " + this.path + " at " + ANSI_BLUE + (new Date()).toString() + ANSI_RESET);
        }
        return true;
    }
    public String getDetails() throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        String optstr = "";
        optstr += ("*********************** STORE ***********************") + '\n';
        for (ProductInfo pq : this.itemsInStore) {
            optstr += (pq.getDetails()) + '\n' + '\n';
        }
        optstr += ("******************************************************") + '\n';
        return optstr;
    }
    public void print() throws Exception
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.read();
        System.out.println(this.getDetails());
    }
}