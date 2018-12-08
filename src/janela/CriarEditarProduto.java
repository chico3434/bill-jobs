package janela;

import exceptions.ProdutoExistente;
import exceptions.VerbaInsuficiente;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objeto.Produto;
import objeto.Software;
import utils.Jogo;

public class CriarEditarProduto extends Stage {

    private VBox caixa;

    private Label lblNome, lblVersao, lblDescricao, lblCusto, lblPreco, lblTipo, lblSoft, lblHard;

    private TextField inNome;

    private TextField inVersao;

    private TextArea inDescricao;

    private TextField inCusto;

    private TextField inPreco;

    private ToggleGroup tipos;

    private RadioButton radioSoft;

    private RadioButton radioHard;

    private Button btnAcao;

    private Produto produto;

    private boolean remover;

    CriarEditarProduto(Produto produto) {
        setTitle("Criar Produto");
        this.produto = produto;
        constroi();
        if (this.produto != null){
            setTitle("Atualizar Produto");
            preenche();
        }

        btnAcao.setOnAction(actionEvent -> btnClicked());

        Scene cena = new Scene(caixa, 400, 600);

        setScene(cena);
    }

    private void constroi(){

        caixa = new VBox();

        lblNome = new Label("Nome");

        lblVersao = new Label("Versão");

        lblDescricao = new Label("Descrição");

        lblCusto = new Label("Custo");

        lblPreco = new Label("Preço");

        lblTipo = new Label("Tipo");

        lblSoft = new Label("Software");

        lblHard = new Label("Hardware");

        inNome = new TextField();

        inVersao = new TextField();

        inDescricao = new TextArea();

        inCusto = new TextField("0");

        inPreco = new TextField("0");

        tipos = new ToggleGroup();

        radioSoft = new RadioButton();

        radioHard = new RadioButton();

        radioSoft.setToggleGroup(tipos);

        radioHard.setToggleGroup(tipos);

        btnAcao = new Button("Criar");

        HBox soft = new HBox();

        HBox hard = new HBox();

        soft.getChildren().addAll(radioSoft, lblSoft);
        soft.setAlignment(Pos.CENTER);

        hard.getChildren().addAll(radioHard, lblHard);
        hard.setAlignment(Pos.CENTER);

        caixa.getChildren().addAll(lblNome, inNome, lblVersao, inVersao, lblDescricao, inDescricao, lblCusto, inCusto, lblPreco, inPreco, lblTipo, soft, hard, btnAcao);
        caixa.setAlignment(Pos.CENTER);
        caixa.setSpacing(10);
        caixa.setPadding(new Insets(10));
    }

    private void preenche(){
        inNome.setText(produto.getNome());

        inVersao.setText(produto.getVersao());

        inDescricao.setText(produto.getDescricao());

        inPreco.setText(new Double(produto.getPreco()).toString());

        if (produto instanceof Software){
            inCusto.setText("0");

            radioSoft.fire();

            radioHard.setDisable(true);
        } else {
            inCusto.setText(new Double(produto.getCusto()).toString());

            radioHard.fire();

            radioSoft.setDisable(true);
        }

        btnAcao.setText("Atualizar");
    }

    private void criarProduto() {
        if (radioSoft.isSelected()) {
            try {
                if (Double.parseDouble(inCusto.getText()) >= 0) {
                    Jogo.criarSoftware(inNome.getText(), inVersao.getText(), inDescricao.getText(), Double.parseDouble(inCusto.getText()), Double.parseDouble(inPreco.getText()));
                    this.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "O custo não pode ser negativo :(").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Erro:\n" + e.getMessage()).show();
            }
        } else if (radioHard.isSelected()) {
            try {
                if (Double.parseDouble(inCusto.getText()) > 4) {
                    Jogo.criarHardware(inNome.getText(), inVersao.getText(), inDescricao.getText(), Double.parseDouble(inCusto.getText()), Double.parseDouble(inPreco.getText()));
                    this.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "O custo de um Hardware deve ser superior a 4.").show();
                }
            } catch (ProdutoExistente p) {
                new Alert(Alert.AlertType.ERROR, "Erro:\n" + p.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "É necessário selecionar o tipo de produto.").show();
        }
    }

    private void atualizarProduto(){
        try{
            Jogo.atualizarProduto(produto, inDescricao.getText(), Double.parseDouble(inCusto.getText()), Double.parseDouble(inPreco.getText()));
            this.close();
        } catch (VerbaInsuficiente verbaInsuficiente) {
            new Alert(Alert.AlertType.ERROR, verbaInsuficiente.getMessage()).show();
        }
    }

    private void btnClicked(){

        String erros = "";

        if (inNome.getText().isEmpty()){
            erros += "Nome não pode estar vazio\n";
        }
        if (inVersao.getText().isEmpty()){
            erros += "Versão não pode estar vazia\n";
        }
        if (inDescricao.getText().isEmpty()){
            erros += "Descrição não pode estar vazia\n";
        }
        if (inCusto.getText().isEmpty()){
            erros += "Custo não pode estar vazio, mas pode ser 0 (caso seja software).\n";
        }
        if (inPreco.getText().isEmpty()){
           erros += "Preco não pode estar vazio.\n";;
        }
        if (Double.parseDouble(inPreco.getText()) == 0.0){
           erros += "Eu acho que você não vai querer dar seu produto de graça.\n";
        }

        if (erros.length() > 0){
            new Alert(Alert.AlertType.ERROR, erros).show();
        } else {
            if (produto != null){
                if (produto.getId().equals(inNome.getText() + inVersao.getText())){
                    atualizarProduto();
                } else {
                    if (produto.getNome().equals(produto.getNome())){
                        if (produto instanceof Software){
                            atualizarSoftware();
                        } else {
                            criarProduto();
                        }
                    } else {
                        criarProduto();
                    }
                }
            } else {
                criarProduto();
            }
        }
    }

    private void atualizarSoftware() {
        try {
            Jogo.atualizarSoftware(produto, inVersao.getText(), inDescricao.getText(), Double.parseDouble(inCusto.getText()), Double.parseDouble(inPreco.getText()));
            this.close();
        } catch (VerbaInsuficiente verbaInsuficiente) {
            new Alert(Alert.AlertType.ERROR, verbaInsuficiente.getMessage()).show();
        }
    }

}
