package app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        launch(args);
//        List<employee> store = new ArrayList<>();
//        employee A = new employee(1,"Nhật Minh","","9/1/2024",90500);
//        employee B = new employee(2,"Công Minh","",23/1/2024",20090);
//        employee C = new employee(3,"Quốc Hùng","","13/1/2024",30600);
//        employee D = new employee(4,"Đức Duy","","1/1/2024",40002);
//        employee E = new employee(5,"Trung Anh","","20/1/2024",6000);
//        employee F = new employee(6,"Anh Minh","","6/1/2024",8000);
//
//        try{
//            FileOutputStream fos = new FileOutputStream("C:\\Users\\Long\\Desktop\\Local\\Studying\\SOICT\\OOP\\Project\\HUST\\src\\main\\java\\app\\data\\employee.data");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(A);
//            oos.writeObject(B);
//            oos.writeObject(C);
//            oos.writeObject(D);
//            oos.writeObject(E);
//            oos.writeObject(F);
//            oos.close();
//            fos.close();
//
//        }catch (Exception k){ }


//
//        employee e = null;

//        try {
//            FileInputStream fis = new FileInputStream("C:\\Users\\Long\\Desktop\\Local\\Studying\\SOICT\\OOP\\Project\\HUST\\src\\main\\java\\app\\data\\employee.data");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            while (true) {
//                e = (employee) ois.readObject();
//                store.add(e);
//            }
//
//        } catch (Exception tmp) {
//            System.out.println(tmp);
//        }
//        for (employee i : store) {
//            System.out.println(i);
//        }

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
}

