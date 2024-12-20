package Store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import Products.Product;
import Products.ProductQuantity;
import Products.Toy;
import exception.StoreNotAvailableException;
import Products.Book;

public class Store {
    private String path;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean avail;
    private ArrayList<ProductQuantity> itemsInStore = new ArrayList<ProductQuantity>();
    private boolean DEBUG_MODE;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public Store()
    {
        this.path = "./storedb.dat";
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
                this.itemsInStore = new ArrayList<ProductQuantity>();
                this.avail = true;
            }
            else
            {
                FileInputStream fis = new FileInputStream(file);
                while(fis.available() > 0)
                {
                    this.ois = new ObjectInputStream(fis);
                    this.itemsInStore = (ArrayList<ProductQuantity>)(this.ois.readObject());
                }
                if(this.ois != null)
                {
                    this.ois.close();
                    fis.close();
                }
                if(this.itemsInStore == null)
                {
                    this.itemsInStore = new ArrayList<ProductQuantity>();
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

    public ArrayList<ProductQuantity> getItemsInStore() throws StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        return this.itemsInStore;
    }

    public void addProduct(Product p) throws FileNotFoundException, IOException, ClassNotFoundException, StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        // add 1 product p to Store
        int id = this.indexOf(p);
        if(id >= 0)
        {
            ProductQuantity pq = this.itemsInStore.get(id);
            pq.setQuantity(pq.getQuantity() + 1);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully added 1 [" + p.getClass() + "] " + p.getName() + " to the Store!");
        }
        else
        {
            ProductQuantity pq = new ProductQuantity(p, 1);
            this.itemsInStore.add(pq);
            this.save();
            System.out.println("Successfully added 1 [" + p.getClass() + "] " + p.getName() + " to the Store!");
        }
    }

    public void removeProduct(Product p) throws FileNotFoundException, IOException, ClassNotFoundException, StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        // remove all product p from Store
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Store!");
        }
        else
        {
            ProductQuantity pq = this.itemsInStore.get(id);
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

    public void addProduct(Product p, int quantity) throws FileNotFoundException, IOException, ClassNotFoundException, StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        // add {quantity} product p to Store
        int id = this.indexOf(p);
        if(id >= 0)
        {
            ProductQuantity pq = this.itemsInStore.get(id);
            pq.setQuantity(pq.getQuantity() + quantity);
            this.itemsInStore.set(id, pq);
            this.save();
            System.out.println("Successfully added " + quantity + " [" + p.getClass() + "] " + p.getName() + " to the Store!");
        }
        else
        {
            ProductQuantity pq = new ProductQuantity(p, quantity);
            this.itemsInStore.add(pq);
            this.save();
            System.out.println("Successfully added " + quantity + " [" + p.getClass() + "] " + p.getName() + " to the Store!");
        }
    }

    public void removeProduct(Product p, int quantity) throws FileNotFoundException, IOException, ClassNotFoundException, StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        // remove {quantity} product p from Store
        int id = this.indexOf(p);
        if(id == -1)
        {
            System.out.println("[" + p.getClass() + "] " + p.getName() + " is not in the Store!");
        }
        else
        {
            ProductQuantity pq = this.itemsInStore.get(id);
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
    public int indexOf(Product p) throws StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
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
    public ProductQuantity getByProductID(int id) throws StoreNotAvailableException {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        for(ProductQuantity e : this.itemsInStore) {
            if(e.getProduct().getProductID() == id) {
                return e;
            }
        }
        return null;
    }
    public ProductQuantity getByProductName(String productname) throws StoreNotAvailableException {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        for(ProductQuantity e : this.itemsInStore) {
            if(e.getProduct().getName().equals(productname)) {
                return e;
            }
        }
        return null;
    }
    public boolean isEmpty() throws StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        return this.itemsInStore.isEmpty();
    }
    public int size() throws StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        return this.itemsInStore.size();
    }
    public boolean clear() throws FileNotFoundException, IOException, ClassNotFoundException, StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        this.itemsInStore.clear();
        this.save();
        if(this.DEBUG_MODE)
        {
            System.out.println(ANSI_GREEN + "Successfully cleared database to " + this.path + " at " + ANSI_BLUE + (new Date()).toString() + ANSI_RESET);
        }
        return true;
    }
    public boolean save() throws FileNotFoundException, IOException, ClassNotFoundException, StoreNotAvailableException
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
    public String getDetails() throws StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        String optstr = "";
        optstr += ("*********************** STORE ***********************") + '\n';
        for (ProductQuantity pq : this.itemsInStore) {
            optstr += (pq.getDetails()) + '\n';
        }
        optstr += ("******************************************************") + '\n';
        return optstr;
    }
    public void print() throws StoreNotAvailableException
    {
        if(!this.avail)
        {
            throw new StoreNotAvailableException(ANSI_RED + "The Store is not available" + ANSI_RESET);
        }
        System.out.println(this.getDetails());
    }
}