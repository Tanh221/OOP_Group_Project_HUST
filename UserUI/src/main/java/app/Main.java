package app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Databases.UserDB;
import model.Products.Book;
import model.Products.Toy;
import model.Store.Store;
import model.Users.Admin;
import model.Users.Customer;
import model.Users.Staff;


import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static final Boolean FIRST_RUN = false;

    private static UserDB userdb = new UserDB();
    private static Store store = new Store();

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, Exception {
        if (FIRST_RUN) {
            initial();
        } else {
//            store.addProduct(new Book("Abc",12.4,"123","A",1),10);
            launch(args);
        }
    }
    @Override
    public void start(Stage primiaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/Login.fxml"));

            Parent root = fxmlLoader.load();

            primiaryStage.setTitle("HUST Book Store");
            Image image  = new Image(getClass().getResourceAsStream("/app/image/HUST.png"));
            primiaryStage.getIcons().add(image);
            primiaryStage.setScene(new Scene(root,1080,750));

            primiaryStage.show();

            primiaryStage.setOnCloseRequest(event -> {
                event.consume();
                logout(primiaryStage);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You about to logout");
        alert.setContentText("Do you want to save before exiting?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("You have logged out");
            stage.close();
        }

    }
    private static Staff generateStaff(String username, String password, String phone, LocalDate dateJoined, float salary) throws Exception
    {
        Staff staff = new Staff(username, password,phone,dateJoined, salary);
        return staff;
    }

    private static Customer generateCustomer(String username, String password) throws Exception
    {
        Customer customer = new Customer(username, password);
        return customer;
    }

    public static void initial() throws Exception
    {
        Customer customer1 = generateCustomer("Bui Cong Minh", "Em Fan Anh 7");
        Customer customer2 = generateCustomer("Binh Cong Mui", "Em Fan Anh 10");
        Customer c3 = generateCustomer("0", "0");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateString = "20/01/2024";
        LocalDate date = LocalDate.parse(dateString, formatter);

        Staff staff1 = generateStaff("Phạm Đức Duy","123","0987654321", date,1000);
        Staff staff2 = generateStaff("Mai Văn Nhât Minh","123","0987654321",date,2000);
        Staff staff3 = generateStaff("Tạ Quốc Hùng","123","0987654321",date,3000);
        Staff staff4 = generateStaff("Đặng Trung Anh","123","0987654321",date,4000);
        Staff staff5 = generateStaff("Bùi Công Minh","123","0987654321",date,5000);
        Staff staff6 = generateStaff("Nguyễn Thái Anh Minh","123","0987654321",date,200);
        Admin test = new Admin("1","1","0",date,0);


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

        Toy toy4 = new Toy("Rong trang mat xanh", 100d, "Sieu khoe", "Yugioh");
        staff2.addProductToStore(toy4, 1, 10);
    }



}



