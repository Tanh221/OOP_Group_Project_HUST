package Users;

import java.io.*;

public class User implements Serializable {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

	private static final long serialVersionUID = -3314716528736752227L;
	private String username;
	private String password;
	private String role;
	public User(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	public String getRole() {
		return this.role;
	}
	@Override
    public boolean equals(Object otherobj) {
        if (this == otherobj) return true; // the same reference
        if (otherobj == null || getClass() != otherobj.getClass()) return false; // null or different class
        User otheruser = (User) otherobj; //
        return this.getUsername().equals(otheruser.getUsername()) && 
				this.getPassword().equals(otheruser.getPassword()) && 
				this.getRole().equals(otheruser.getRole());
    }
}
