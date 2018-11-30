package janela;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objeto.Fabrica;
import utils.Jogo;

public class UsarFabrica extends Stage {

    public UsarFabrica(Fabrica fabrica) {

        setTitle("Usar Fábrica");

        GridPane root = new GridPane();

        Scene cena = new Scene(root, 300, 50);

        root.setVgap(15);
        root.setHgap(15);
        root.setPadding(new Insets(15));

        TextField inHard = new TextField();
        inHard.setPromptText("ID do Hardware");

        if (fabrica.getProduto() != null){
            inHard.setText(fabrica.getProduto().getId());
        }

        Button button = new Button("Definir");

        root.addRow(0, inHard, button);

        button.setOnAction((e) -> {

            try {
                Jogo.alocarHardwareEmFabrica(fabrica, inHard.getText());
                this.close();
            } catch (Exception ex){
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }

        });

        setScene(cena);
    }
}
