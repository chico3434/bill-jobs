package chico3434.billjobs.windows;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import chico3434.billjobs.objects.Server;
import chico3434.billjobs.objects.Software;
import chico3434.billjobs.utils.Game;

import java.util.Iterator;
import java.util.List;

class UseServer extends Stage {

    private Server server;

    UseServer(Server server){

        this.server = server;

        int height = 40 + (server.getCapacity() * 40);

        setTitle("Usar Server");

        GridPane root = new GridPane();

        Scene scene = new Scene(root, 220, height);

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10));

        root.addRow(0, new Label("Server"), createButton("Add em Todos", null));

        int rows = 1;

        List<Software> softwareList = server.getSoftwareList();

        for (Software software : softwareList) {
            root.addRow(rows, new Label(software.getId()), createButton("Trocar", software));
            rows++;
        }

        while(rows <= server.getCapacity()){
            root.addRow(rows, new Label("Vazio"), createButton("Add", null));
            rows++;
        }

        setScene(scene);

    }

    private Node createButton(String action, Software software) {
        Button btn = new Button(action);

        btn.setOnAction((e)->{
            if (action.equals("Add")){
                TextInputDialog tid = new TextInputDialog();
                tid.setTitle("Software a ser hospedado");
                tid.setContentText("Digite o id do Software que você deseja\nhospedar neste server: ");
                tid.showAndWait();

                String soft = tid.getEditor().getText();

                try {
                    Game.hostSoftware(server, soft);
                    this.close();
                } catch (Exception ex){
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }

            } else if (action.equals("Add em Todos")) {
                TextInputDialog tid = new TextInputDialog();
                tid.setTitle("Software a ser hospedado");
                tid.setContentText("Digite o id do Software que você deseja\nhospedar em todos os slots deste server: ");
                tid.showAndWait();

                String soft = tid.getEditor().getText();

                try {
                    Game.hostSoftwareInAll(server, soft);
                    this.close();
                } catch (Exception ex){
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }
            } else {
                TextInputDialog tid = new TextInputDialog();
                tid.setTitle("Software a ser hospedado");
                tid.setContentText("Digite o id do Software que você deseja\nhospedar no lugar de " + software.getName() + ": ");
                tid.showAndWait();

                String soft = tid.getEditor().getText();

                try {
                    Game.exchangeSoftware(server, soft, software);
                    this.close();
                } catch (Exception ex){
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }
            }
        });

        return btn;
    }

}
