package Databases.usingdict;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import Users.User;

import java.util.Date;
import java.util.ArrayList;

public class UserDB {
    private String path;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean avail;
    private HashMap<String, User> dict;
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
        this.path = "./db.dat";
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
                this.dict = new HashMap<String, User>();
                this.avail = true;
            }
            else
            {
                FileInputStream fis = new FileInputStream(file);
                while(fis.available() > 0)
                {
                    this.ois = new ObjectInputStream(fis);
                    this.dict = (HashMap<String, User>)(this.ois.readObject());
                }
                if(this.ois != null)
                {
                    this.ois.close();
                    fis.close();
                }
                if(this.dict == null)
                {
                    this.dict = new HashMap<String, User>();
                }
                this.avail = true;
            }

            this.oos = new ObjectOutputStream(new FileOutputStream(new File(this.path)));
            this.oos.writeObject(this.dict);
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

    public User set(String id, User obj) throws FileNotFoundException, IOException, ClassNotFoundException {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        this.dict.put(id, obj);
        this.write();
        return obj;
    }

    public User get(String id) {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        if(!this.has(id))
        {
            System.err.println(ANSI_RED + "ID {" + id + "} is not in the database" + ANSI_RESET);
            return null;
        }
        return this.dict.get(id);
    }

    public boolean delete(String id) throws FileNotFoundException, IOException, ClassNotFoundException {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return false;
        }
        if(!this.has(id))
        {
            System.err.println(ANSI_RED + "ID {" + id + "} is not in the database" + ANSI_RESET);
            return false;
        }
        else
        {
            this.dict.remove(id);
            this.write();
            return true;
        }
    }

    public boolean has(String id) {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return false;
        }
        return this.dict.containsKey(id);
    }

    public boolean isEmpty()
    {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return false;
        }
        return this.dict.isEmpty();
    }
    
    public ArrayList<String> keys() {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        return new ArrayList<String>(this.dict.keySet());
    }

    public ArrayList<User> values() {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        return new ArrayList<User>(this.dict.values());
    }

    public ArrayList<HashMap.Entry<String, User>> entries() {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        return new ArrayList<HashMap.Entry<String, User>>(this.dict.entrySet());
    }

    public Integer size() {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return null;
        }
        return this.dict.size();
    }

    public boolean clear() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        if(!this.avail)
        {
            System.err.println(ANSI_RED + "The database is not available" + ANSI_RESET);
            return false;
        }
        this.oos = new ObjectOutputStream(new FileOutputStream(new File(this.path)));
        this.oos.writeObject(new HashMap<String, User>());
        this.oos.close();
        this.dict.clear();
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
        this.oos.writeObject(this.dict);
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
        HashMap<String, User> data = this.dict;
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