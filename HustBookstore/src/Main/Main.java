package Main;

import java.io.*;

import Cart.Cart;
import Store.Store;
import Databases.UserDB;

import java.util.ArrayList;

import Products.Product;
import Products.Book;
import Products.Toy;
import Products.ProductQuantity;
import Users.Customer;
import Users.Staff;
import Users.Admin;
import Users.User;

public class Main {
	private static final Boolean FIRST_RUN = true;

	private static UserDB userdb = new UserDB();
	private static Store store = new Store();

	public static void main(String[] args) throws Exception
	{
		if(FIRST_RUN)
		{
			init(); // khởi tạo lần đầu chạy khi test
		}
		else
		{
			User user1 = userdb.getByUsernameAndPassword("Pham Duc Duy", "Hoc Dot"); // get user từ database, về cơ bản lúc cắm vào UI thì cho login
			System.out.println(user1); // user1 sẽ là Staff
			Staff staff1 = (Staff) user1;
	
			/*
			Sẽ sinh lỗi :
			Customer user2 = (Customer)userdb.getByUsernameAndPassword("Pham Duc Duy", "Hoc Dot"); // get user từ database, về cơ bản lúc cắm vào UI thì cho login
			System.out.println(user2);
			*/
			User user2 = userdb.getByUsernameAndPassword("Bui Cong Minh", "Em Fan Anh 7");
			System.out.println(user2); // user2 sẽ là customer
			Customer customer1 = (Customer) user2;
	
			// giả sử thằng customer1 lướt store để mua 1 bút bi
			ProductQuantity pq1 = store.getByProductName("But bi");
			if(pq1 == null) // nếu ko tồn tại sản phẩm
			{
				// làm gì đó ở đây :D
			}
			else // có tồn tại sản phầm
			{
				if(pq1.getQuantity() >= 1) // nếu trong store có đủ tối thiểu 1 cái bút bi
				{
					// add 1 bút bi vào giỏ hàng
					customer1.addProductToCart(pq1.getProduct());
				}
				else
				{
					// báo lỗi
				}
			}
			userdb.update(customer1); // update customer1 (to save the cart);
	
			// giả sử thằng customer1 lướt store để mua 3 cái tai nghe 100 củ
			ProductQuantity pq2 = store.getByProductName("Tai nghe 100 cu");
			if(pq2 == null) // nếu ko tồn tại sản phẩm
			{
				// làm gì đó ở đây :D
			}
			else // có tồn tại sản phầm
			{
				// (thực tế store chỉ có 2 tai nghe)
				if(pq2.getQuantity() >= 3)  // nếu trong store có đủ tối thiểu 3 tai nghe
				{
					customer1.addProductToCart(pq2.getProduct(), 3);
				}
				else
				{
					// báo lỗi
				}
			}
	
			// customer cay quá đành chỉ mua 2 tai nghe
			if(pq2.getQuantity() >= 2)
			{
				// chạy được vì store có 2 tai nghe
				customer1.addProductToCart(pq2.getProduct(), 2);
			}
			else
			{
				// báo lỗi
			}
			userdb.update(customer1); // update customer1 (to save the cart);
	
			// mua thêm quyển sách :D
			ProductQuantity pq3 = store.getByProductName("Harry Potter and BCM");
			if(pq3 == null)
			{
			}
			else
			{
				if(pq3.getQuantity() >= 1)
				{
					customer1.addProductToCart(pq3.getProduct(), 1);
				}
				else
				{
				}
			}
			userdb.update(customer1); // update customer1 (to save the cart);
	
			customer1.getCart().print();
			// store.print(); // quantity của các product chưa bị trừ
			// customer1.pay(store);
			// store.print(); // quantity đã bị trừ
		}
	}

	private static Staff generateStaff(String username, String password) throws Exception
	{
		Staff staff = new Staff(username, password);
		userdb.add(staff); // tự động lưu staff vào database
		return staff;
	}

	private static Customer generateCustomer(String username, String password) throws Exception
	{
		Customer customer = new Customer(username, password);
		userdb.add(customer); // tự động lưu customer vào database
		return customer;
	}

	private static void init() throws Exception
	{
		Staff staff1 = generateStaff("Pham Duc Duy", "Hoc Dot");
		Customer customer1 = generateCustomer("Bui Cong Minh", "Em Fan Anh 7");
		Customer customer2 = generateCustomer("Binh Cong Mui", "Em Fan Anh 10");
		
		Book book1 = new Book("Harry Potter and BCM", 1.0d, "The Adventure of Harry Potter and BCM", "BCM", 5);
		Book book2 = new Book("Harry Potter and PDD", 100.0d, "The Adventure of Harry Potter and PDD", "PDD", 200);
		
		staff1.addProductToStore(store, book1, 10); // thêm 10 cuốn sách book1 vào store
		staff1.addProductToStore(store, book2, 20); // thêm 20 cuốn sách book2 vào store
		
		Toy toy1 = new Toy("Tai nghe 100 cu", 100000000d, "Tai nghe nghe sieu hay", "Handmade");
		Toy toy2 = new Toy("Laptop 1 ty 7", 1700000000d, "Laptop sieu khoe", "Hang tang khong ban");
		Toy toy3 = new Toy("But bi", 2.0d, "but bi cho hoc sinh", "Thien Long");
		
		staff1.addProductToStore(store, toy1, 2); // thêm 2 toy1 vào store
		staff1.addProductToStore(store, toy2); // thêm 1 toy2 vào store
		staff1.addProductToStore(store, toy3, 100); // thêm 100 toy3 vào store
	}
}
