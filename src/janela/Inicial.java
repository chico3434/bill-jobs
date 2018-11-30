package janela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Jogo;

import java.io.FileNotFoundException;


public class Inicial {

    @FXML
    private TextField inNome;

    @FXML
    private TextField inEmpresarioNome;

    private void carregarTela(){
        Main.inicial.close();
        try {
            Main.tela = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/tela.fxml"));
            Main.tela.setTitle("Jogo");
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(Inicial.class.getResource("css/estilo.css").toExternalForm());
            Main.tela.setScene(scene);
            Main.tela.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void criarClicado(){
        Jogo.criarEmpresario(inNome.getText());
        carregarTela();
    }

    public void entrarClicado(ActionEvent actionEvent) {
        try {
            Jogo.carregarEmpresario(inEmpresarioNome.getText());
            carregarTela();
        } catch (FileNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"O empresário digitado não foi encontrado!\nCrie um").show();
        }
    }
}
