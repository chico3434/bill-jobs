package janela;

import exceptions.ProdutoExistente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.Jogo;

public class CriarProduto {

    @FXML
    private TextField inNome;

    @FXML
    private TextField inVersao;

    @FXML
    private TextArea inDescricao;

    @FXML
    private TextField inCusto;

    @FXML
    private TextField inPreco;

    @FXML
    private ToggleGroup tipos;

    @FXML
    private RadioButton rSoft;

    @FXML
    private RadioButton rHard;



    public void criarProduto(ActionEvent actionEvent) {
        if (inNome.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Nome não pode estar vazio").show();
            return;
        }
        if (inVersao.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Versão não pode estar vazia").show();
            return;
        }
        if (inDescricao.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Descrição não pode estar vazia").show();
            return;
        }
        if (inCusto.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Custo não pode estar vazio, mas pode ser 0 \n(caso seja software).").show();
            return;
        }
        if (inPreco.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Preco não pode estar vazio.").show();
            return;
        }
        if (Double.parseDouble(inPreco.getText()) == 0.0){
            new Alert(Alert.AlertType.WARNING, "Eu acho que você não vai querer dar seu produto \nde graça.").show();
            return;
        }
        if (rSoft.isSelected()){
            try {
                if (Double.parseDouble(inCusto.getText()) >= 0) {
                    Jogo.criarSoftware(inNome.getText(), inVersao.getText(), inDescricao.getText(), Double.parseDouble(inCusto.getText()), Double.parseDouble(inPreco.getText()));
                    Main.criarProduto.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "O custo não pode ser negativo :(").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Erro:\n" + e.getMessage()).show();
            }
        } else if (rHard.isSelected()){
            try {
                if (Double.parseDouble(inCusto.getText()) > 4) {
                    Jogo.criarHardware(inNome.getText(), inVersao.getText(), inDescricao.getText(), Double.parseDouble(inCusto.getText()), Double.parseDouble(inPreco.getText()));
                    Main.criarProduto.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "O custo de um Hardware deve ser superior a 4.").show();
                }
            } catch(ProdutoExistente p){
                new Alert(Alert.AlertType.ERROR, "Erro:\n" + p.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "É necessário selecionar o tipo de produto.").show();
        }
    }

    public void initialize() {
        tipos = new ToggleGroup();
        rSoft.setToggleGroup(tipos);
        rHard.setToggleGroup(tipos);
        inCusto.setText("0");
        inPreco.setText("0");
    }
}
