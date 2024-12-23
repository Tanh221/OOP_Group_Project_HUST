package Main;

import java.io.*;

import Cart.Cart;
import Store.Store;
import Databases.UserDB;

import java.util.ArrayList;

import Products.Product;
import Products.Book;
import Products.Toy;
import Products.ProductInfo;
import Users.Customer;
import Users.Staff;
import Users.Admin;
import Users.User;

import Order.Order;

public class Main {
	private static final Boolean FIRST_RUN = false;

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
			ProductInfo pq1 = store.getByProductName("But bi");
			if(pq1 == null) // nếu ko tồn tại sản phẩm
			{
				// làm gì đó ở đây :D
			}
			else // có tồn tại sản phầm
			{
				if(pq1.getQuantity() >= 40) // nếu trong store có đủ tối thiểu 1 cái bút bi
				{
					// add 1 bút bi vào giỏ hàng
					customer1.addProductToCart(pq1.getProduct(), 40);
				}
				else
				{
					// báo lỗi
				}
			}
			// userdb.update(customer1); // ko cần dòng này nữa vì customer1 nó tự lưu r
	
			// giả sử thằng customer1 lướt store để mua 3 cái tai nghe 100 củ
			ProductInfo pq2 = store.getByProductName("Tai nghe 100 cu");
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
	
			// customer1 cay quá đành chỉ mua 2 tai nghe
			if(pq2.getQuantity() >= 2)
			{
				// chạy được vì store có 2 tai nghe
				customer1.addProductToCart(pq2.getProduct(), 2);
			}
			else
			{
				// báo lỗi
			}
			// userdb.update(customer1); // ko cần dòng này nữa vì customer1 nó tự lưu r
	
			// mua thêm quyển sách :D
			ProductInfo pq3 = store.getByProductName("Harry Potter and BCM");
			if(pq3 == null)
			{
			}
			else
			{
				if(pq3.getQuantity() >= 1)
				{
					customer1.addProductToCart(pq3.getProduct());
				}
				else
				{
				}
			}
			// userdb.update(customer1); // ko cần dòng này nữa vì customer1 nó tự lưu r

			// test remove from cart
			ArrayList<ProductInfo> itemsInCart = customer1.getCart().getItemsInCart();
			for(ProductInfo pq : itemsInCart)
			{
				if(pq.getQuantity() > 3)
				{
					customer1.removeProductFromCart(pq.getProduct(), 3);
				}
			}
			// userdb.update(customer1); // ko cần dòng này nữa vì customer1 nó tự lưu r
			customer1.getCart().print(); // in ra cart của người dùng
			staff1.updateAuthorOfBook((Book)pq3.getProduct(), "xyz"); // cập nhật tác giả của quyển sách ["Harry Potter and BCM"] thành xyz
			customer1.getCart().print(); // tên quyển sách trong cart cũng sync theo
			store.print(); // quantity của các product chưa bị trừ
			Order o = customer1.pay(); // pay (tự tạo order và lưu vào orderdb)
			store.print(); // quantity đã bị trừ
			staff1.updateAuthorOfBook((Book)pq3.getProduct(), "abc"); // cập nhật tác giả của quyển sách ["Harry Potter and BCM"] thành abc
			staff1.getAllOrdersOfUser(customer1); // order giữ nguyên thông tin
			customer1.getAllMyOrders(); // user cũng tự xem đc các order của mình
			customer1.getCart().print(); // tên quyển sách trong cart cũng sync theo
			System.out.println(o.getDetails()); // order cũng ko thay đổi thông tin
			staff1.printAllMyReceiveNotes();
			staff1.printAllReceiveNotesOfProduct(pq3.getProduct()); // in tất cả đơn nhập quyển sách ["Harry Potter and BCM"]
			
			User user3 = userdb.getByUsernameAndPassword("Mai Van Nhat Minh", "Hoc Gioi");
			Staff staff3 = (Staff) user3;
			
			staff1.printUserReceiveNoteByProduct(staff3, pq3.getProduct()); // in tất cả đơn nhập quyển sách ["Harry Potter and BCM"] được nhập bởi staff 3
		}
	}

	private static Staff generateStaff(String username, String password) throws Exception
	{
		Staff staff = new Staff(username, password);
		return staff;
	}

	private static Customer generateCustomer(String username, String password) throws Exception
	{
		Customer customer = new Customer(username, password);
		return customer;
	}

	private static void init() throws Exception
	{
		Staff staff1 = generateStaff("Pham Duc Duy", "Hoc Dot");
		Customer customer1 = generateCustomer("Bui Cong Minh", "Em Fan Anh 7");
		Customer customer2 = generateCustomer("Binh Cong Mui", "Em Fan Anh 10");

		Book book1 = new Book("Harry Potter and BCM", 1.0d, "The Adventure of Harry Potter and BCM", "BCM", 5);
		Book book2 = new Book("Harry Potter and PDD", 100.0d, "The Adventure of Harry Potter and PDD", "PDD", 200);
		
		staff1.addProductToStore(book1, 10, 4.0); // thêm 10 cuốn sách book1 vào store với giá nhập là 4.0
		staff1.addProductToStore(book1, 20, 6.0); // tiếp tục thêm 20 cuốn sách book1 vào store với giá nhập là 6.0
		staff1.addProductToStore(book2, 20, 5.0); // thêm 20 cuốn sách book2 vào store với giá nhập là 5.0
		
		Toy toy1 = new Toy("Tai nghe 100 cu", 100000000d, "Tai nghe nghe sieu hay", "Handmade");
		Toy toy2 = new Toy("Laptop 1 ty 7", 1700000000d, "Laptop sieu khoe", "Hang tang khong ban");
		Toy toy3 = new Toy("But bi", 2.0d, "but bi cho hoc sinh", "Thien Long");
		
		staff1.addProductToStore(toy1, 2, 10.4); // thêm 2 toy1 vào store với giá nhập là 10.4
		staff1.addProductToStore(toy2, 1, 5.7); // thêm 1 toy2 vào store  với giá nhập là 5.7
		staff1.addProductToStore(toy3, 100, 1.2); // thêm 100 toy3 vào store với giá nhập là 1.2
		
		Staff staff2 = generateStaff("Mai Van Nhat Minh", "Hoc Gioi");
		Toy toy4 = new Toy("Rong trang mat xanh", 100d, "Sieu khoe", "Yugioh");
		staff2.addProductToStore(toy4, 1, 10);
	}
}
