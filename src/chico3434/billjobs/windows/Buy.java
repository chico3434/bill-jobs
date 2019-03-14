package chico3434.billjobs.windows;

import chico3434.billjobs.exceptions.MissingFunds;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import chico3434.billjobs.utils.Game;

import java.util.Random;

public class Buy {

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

    public void initialize() {
        Random r = new Random();
        switch (Screen.buy) {
            case "Server":
                lbl1.setText(String.format("%.2f", 1000 + (r.nextDouble() * 1000)));
                lbl3.setText(String.format("%.2f", 5000 + (r.nextDouble() * 5000)));
                lbl5.setText(String.format("%.2f", 10000 + (r.nextDouble() * 10000)));

                lbl2.setText(String.valueOf(1 + r.nextInt(2)));
                lbl4.setText(String.valueOf(5 + r.nextInt(5)));
                lbl6.setText(String.valueOf(10 + r.nextInt(10)));
                break;
            case "Fábrica":
                lbl1.setText(String.format("%.2f", 1000 + (r.nextDouble() * 1000)));
                lbl3.setText(String.format("%.2f", 5000 + (r.nextDouble() * 5000)));
                lbl5.setText(String.format("%.2f", 10000 + (r.nextDouble() * 10000)));

                lbl2.setText((1 + r.nextInt(2)) + " ; " + (100 + r.nextInt(100)));
                lbl4.setText((5 + r.nextInt(5)) + " ; " + (500 + r.nextInt(500)));
                lbl6.setText((10 + r.nextInt(10)) + " ; " + (1000 + r.nextInt(1000)));
                break;
            case "Galpão":
                lbl1.setText(String.format("%.2f", 1000 + (r.nextDouble() * 1000)));
                lbl3.setText(String.format("%.2f", 5000 + (r.nextDouble() * 5000)));
                lbl5.setText(String.format("%.2f", 10000 + (r.nextDouble() * 10000)));

                lbl2.setText(String.valueOf(1000 + r.nextInt(1000)));
                lbl4.setText(String.valueOf(10000 + r.nextInt(10000)));
                lbl6.setText(String.valueOf(100000 + r.nextInt(100000)));
                break;
        }
    }

    public void buy1(ActionEvent actionEvent) {
        makePurchase(lbl1.getText(), lbl2.getText());
    }
    public void buy2(ActionEvent actionEvent) {
        makePurchase(lbl3.getText(), lbl4.getText());
    }
    public void buy3(ActionEvent actionEvent) {
        makePurchase(lbl5.getText(), lbl6.getText());
    }

    private void makePurchase(String s, String s1){
        switch (Screen.buy) {
            case "Server":
                try {
                    Game.buyServer(Double.parseDouble(s.replace(',', '.')), Integer.parseInt(s1));
                    Main.buy.close();
                } catch (MissingFunds missingFunds) {
                    Alert a = new Alert(Alert.AlertType.WARNING, missingFunds.toString());
                    a.show();
                }
                break;
            case "Fábrica":
                try {
                    String[] split = s1.split(";");
                    Game.buyFactory(Double.parseDouble(s.replace(',', '.')), Integer.parseInt(split[1]), Integer.parseInt(split[0]));
                    Main.buy.close();
                } catch (MissingFunds missingFunds) {
                    Alert a = new Alert(Alert.AlertType.WARNING, missingFunds.toString());
                    a.show();
                }
                break;
            case "Galpão":
                try {
                    Game.buyShed(Double.parseDouble(s.replace(',', '.')), Integer.parseInt(s1));
                    Main.buy.close();
                } catch (MissingFunds missingFunds) {
                    Alert a = new Alert(Alert.AlertType.WARNING, missingFunds.toString());
                    a.show();
                }
                break;
        }
    }
}
