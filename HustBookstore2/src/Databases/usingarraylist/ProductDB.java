package Databases.usingarraylist;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import Products.Product;
import Users.User;

import java.util.Date;
import java.util.ArrayList;

public class ProductDB {
    private String path;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean avail;
	private ArrayList<Product> db = new ArrayList<Product>();
    private boolean DEBUG_MODE;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public ProductDB()
    {
        this.path = "./productdb.dat";
        this.avail = false;
        this.DEBUG_MODE = false;
        this.init();
    }

    public ProductDB(String path)
    {
        this.path = path;
        this.avail = false;
        this.DEBUG_MODE = false;
        this.init();
    }

    public ProductDB(boolean DEBUG_MODE)
    {
        this.path = "./orderdb.dat";
        this.avail = false;
        this.DEBUG_MODE = DEBUG_MODE;
        this.init();
    }

    public ProductDB(String path, boolean DEBUG_MODE)
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
                this.db = new ArrayList<Product>();
                this.avail = true;
            }
            else
            {
                FileInputStream fis = new FileInputStream(file);
                while(fis.available() > 0)
                {
                    this.ois = new ObjectInputStream(fis);
                    this.db = (ArrayList<Product>)(this.ois.readObject());
                }
                if(this.ois != null)
                {
                    this.ois.close();
                    fis.close();
                }
                if(this.db == null)
                {
                    this.db = new ArrayList<Product>();
                }
                this.avail = true;
            }

            this.oos = new ObjectOutputStream(new FileOutputStream(new File(this.path)));
            this.oos.writeObject(this.db);
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

    public String getPath()
    {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        return this.path;
    }

    public Product add(Product obj) throws FileNotFoundException, IOException, ClassNotFoundException {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        this.db.add(obj);
        this.write();
        return obj;
    }

    public Product set(int index, Product obj) throws FileNotFoundException, IOException, ClassNotFoundException {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        this.db.set(index, obj);
        this.write();
        return obj;
    }

    public boolean remove(int index) throws FileNotFoundException, IOException, ClassNotFoundException {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return false;
        }
        try {
            this.db.remove(index);
            this.write();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }


    public boolean remove(Product obj) throws FileNotFoundException, IOException, ClassNotFoundException {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return false;
        }
        try {
            this.db.remove(obj);
            this.write();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public int indexOf(Product obj) throws FileNotFoundException, IOException, ClassNotFoundException {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return -1;
        }
        return this.db.indexOf(obj);
    }

    public Product getByProductID(int id) {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        for(Product e : this.db) {
            if(e.getProductID() == id) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Product> getAllProducts() {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        return this.db;
    }

    public boolean isEmpty()
    {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return false;
        }
        return this.db.isEmpty();
    }
    
    public Integer size() {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        return this.db.size();
    }

    public boolean clear() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return false;
        }
        this.oos = new ObjectOutputStream(new FileOutputStream(new File(this.path)));
        this.oos.writeObject(new HashMap<String, Product>());
        this.oos.close();
        this.db.clear();
        if(this.DEBUG_MODE)
        {
            System.out.println(ANSI_GREEN + "Successfully cleared database to " + this.path + " at " + ANSI_BLUE + (new Date()).toString() + ANSI_RESET);
        }
        return true;
    }

    public boolean write() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return false;
        }
        this.oos = new ObjectOutputStream(new FileOutputStream(new File(this.path)));
        this.oos.writeObject(this.db);
        this.oos.close();
        if(this.DEBUG_MODE)
        {
            System.out.println(ANSI_GREEN + "Successfully saved database to " + this.path + " at " + ANSI_BLUE + (new Date()).toString() + ANSI_RESET);
        }
        return true;
    }
    
    public void backupTo(String path) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return;
        }
        ArrayList<Product> data = this.db;
        this.oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
        this.oos.writeObject(data);
        this.oos.close();
        if(this.DEBUG_MODE)
        {
            System.out.println(ANSI_GREEN + "Successfully copied database to " + path + ANSI_RESET);
        }
        //(new File(this.path)).delete();
        //this.path = path;
        return;
    }

    public void changePathTo(String newPath, boolean doBackup) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return;
        }
        if(doBackup)
        {
            String backupPath;
            int idx = 1;
            while(true)
            {
                backupPath = this.path + "_backup_" + Integer.toString(idx);
                File backupfile = new File(backupPath);
                if(!backupfile.exists() || !backupfile.isFile())
                {
                    break;
                }
            }
            this.backupTo(backupPath);
        }
        this.path = newPath;
        this.avail = false;
        this.init();
    }
}