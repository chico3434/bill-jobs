package janela;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import objeto.Infraestrutura;
import objeto.Produto;

import java.util.Objects;

public class CelulaTabela extends HBox {

    private String idComp;

    private Label label;

    private Button button1, button2;

    public CelulaTabela(String id, Label lbl, Button btn1, Button btn2){
        this.idComp = id;
        label = lbl;
        button1 = btn1;
        button2 = btn2;

        this.setPadding(new Insets(10));

        this.getChildren().addAll(label, button1, button2);
    }

    public void remover(){
        this.setVisible(false);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof CelulaTabela) {
            CelulaTabela that = (CelulaTabela) o;
            return Objects.equals(idComp, that.idComp);
        }
        return false;
    }

    public String getIdComp() {
        return idComp;
    }

    public void setIdComp(String idComp) {
        this.idComp = idComp;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Button getButton1() {
        return button1;
    }

    public void setButton1(Button button1) {
        this.button1 = button1;
    }

    public Button getButton2() {
        return button2;
    }

    public void setButton2(Button button2) {
        this.button2 = button2;
    }
}