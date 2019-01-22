package windows;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Shed;
import objects.Hardware;

public class UseShed extends Stage {

    private Shed shed;

    public UseShed(Shed shed) {

        this.shed = shed;

        setTitle("Usar Galpão");

        GridPane root = new GridPane();

        int height = 50 + (shed.getHardwares().size() * 50);

        Scene scene = new Scene(root, 350, height);

        root.setVgap(15);
        root.setHgap(15);
        root.setPadding(new Insets(15));

        Button removeAll = new Button("Remover Tudo");
        removeAll.setOnAction((e) -> {
            shed.removeAll();
        });

        root.addRow(0, new Label("Hardware"), new Label("Quantidade"), removeAll);

        for (int numRows = 1; numRows <= shed.getHardwares().size(); numRows++){
            Hardware hardware = shed.getHardwares().get(numRows - 1);
            Integer quantity = shed.getQuantity().get(numRows - 1);
            root.addRow(numRows, new Label(hardware.getId()), new Label(quantity.toString()), createButton("Remover", hardware));
        }

        setScene(scene);
    }

    private Node createButton(String text, Hardware hardware){
        Button button = new Button(text);

        button.setOnAction((e) -> {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType btnYes = new ButtonType("Sim");
            ButtonType btnNo = new ButtonType("Não");

            dialog.setTitle("Remover");
            dialog.setHeaderText("Você está prestes a remove " + hardware.getId() + " do galpão " + shed.getId() + ".");
            dialog.setContentText("Você deseja continuar?");
            dialog.getButtonTypes().addAll(btnYes, btnNo);
            dialog.showAndWait().ifPresent(b -> {
                if (b == btnYes){
                    shed.removeHardware(shed.getHardwares().indexOf(hardware));
                }
            });
        });

        return button;
    }
}
