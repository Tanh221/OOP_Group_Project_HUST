package model.db;

import model.Book;
import model.Product;
import model.Stationary;
import model.Toy;

import java.util.ArrayList;

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

        System.out.println("Book: " + book.getName());
        System.out.println("Toy: " + toy.getName());
        System.out.println("Stationary: " + stationary.getName());
    }
}
