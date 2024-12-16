package Users;

import java.io.Serializable;

public class Customer extends User implements Serializable {
	private static final long serialVersionUID = -2024342845208082987L;

	public Customer(String username, String password, String email, String phonenumber) {
		super(username, password, email, phonenumber);
	}
	
}
