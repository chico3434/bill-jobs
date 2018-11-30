package janela;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objeto.Servidor;
import objeto.Software;
import utils.Jogo;

import java.util.Iterator;
import java.util.List;

public class UsarServidor extends Stage {

    private Servidor servidor;

    public UsarServidor(Servidor servidor){

        this.servidor = servidor;

        int height = 40 + (servidor.getCapacidade() * 40);

        setTitle("Usar Servidor");

        GridPane root = new GridPane();

        Scene cena = new Scene(root, 220, height);

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10));

        root.addRow(0, new Label("Servidor"), criarBtn("Add em Todos", null));

        int rows = 1;

        List<Software> soft = servidor.getSoftwares();
        Iterator<Software> it = soft.iterator();

        while(it.hasNext()){
            Software software = it.next();
            root.addRow(rows, new Label(software.getId()), criarBtn("Trocar", software));
            rows++;
        }

        while(rows <= servidor.getCapacidade()){
            root.addRow(rows, new Label("Vazio"), criarBtn("Add", null));
            rows++;
        }

        setScene(cena);

    }

    private Node criarBtn(String acao, Software software){
        Button btn = new Button(acao);

        btn.setOnAction((e)->{
            if (acao.equals("Add")){
                TextInputDialog tid = new TextInputDialog();
                tid.setTitle("Software a ser hospedado");
                tid.setContentText("Digite o id do Software que você deseja\nhospedar neste servidor: ");
                tid.showAndWait();

                String soft = tid.getEditor().getText();

                try {
                    Jogo.hospedarSoftware(servidor, soft);
                    this.close();
                } catch (Exception ex){
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }

            } else if (acao.equals("Add em Todos")){
                TextInputDialog tid = new TextInputDialog();
                tid.setTitle("Software a ser hospedado");
                tid.setContentText("Digite o id do Software que você deseja\nhospedar em todos os slots deste servidor: ");
                tid.showAndWait();

                String soft = tid.getEditor().getText();

                try {
                    Jogo.hospedarSoftwareAll(servidor, soft);
                    this.close();
                } catch (Exception ex){
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }
            } else {
                TextInputDialog tid = new TextInputDialog();
                tid.setTitle("Software a ser hospedado");
                tid.setContentText("Digite o id do Software que você deseja\nhospedar no lugar de " + software.getNome() + ": ");
                tid.showAndWait();

                String soft = tid.getEditor().getText();

                try {
                    Jogo.trocarSoftware(servidor, soft, software);
                    this.close();
                } catch (Exception ex){
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }
            }
        });

        return btn;
    }

}
