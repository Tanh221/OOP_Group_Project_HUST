package model.Databases;

import model.Users.User;
import model.exception.DatabaseNotAvailableException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

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
        this.path = "./src/main/java/app/data/userdb.dat";
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

    public String getPath() throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        return this.path;
    }

    public User add(User obj) throws Exception {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        this.db.add(obj);
        this.save();
        return obj;
    }

    public User set(int indexInDatabase, User obj) throws Exception {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        if(indexInDatabase < 0 || indexInDatabase >= this.size())
        {
            System.err.println(ANSI_RED + "Index " + indexInDatabase + " out of bounds for length " + this.size() + "." + ANSI_RESET);
            return null;
        }
        else
        {
            this.db.set(indexInDatabase, obj);
        }
        this.save();
        return obj;
    }


    public boolean remove(int indexInDatabase) throws Exception {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        if(indexInDatabase < 0 || indexInDatabase >= this.size())
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

    public boolean remove(User obj) throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        int id = this.indexOf(obj);
        if(id < 0 || id >= this.size())
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

    public User update(User obj) throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        int index = this.indexOf(obj);
        if(index < 0 || index >= this.size())
        {
            return this.add(obj);
        }
        else
        {
            return this.set(index, obj);
        }
    }

    public int indexOf(User obj) throws Exception {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        return this.db.indexOf(obj);
    }
    public User getByUserID(int id) throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        for(User e : this.db) {
            if(e.getUserID() == id) {
                return e;
            }
        }
        return null;
    }

    public User getByUsername(String username) throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        for(User e : this.db) {
            if(e.getUsername().equals(username)) {
                return e;
            }
        }
        return null;
    }

    public User getByUsernameAndPassword(String username, String password) throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        for(User e : this.db) {
            if(e.getUsername().equals(username) && e.getPassword().equals(password)) {
                return e;
            }
        }
        return null;
    }
    public ArrayList<User> getAllUsers() throws Exception {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        return this.db;
    }
    public void syncWithDB(User user) throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        User user_in_db = this.getByUserID(user.getUserID());
        if(user_in_db == null)
        {
            System.err.println(ANSI_RED + user.toString() + " hasn't been in the database." + ANSI_RESET);
        }
        else
        {
            user = user_in_db;
            System.out.println("Successfully synced " + user.toString() + " with DB!");
        }
    }
    public boolean isEmpty() throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        return this.db.isEmpty();
    }
    
    public int size() throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        return this.db.size();
    }

    public boolean clear() throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
        this.db.clear();
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
        this.db = (ArrayList<User>)(this.ois.readObject());
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
    
    public void backupTo(String path) throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
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

    public void changePathTo(String newPath, boolean doBackup) throws Exception
    {
        if(!this.avail)
        {
            throw new DatabaseNotAvailableException(ANSI_RED + "The database is not available" + ANSI_RESET);
        }
        this.read();
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