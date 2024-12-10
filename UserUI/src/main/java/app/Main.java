package app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        launch(args);

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

