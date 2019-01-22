package chico3434.billjobs.windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class MonthlyReport extends Stage {

    public MonthlyReport(String report) {
        setTitle("Relatório Mensal");

        ScrollPane root = new ScrollPane();
        root.setPadding(new Insets(10));


        Scene scene = new Scene(root, 400, 500);

        root.setContent(new Label(report));

        setScene(scene);
    }
}
