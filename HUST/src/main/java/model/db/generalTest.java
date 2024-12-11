package model.db;

import model.Book;
import model.Product;
import model.Stationary;
import model.Toy;

import java.util.ArrayList;
import java.util.Scanner;

public class generalTest {
    public static void main(String[] args) {
        Book book = new Book(
                "The Great Gatsby", "Fiction", 10.99, 100, "A classic novel.", true, "Scribner", 0.5,
                "Paperback", "1-year", "F. Scott Fitzgerald", "Scribner", "1925", "English", "Novel", "Paperback", "Classic", 180
        );

        Toy toy = new Toy(
                "Lego Set", "Toys", 29.99, 50, "Building block set.", true, "Lego", 1.2,
                "Colorful blocks", "2-year", 6, "10x10x10 cm", "Lego", "Plastic", "Non-toxic"
        );

        Stationary stationary = new Stationary(
                "Notebook", "Stationary", 3.99, 200, "Ruled notebook for students.", true, "Staples", 0.3,
                "Eco-friendly", "1-year", "Notebook", "Paper", 5, "High School", "Office Supplies"
        );

//        System.out.println("Book: " + book.getName());
//        System.out.println("Toy: " + toy.getName());
//        System.out.println("Stationary: " + stationary.getName());

        DBManager database = new DBManager("bookstore_data.ser");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Bookstore Menu ---");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Save and Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    database.displayProducts();
                    break;
                case 2:
                    System.out.println("Choose product type: 1. Book, 2. Toy, 3. Stationary");
                    int type = scanner.nextInt();
//                    System.out.print("Enter product ID: ");
//                    int id = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline
//                    System.out.print("Enter name: ");
//                    String name = scanner.nextLine();
//                    System.out.print("Enter price: ");
//                    double price = scanner.nextDouble();
//                    System.out.print("Enter quantity: ");
//                    int quantity = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline

                    if (type == 1) {
//                        System.out.print("Enter author: ");
//                        String author = scanner.nextLine();
//                        System.out.print("Enter publisher: ");
//                        String publisher = scanner.nextLine();
//                        database.addProduct(new Book(id, name, price, quantity, author, publisher));
                        database.addProduct(book);
                    } else if (type == 2) {
//                        System.out.print("Enter toy type: ");
//                        String toyType = scanner.nextLine();
//                        database.addProduct(new Toy(id, name, price, quantity, toyType));
                        database.addProduct(toy);
                    } else if (type == 3) {
//                        System.out.print("Enter material: ");
//                        String material = scanner.nextLine();
//                        database.addProduct(new Stationary(id, name, price, quantity, material));
                        database.addProduct(stationary);
                    }
                    break;
                case 3:
                    database.saveData();
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
