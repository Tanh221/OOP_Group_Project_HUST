package model.Users;

import model.Databases.UserDB;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";

	private static final long serialVersionUID = -3314716528736752227L;

	private static int idCounter = 0;

	private int userID;

	private String username;
	private String password;
	private String role;
	public User(String username, String password) throws Exception {
		UserDB userdb = new UserDB();
		ArrayList<User> alluser = userdb.getAllUsers();
		for(User u : alluser) {
			User.idCounter = Math.max(User.idCounter, u.getUserID());
		}
		this.userID = ++User.idCounter;
		this.username = username;
		this.password = password;
		this.role = this.getClass().getSimpleName();
		userdb.update(this);
	}
	public int getUserID() {
		return this.userID;
	}
	public String getUsername() throws Exception {
		return this.username;
	}
	public void setUsername(String username) throws Exception {
		this.username = username;
		UserDB userdb = new UserDB();
		userdb.update(this);
	}
	public String getPassword() throws Exception {
		return this.password;
	}
	public void setPassword(String password) throws Exception {
		this.password = password;
		UserDB userdb = new UserDB();
		userdb.update(this);
	}
	public String getRole() throws Exception {
		return this.role;
	}
	public void setRole(String role) throws Exception {
		this.role = role;
		UserDB userdb = new UserDB();
		userdb.update(this);
	}
	@Override
	public boolean equals(Object otherobj) {
		if (this == otherobj) return true; // the same reference
		if (otherobj == null || getClass() != otherobj.getClass()) return false; // null or different class
		User otheruser = (User) otherobj; //
		// return this.getUsername().equals(otheruser.getUsername()) &&
		// 		this.getPassword().equals(otheruser.getPassword()) && 
		// 		this.getRole().equals(otheruser.getRole());
		return this.getUserID() == otheruser.getUserID();
	}
}
