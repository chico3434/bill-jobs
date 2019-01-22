package chico3434.billjobs.windows;

import chico3434.billjobs.exceptions.MissingMoney;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import chico3434.billjobs.utils.Game;

import java.text.NumberFormat;

public class CreateCompany {

    @FXML
    private TextField companyName;

    @FXML
    private Slider transferredFunds;

    @FXML
    private TextField tfFunds;

    public void initialize(){
        transferredFunds.setMin(0);
        transferredFunds.setMax(Game.getBusinessman().getMoney());
        transferredFunds.setValue(Game.getBusinessman().getMoney()/2);

        tfFunds.setDisable(true);
        tfFunds.textProperty().bindBidirectional(transferredFunds.valueProperty(), NumberFormat.getNumberInstance());
    }

    public void createCompany(){
        try {
            Game.createCompany(companyName.getText(), transferredFunds.getValue());
            Main.createCompany.close();
        } catch (MissingMoney e){
            Alert a = new Alert(Alert.AlertType.WARNING, "Dinheiro Insuficiente");
            a.show();
        }
    }

}
