package windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Factory;
import utils.Game;

public class UseFactory extends Stage {

    public UseFactory(Factory factory) {

        setTitle("Usar Fábrica");

        GridPane root = new GridPane();

        Scene scene = new Scene(root, 300, 50);

        root.setVgap(15);
        root.setHgap(15);
        root.setPadding(new Insets(15));

        TextField inHard = new TextField();
        inHard.setPromptText("ID do Hardware");

        if (factory.getProduct() != null){
            inHard.setText(factory.getProduct().getId());
        }

        Button button = new Button("Definir");

        root.addRow(0, inHard, button);

        button.setOnAction((e) -> {

            try {
                Game.allocateHardwareInFactory(factory, inHard.getText());
                this.close();
            } catch (Exception ex){
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }

        });

        setScene(scene);
    }
}
