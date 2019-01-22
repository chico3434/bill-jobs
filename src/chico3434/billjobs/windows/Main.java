package chico3434.billjobs.windows;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage initial;
    public static Stage screen;
    public static Stage createCompany;
    public static Stage buy;

    @Override
    public void start(Stage stage) throws Exception {

        initial = stage;

        Parent root = FXMLLoader.load(getClass().getResource("fxml/initial.fxml"));
        stage.setTitle("Game");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
