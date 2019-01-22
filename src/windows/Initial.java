package windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Game;

import java.io.FileNotFoundException;


public class Initial {

    @FXML
    private TextField inName;

    @FXML
    private TextField inBusinessmanName;

    private void loadScreen(){
        Main.initial.close();
        try {
            Main.screen = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/screen.fxml"));
            Main.screen.setTitle("Game");
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(Initial.class.getResource("css/style.css").toExternalForm());
            Main.screen.setScene(scene);
            Main.screen.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createClicked(){
        Game.createBusinessman(inName.getText());
        loadScreen();
    }

    public void loginClicked(ActionEvent actionEvent) {
        try {
            Game.loadBusinessman(inBusinessmanName.getText());
            loadScreen();
        } catch (FileNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"O empresário digitado não foi encontrado!\nCrie um").show();
        }
    }
}
