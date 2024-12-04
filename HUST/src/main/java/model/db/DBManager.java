package model.db;

import model.Product;

import java.io.*;
import java.util.List;

class DBManager {
    public static void saveData(String fileName, List<Product> products) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(products);
        }
    }

    public static List<Product> loadData(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Product>) ois.readObject();
        }
    }
}
