package janela;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage inicial;
    public static Stage tela;
    public static Stage criarEmpresa;
    public static Stage comprar;

    @Override
    public void start(Stage primaryStage) throws Exception {

        inicial = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("fxml/inicial.fxml"));
        primaryStage.setTitle("Jogo");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
