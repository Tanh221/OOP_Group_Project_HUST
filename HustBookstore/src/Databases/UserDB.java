package Databases;
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

import exception.DatabaseNotAvailableException;

public class UserDB {
    private String path;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean avail;
	private ArrayList<User> db = new ArrayList<User>();
    private boolean DEBUG_MODE;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public UserDB()
    {
        this.path = "./userdb.dat";
        this.avail = false;
        this.DEBUG_MODE = false;
        this.init();
    }

    public UserDB(String path)
    {
        this.path = path;
        this.avail = false;
        this.DEBUG_MODE = false;
        this.init();
    }

    public UserDB(boolean DEBUG_MODE)
    {
        this.path = "./userdb.dat";
        this.avail = false;
        this.DEBUG_MODE = DEBUG_MODE;
        this.init();
    }

    public UserDB(String path, boolean DEBUG_MODE)
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
                this.db = new ArrayList<User>();
                this.avail = true;
            }
            else
            {
                FileInputStream fis = new FileInputStream(file);
                while(fis.available() > 0)
                {
                    this.ois = new ObjectInputStream(fis);
                    this.db = (ArrayList<User>)(this.ois.readObject());
                }
                if(this.ois != null)
                {
                    this.ois.close();
                    fis.close();
                }
                if(this.db == null)
                {
                    this.db = new ArrayList<User>();
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

    public String getPath() throws DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        return this.path;
    }

    public User add(User obj) throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.db.add(obj);
        this.save();
        return obj;
    }

    public User set(int indexInDatabase, User obj) throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.db.set(indexInDatabase, obj);
        this.save();
        return obj;
    }


    public boolean remove(int indexInDatabase) throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        if(indexInDatabase <= 0 || indexInDatabase >= this.size())
        {
            System.err.println(ANSI_RED + "Index " + indexInDatabase + " out of bounds for length " + this.size() + "." + ANSI_RESET);
            return false;
        }
        else
        {
            this.db.remove(indexInDatabase);
            this.save();
            return true;
        }
    }

    public boolean remove(User obj) throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        int id = this.indexOf(obj);
        if(id <= 0 || id >= this.size())
        {
            System.err.println(ANSI_RED + obj.toString() + " hasn't been in the database." + ANSI_RESET);
            return false;
        }
        else
        {
            this.db.remove(obj);
            this.save();
            return true;
        }
    }

    public User update(User obj) throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        int index = this.indexOf(obj);
        if(index <= 0 || index >= this.size())
        {
            return this.add(obj);
        }
        else
        {
            return this.set(index, obj);
        }
    }

    public int indexOf(User obj) throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        return this.db.indexOf(obj);
    }

    public User getByUsername(String username) throws DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        for(User e : this.db) {
            if(e.getUsername().equals(username)) {
                return e;
            }
        }
        return null;
    }

    public User getByUsernameAndPassword(String username, String password) throws DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        for(User e : this.db) {
            if(e.getUsername().equals(username) && e.getPassword().equals(password)) {
                return e;
            }
        }
        return null;
    }

    public boolean isEmpty() throws DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        return this.db.isEmpty();
    }
    
    public int size() throws DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        return this.db.size();
    }

    public boolean clear() throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.oos = new ObjectOutputStream(new FileOutputStream(new File(this.path)));
        this.oos.writeObject(new HashMap<String, User>());
        this.oos.close();
        this.db.clear();
        if(this.DEBUG_MODE)
        {
            System.out.println(ANSI_GREEN + "Successfully cleared database to " + this.path + " at " + ANSI_BLUE + (new Date()).toString() + ANSI_RESET);
        }
        return true;
    }

    public boolean save() throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
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
    
    public void backupTo(String path) throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        ArrayList<User> data = this.db;
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

    public void changePathTo(String newPath, boolean doBackup) throws FileNotFoundException, IOException, ClassNotFoundException, DatabaseNotAvailableException
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
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