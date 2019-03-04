package chico3434.billjobs.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class TableCell extends HBox {

    private String idComp;

    private Label label;

    private Button button1, button2;

    TableCell(String id, Label lbl, Button btn1, Button btn2) {
        this.idComp = id;
        label = lbl;
        
        HBox buttons = new HBox();
        button1 = btn1;
        button2 = btn2;
        
        buttons.getChildren().addAll(button1, button2);
        buttons.setSpacing(5);

        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        this.getChildren().addAll(label, buttons);
    }

    /* public void remove(){
        this.setVisible(false);
    } */


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof TableCell) {
            TableCell that = (TableCell) o;
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
