package janela;

import exceptions.VerbaInsuficiente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import utils.Jogo;

import java.util.Random;

public class Comprar {

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;

    @FXML
    private Label lbl5;

    @FXML
    private Label lbl6;

    public void initialize(){
        Random r = new Random();
        if (Tela.comprar.equals("Servidor")) {
            lbl1.setText(String.format("%.2f", 1000 + (r.nextDouble() * 1000)));
            lbl3.setText(String.format("%.2f", 5000 + (r.nextDouble() * 5000)));
            lbl5.setText(String.format("%.2f", 10000 + (r.nextDouble() * 10000)));

            lbl2.setText(String.valueOf(1 + r.nextInt(2)));
            lbl4.setText(String.valueOf(5 + r.nextInt(5)));
            lbl6.setText(String.valueOf(10 + r.nextInt(10)));
        } else if (Tela.comprar.equals("Fábrica")) {
            lbl1.setText(String.format("%.2f", 1000 + (r.nextDouble() * 1000)));
            lbl3.setText(String.format("%.2f", 5000 + (r.nextDouble() * 5000)));
            lbl5.setText(String.format("%.2f", 10000 + (r.nextDouble() * 10000)));

            lbl2.setText(String.valueOf(1 + r.nextInt(2)) + ";" + String.valueOf(100 + r.nextInt(100)));
            lbl4.setText(String.valueOf(5 + r.nextInt(5)) + ";" + String.valueOf(500 + r.nextInt(500)));
            lbl6.setText(String.valueOf(10 + r.nextInt(10)) + ";" + String.valueOf(1000 + r.nextInt(1000)));
        } else if (Tela.comprar.equals("Galpão")) {
            lbl1.setText(String.format("%.2f", 1000 + (r.nextDouble() * 1000)));
            lbl3.setText(String.format("%.2f", 5000 + (r.nextDouble() * 5000)));
            lbl5.setText(String.format("%.2f", 10000 + (r.nextDouble() * 10000)));

            lbl2.setText(String.valueOf(1000 + r.nextInt(1000)));
            lbl4.setText(String.valueOf(10000 + r.nextInt(10000)));
            lbl6.setText(String.valueOf(100000 + r.nextInt(100000)));
        }
    }

    public void comprar1(ActionEvent actionEvent) {
        efetivarCompra(lbl1.getText(), lbl2.getText());
    }
    public void comprar2(ActionEvent actionEvent) {
        efetivarCompra(lbl3.getText(), lbl4.getText());
    }
    public void comprar3(ActionEvent actionEvent) {
        efetivarCompra(lbl5.getText(), lbl6.getText());
    }

    public void efetivarCompra(String s, String s1){
        if (Tela.comprar.equals("Servidor")){
            try {
                Jogo.comprarServidor(Double.parseDouble(s.replace(',','.')), Integer.parseInt(s1));
                Main.comprar.close();
            } catch (VerbaInsuficiente verbaInsuficiente) {
                Alert a = new Alert(Alert.AlertType.WARNING, verbaInsuficiente.toString());
                a.show();
            }
        } else if (Tela.comprar.equals("Fábrica")){
            try{
                String[] split = s1.split(";");
                Jogo.comprarFabrica(Double.parseDouble(s.replace(',','.')), Integer.parseInt(split[1]), Integer.parseInt(split[0]));
                Main.comprar.close();
            } catch (VerbaInsuficiente verbaInsuficiente){
                Alert a = new Alert(Alert.AlertType.WARNING, verbaInsuficiente.toString());
                a.show();
            }
        } else if (Tela.comprar.equals("Galpão")){
            try {
                Jogo.comprarGalpao(Double.parseDouble(s.replace(',','.')), Integer.parseInt(s1));
                Main.comprar.close();
            } catch (VerbaInsuficiente verbaInsuficiente) {
                Alert a = new Alert(Alert.AlertType.WARNING, verbaInsuficiente.toString());
                a.show();
            }
        }
    }
}
