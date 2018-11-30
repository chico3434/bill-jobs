package janela;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objeto.Galpao;
import objeto.Hardware;

public class UsarGalpao extends Stage {

    private Galpao galpao;

    public UsarGalpao(Galpao galpao) {

        this.galpao = galpao;

        setTitle("Usar Galpão");

        GridPane root = new GridPane();

        int height = 50 + (galpao.getHardwares().size() * 50);

        Scene cena = new Scene(root, 350, height);

        root.setVgap(15);
        root.setHgap(15);
        root.setPadding(new Insets(15));

        Button removerTudo = new Button("Remover Tudo");
        removerTudo.setOnAction((e) -> {
            galpao.removerTudo();
        });

        root.addRow(0, new Label("Hardware"), new Label("Quantidade"), removerTudo);

        for (int numRows = 1; numRows <= galpao.getHardwares().size(); numRows++){
            Hardware hardware = galpao.getHardwares().get(numRows - 1);
            Integer quantidade = galpao.getQuantidade().get(numRows - 1);
            root.addRow(numRows, new Label(hardware.getId()), new Label(quantidade.toString()), criarButton("Remover", hardware));
        }

        setScene(cena);
    }

    private Node criarButton(String texto, Hardware hardware){
        Button button = new Button(texto);

        button.setOnAction((e) -> {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não");

            dialog.setTitle("Remover");
            dialog.setHeaderText("Você está prestes a remover " + hardware.getId() + " do galpão " + galpao.getId() + ".");
            dialog.setContentText("Você deseja continuar?");
            dialog.getButtonTypes().addAll(btnSim, btnNao);
            dialog.showAndWait().ifPresent(b -> {
                if (b == btnSim){
                    galpao.removerHardware(galpao.getHardwares().indexOf(hardware));
                }
            });
        });

        return button;
    }
}
