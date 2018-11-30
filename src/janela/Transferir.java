package janela;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objeto.Empresa;
import objeto.Empresario;

import java.text.NumberFormat;

public class Transferir extends Stage {
    public Transferir(Empresario empresario, Empresa empresa) {

        setTitle("Tranferência");

        Slider s = new Slider(0, empresario.getDinheiro(), 0);
        if (empresario.getDinheiro() == 0){
            s.setDisable(true);
        }

        TextField tf = new TextField();
        tf.setDisable(true);
        tf.textProperty().bindBidirectional(s.valueProperty(), NumberFormat.getNumberInstance());


        Slider s1 = new Slider(0, empresa.getVerba(), 0);
        if (empresa.getVerba() == 0){
            s1.setDisable(true);
        }

        TextField tf1 = new TextField();
        tf1.setDisable(true);
        tf1.textProperty().bindBidirectional(s1.valueProperty(), NumberFormat.getNumberInstance());

        Button confirma = new Button("Confirma");

        confirma.setOnAction(e -> {
            empresario.setDinheiro(empresario.getDinheiro() + s1.getValue() - s.getValue());
            empresa.setVerba(empresa.getVerba() + s.getValue() - s1.getValue());
            this.close();
        });

        VBox root = new VBox(5);

        Scene cena = new Scene(root, 300, 200);

        root.alignmentProperty().setValue(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(s, tf, s1, tf1, confirma);

        setScene(cena);

    }
}
