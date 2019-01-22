package chico3434.billjobs.windows;

import chico3434.billjobs.exceptions.ExistingProduct;
import chico3434.billjobs.exceptions.MissingFunds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import chico3434.billjobs.objects.Product;
import chico3434.billjobs.objects.Software;
import chico3434.billjobs.utils.Game;

public class CreateOrEditProduct extends Stage {

    private VBox vBox;

    private Label lblName, lblVersion, lblDescription, lblCost, lblPrice, lblType, lblSoft, lblHard;

    private TextField inName;

    private TextField inVersion;

    private TextArea inDescription;

    private TextField inCost;

    private TextField inPrice;

    private ToggleGroup types;

    private RadioButton radioSoft;

    private RadioButton radioHard;

    private Button btnAction;

    private Product product;

    private boolean remove;

    CreateOrEditProduct(Product product) {
        setTitle("Criar Produto");
        this.product = product;
        build();
        if (this.product != null){
            setTitle("Atualizar Product");
            fill();
        }

        btnAction.setOnAction(actionEvent -> btnClicked());

        Scene scene = new Scene(vBox, 400, 600);

        setScene(scene);
    }

    private void build(){

        vBox = new VBox();

        lblName = new Label("Nome");

        lblVersion = new Label("Versão");

        lblDescription = new Label("Descrição");

        lblCost = new Label("Custo");

        lblPrice = new Label("Preço");

        lblType = new Label("Tipo");

        lblSoft = new Label("Software");

        lblHard = new Label("Hardware");

        inName = new TextField();

        inVersion = new TextField();

        inDescription = new TextArea();

        inCost = new TextField("0");

        inPrice = new TextField("0");

        types = new ToggleGroup();

        radioSoft = new RadioButton();

        radioHard = new RadioButton();

        radioSoft.setToggleGroup(types);

        radioHard.setToggleGroup(types);

        btnAction = new Button("Criar");

        HBox soft = new HBox();

        HBox hard = new HBox();

        soft.getChildren().addAll(radioSoft, lblSoft);
        soft.setAlignment(Pos.CENTER);

        hard.getChildren().addAll(radioHard, lblHard);
        hard.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(lblName, inName, lblVersion, inVersion, lblDescription, inDescription, lblCost, inCost, lblPrice, inPrice, lblType, soft, hard, btnAction);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
    }

    private void fill(){
        inName.setText(product.getName());

        inVersion.setText(product.getVersion());

        inDescription.setText(product.getDescription());

        inPrice.setText(new Double(product.getPrice()).toString());

        if (product instanceof Software){
            inCost.setText("0");

            radioSoft.fire();

            radioHard.setDisable(true);
        } else {
            inCost.setText(new Double(product.getCost()).toString());

            radioHard.fire();

            radioSoft.setDisable(true);
        }

        btnAction.setText("Atualizar");
    }

    private void createProduct() {
        if (radioSoft.isSelected()) {
            try {
                if (Double.parseDouble(inCost.getText()) >= 0) {
                    Game.createSoftware(inName.getText(), inVersion.getText(), inDescription.getText(), Double.parseDouble(inCost.getText()), Double.parseDouble(inPrice.getText()));
                    this.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "O custo não pode ser negativo :(").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Erro:\n" + e.getMessage()).show();
            }
        } else if (radioHard.isSelected()) {
            try {
                if (Double.parseDouble(inCost.getText()) > 4) {
                    Game.createHardware(inName.getText(), inVersion.getText(), inDescription.getText(), Double.parseDouble(inCost.getText()), Double.parseDouble(inPrice.getText()));
                    this.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "O custo de um Hardware deve ser superior a 4.").show();
                }
            } catch (ExistingProduct p) {
                new Alert(Alert.AlertType.ERROR, "Erro:\n" + p.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "É necessário selecionar o tipo de product.").show();
        }
    }

    private void updateProduct(){
        try{
            Game.updateProduct(product, inDescription.getText(), Double.parseDouble(inCost.getText()), Double.parseDouble(inPrice.getText()));
            this.close();
        } catch (MissingFunds missingFunds) {
            new Alert(Alert.AlertType.ERROR, missingFunds.getMessage()).show();
        }
    }

    private void btnClicked(){

        String erros = "";

        if (inName.getText().isEmpty()){
            erros += "Nome não pode estar vazio\n";
        }
        if (inVersion.getText().isEmpty()){
            erros += "Versão não pode estar vazia\n";
        }
        if (inDescription.getText().isEmpty()){
            erros += "Descrição não pode estar vazia\n";
        }
        if (inCost.getText().isEmpty()){
            erros += "Custo não pode estar vazio, mas pode ser 0 (caso seja software).\n";
        }
        if (inPrice.getText().isEmpty()){
           erros += "Preco não pode estar vazio.\n";;
        }
        if (Double.parseDouble(inPrice.getText()) == 0.0){
           erros += "Eu acho que você não vai querer dar seu product de graça.\n";
        }

        if (erros.length() > 0){
            new Alert(Alert.AlertType.ERROR, erros).show();
        } else {
            if (product != null){
                if (product.getId().equals(inName.getText() + inVersion.getText())){
                    updateProduct();
                } else {
                    if (product.getName().equals(product.getName())){
                        if (product instanceof Software){
                            updateSoftware();
                        } else {
                            createProduct();
                        }
                    } else {
                        createProduct();
                    }
                }
            } else {
                createProduct();
            }
        }
    }

    private void updateSoftware() {
        try {
            Game.updateSoftware(product, inVersion.getText(), inDescription.getText(), Double.parseDouble(inCost.getText()), Double.parseDouble(inPrice.getText()));
            this.close();
        } catch (MissingFunds missingFunds) {
            new Alert(Alert.AlertType.ERROR, missingFunds.getMessage()).show();
        }
    }

}
