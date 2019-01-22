package chico3434.billjobs.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import chico3434.billjobs.objects.Company;
import chico3434.billjobs.objects.Businessman;

import java.text.NumberFormat;

public class Transfer extends Stage {
    public Transfer(Businessman businessman, Company company) {

        setTitle("Tranferência");

        Slider s = new Slider(0, businessman.getMoney(), 0);
        if (businessman.getMoney() == 0){
            s.setDisable(true);
        }

        TextField tf = new TextField();
        tf.setDisable(true);
        tf.textProperty().bindBidirectional(s.valueProperty(), NumberFormat.getNumberInstance());


        Slider s1 = new Slider(0, company.getFunds(), 0);
        if (company.getFunds() == 0){
            s1.setDisable(true);
        }

        TextField tf1 = new TextField();
        tf1.setDisable(true);
        tf1.textProperty().bindBidirectional(s1.valueProperty(), NumberFormat.getNumberInstance());

        Button btnDone = new Button("Confirmar");

        btnDone.setOnAction(e -> {
            businessman.setMoney(businessman.getMoney() + s1.getValue() - s.getValue());
            company.setFunds(company.getFunds() + s.getValue() - s1.getValue());
            this.close();
        });

        VBox root = new VBox(5);

        Scene scene = new Scene(root, 300, 200);

        root.alignmentProperty().setValue(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(s, tf, s1, tf1, btnDone);

        setScene(scene);

    }
}
