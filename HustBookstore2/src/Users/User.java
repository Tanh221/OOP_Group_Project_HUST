package Users;

import java.io.*;

public class User implements Serializable{
	private static final long serialVersionUID = -3314716528736752227L;
	private String username;
	private String password;
	private String email;
	private String phonenumber;
	private String role;
	public User(String username, String email, String phonenumber, String password) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.phonenumber = phonenumber;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRole() {
		return role;
	}
	
}
