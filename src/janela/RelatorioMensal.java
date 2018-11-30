package janela;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class RelatorioMensal extends Stage {

    public RelatorioMensal(String relatorio) {
        setTitle("Relatório Mensal");

        ScrollPane root = new ScrollPane();
        root.setPadding(new Insets(10));


        Scene cena = new Scene(root, 400, 500);

        root.setContent(new Label(relatorio));

        setScene(cena);
    }
}
