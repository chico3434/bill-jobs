package janela;

import exceptions.DinheiroInsuficiente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import utils.Jogo;

import java.text.NumberFormat;

public class CriarEmpresa {

    @FXML
    private TextField nomeEmpresa;

    @FXML
    private Slider verbaPassada;

    @FXML
    private TextField tfVerba;

    public void initialize(){
        verbaPassada.setMin(0);
        verbaPassada.setMax(Jogo.getEmpresario().getDinheiro());
        verbaPassada.setValue(Jogo.getEmpresario().getDinheiro()/2);

        tfVerba.setDisable(true);
        tfVerba.textProperty().bindBidirectional(verbaPassada.valueProperty(), NumberFormat.getNumberInstance());
    }

    public void criarEmpresa(){
        try {
            Jogo.criarEmpresa(nomeEmpresa.getText(), verbaPassada.getValue());
            Main.criarEmpresa.close();
        } catch (DinheiroInsuficiente e){
            Alert a = new Alert(Alert.AlertType.WARNING, "Dinheiro Insuficiente");
            a.show();
        }
    }

}
