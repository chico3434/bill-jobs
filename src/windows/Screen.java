package windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.*;
import utils.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Screen {

    @FXML
    private Label lblBusinessmanName;

    @FXML
    private Label lblBusinessmanMoney;

    @FXML
    private Label lblCompanyName;

    @FXML
    private Label lblCompanyFunds;

    @FXML
    private Label lblDetails;

    @FXML
    private Button btnCreateCompany;

    @FXML
    private AnchorPane panelCompany;

    @FXML
    private VBox boxServers;

    @FXML
    private VBox boxFactories;

    @FXML
    private VBox boxSheds;

    @FXML
    private VBox boxProducts;

    private List<TableCell> cServers = new ArrayList<>();
    private List<TableCell> cFactories = new ArrayList<>();
    private List<TableCell> cSheds = new ArrayList<>();
    private List<TableCell> cProducts = new ArrayList<>();

    public static String buy;

    public void initialize(){
        // F11 para tecla cheia ativado
        Main.screen.setFullScreenExitHint("Tecle F11 para sair da tela cheia");
        Main.screen.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            if (t.getCode() == KeyCode.F11) {
                Main.screen.setFullScreen(!Main.screen.isFullScreen());
            }
        });

        // Definindo componentes
        lblBusinessmanName.setText(Game.getBusinessman().getName());

        if (Game.getBusinessman().getCompany() == null){
            btnCreateCompany.setText("Criar sua empresa");

            // Consertando bug no dinheiro 0
            lblBusinessmanMoney.setText(Game.getMoney());

            // Tornando componentes que necessitamd e uma empresa invisíveis
            panelCompany.setVisible(false);
            boxServers.setVisible(false);
            boxFactories.setVisible(false);
            boxSheds.setVisible(false);
            boxProducts.setVisible(false);
        } else {
            btnCreateCompany.setVisible(false);
            updateData();
        }
    }

    public void createCompanyClicked(){
        try {
            Main.createCompany = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/create-company.fxml"));
            Main.createCompany.setTitle("Criar Empresa");
            Scene scene = new Scene(root, 400, 300);
            Main.createCompany.setScene(scene);
            Main.createCompany.showAndWait();

            // Tornando invisível botão criar empresa, pois não é possível criar duas empresas
            btnCreateCompany.setVisible(false);

            // Tornando visíveis componentes que dependem de uma Company
            panelCompany.setVisible(true);
            boxServers.setVisible(true);
            boxFactories.setVisible(true);
            boxSheds.setVisible(true);
            boxProducts.setVisible(true);

            updateData();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateCells(){
        List<Server> servers = Game.getBusinessman().getCompany().getServers();
        for(Server s : servers){
            TableCell tc = new TableCell(s.getId(), createLabel(s.getId()), createButton("Usar", s), createButton("Vender", s));
            if (!cServers.contains(tc)){
                cServers.add(tc);
                boxServers.getChildren().add(tc);
            }
        }
        List<Factory> factories = Game.getBusinessman().getCompany().getFactories();
        for(Factory f : factories){
            TableCell ct = new TableCell(f.getId(), createLabel(f.getId()), createButton("Usar", f), createButton("Vender", f));
            if (!cFactories.contains(ct)){
                cFactories.add(ct);
                boxFactories.getChildren().add(ct);
            }
        }

        List<Shed> sheds = Game.getBusinessman().getCompany().getSheds();
        for(Shed s : sheds){
            TableCell ct = new TableCell(s.getId(), createLabel(s.getId()), createButton("Usar", s), createButton("Vender", s));
            if (!cSheds.contains(ct)){
                cSheds.add(ct);
                boxSheds.getChildren().add(ct);
            }
        }

        List<Product> products = new ArrayList<>();
        products.addAll(Game.getBusinessman().getCompany().getSoftwares());
        products.addAll(Game.getBusinessman().getCompany().getHardwares());
        for(TableCell c : cProducts){
            boxProducts.getChildren().remove(c);
        }
        cProducts.clear();
        for(Product p : products){
            TableCell ct = new TableCell(p.getId(), createLabel(p.getId()), createButton("Editar", p), createButton("Remover", p));
            if (!cProducts.contains(ct)){
                cProducts.add(ct);
                boxProducts.getChildren().add(ct);
                boxProducts.getChildren();
            }
        }
    }

    public void updateData(){
        // Atualizando área monetária
        lblCompanyFunds.setText(Game.getFunds());
        lblBusinessmanMoney.setText(Game.getMoney());

        // Nome empresa - ainda nao é possivel mudar, mas sla
        lblCompanyName.setText(Game.getBusinessman().getCompany().getName());

        // Atualizar celulas
        updateCells();
    }

    private Label createLabel(String string){
        Label label = new Label(string);

        // Lógica para mostrar os detalhes deste objects no painel de detalhes
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                List<Infrastructure>  infrastructures = new ArrayList<>();
                infrastructures.addAll(Game.getBusinessman().getCompany().getServers());
                infrastructures.addAll(Game.getBusinessman().getCompany().getFactories());
                infrastructures.addAll(Game.getBusinessman().getCompany().getSheds());

                if (infrastructures.contains(new Infrastructure(string))){
                    Infrastructure i = infrastructures.get(infrastructures.indexOf(new Infrastructure(string)));

                    String info = null;

                    if (i instanceof Server){
                        Server s = (Server) i;
                        info = s.getType();
                        info += "\nId: " + s.getId() +
                                "\nPreço: " + s.getPrice() +
                                "\nCapacidade: " + s.getCapacity();
                    } else if (i instanceof Factory){
                        Factory f = (Factory) i;
                        info = f.getType();
                        info += "\nId: " + f.getId() +
                                "\nPreço: " + f.getPrice() +
                                "\nPotencial: " + f.getPotential() +
                                "\nNível: " + f.getLevel();
                    } else if (i instanceof Shed){
                        Shed g = (Shed) i;
                        info = g.getType();
                        info += "\nId: " + g.getId() +
                                "\nPreço: " + g.getPrice() +
                                "\nCapacidade: " + g.getCapacity();
                    }

                    lblDetails.setText(info);
                    return;
                }

                List<Product> products = new ArrayList<>();
                products.addAll(Game.getBusinessman().getCompany().getSoftwares());
                products.addAll(Game.getBusinessman().getCompany().getHardwares());

                if (products.contains(new Product(string, ""))){
                    Product p = products.get(products.indexOf(new Product(string, "")));
                    String info = "Product\nId: " + p.getId() + "\nNome: " + p.getName() + "\nVersão: " + p.getVersion() +
                            "\nDescrição: " + p.getDescription() + "\nPreço: " + p.getPrice() + "\nCusto: " + p.getCost();

                    lblDetails.setText(info);
                }
            }
        });
        return label;
    }

    private Button createButton(String string, Object o){
        Button button = new Button(string);
        button.setOnAction((e) -> {
            if (string.equals("Usar")){
                Infrastructure i = (Infrastructure) o;
                if (i instanceof Server){
                    Server s = (Server) i;
                    UseServer us = new UseServer(s);
                    us.showAndWait();
                } else if (i instanceof Factory){
                    Factory f = (Factory) i;
                    UseFactory uf = new UseFactory(f);
                    uf.showAndWait();
                } else if (i instanceof Shed) {
                    Shed g = (Shed) i;
                    UseShed us = new UseShed(g);
                    us.showAndWait();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Função ainda não implementada").show();
                }
            } else if (string.equals("Remover")){

                if (o instanceof Product) {
                    Product p = (Product) o;
                    if (p instanceof Software){
                        Software s = (Software) p;
                        Game.getBusinessman().getCompany().removeSoftware(s);
                        TableCell tc = cProducts.get(cProducts.indexOf(new TableCell(s.getId(), new Label(), new Button(), new Button())));
                        cProducts.remove(tc);
                        boxProducts.getChildren().removeAll(tc);
                    } else if (p instanceof Hardware){
                        Hardware h = (Hardware) p;
                        Game.getBusinessman().getCompany().removeHardware(h);
                        TableCell ct = cProducts.get(cProducts.indexOf(new TableCell(h.getId(), new Label(), new Button(), new Button())));
                        cProducts.remove(ct);
                        boxProducts.getChildren().removeAll(ct);
                    }
                }
                updateData();
            } else if (string.equals("Vender")){
                Infrastructure i = (Infrastructure) o;
                if (i instanceof Server){
                    TableCell tc = cServers.get(cServers.indexOf(new TableCell(i.getId(), new Label(), new Button(), new Button())));
                    cServers.remove(tc);
                    boxServers.getChildren().removeAll(tc);
                    Game.getBusinessman().getCompany().buyServer((Server) i);
                } else if (i instanceof Factory){
                    TableCell ct = cFactories.get(cFactories.indexOf(new TableCell(i.getId(), new Label(), new Button(), new Button())));
                    cFactories.remove(ct);
                    boxFactories.getChildren().removeAll(ct);
                    Game.getBusinessman().getCompany().buyFactory((Factory) i);
                } else if (i instanceof Shed){
                    TableCell ct = cSheds.get(cSheds.indexOf(new TableCell(i.getId(), new Label(), new Button(), new Button())));
                    cSheds.remove(ct);
                    boxSheds.getChildren().removeAll(ct);
                    Game.getBusinessman().getCompany().buyShed((Shed) i);
                }
                updateData();
            } else if(string.equals("Editar")) {
            	//new Alert(Alert.AlertType.WARNING, "Função ainda não implementada").show();
                Product p = (Product) o;
                CreateOrEditProduct cep = new CreateOrEditProduct(p);
                cep.showAndWait();
                updateData();
            }
        });
        return button;
    }

    public void exitGame(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void showAbout(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.INFORMATION, "Game para ganhar 2 pontos em Java").show();
    }

    public void buyServer(ActionEvent actionEvent) {
        try {
            buy = "Server";
            Main.buy = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/buy.fxml"));
            Main.buy.setTitle("Buy " + buy);
            Main.buy.setScene(new Scene(root, 300, 125));
            Main.buy.showAndWait();

            buy = null;
            updateData();
        } catch (IOException e) {
            System.out.println("Erro em " + e);
            e.printStackTrace();
        }

    }

    public void buyFactory(ActionEvent actionEvent) {
        try{
            buy = "Fábrica";
            Main.buy = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/buy.fxml"));
            Main.buy.setTitle("Buy " + buy);
            Main.buy.setScene(new Scene(root, 300, 125));
            Main.buy.showAndWait();

            buy = null;
            updateData();
        } catch (IOException e) {
            System.out.println("Erro em " + e);
            e.printStackTrace();
        }
    }

    public void buyShed(ActionEvent actionEvent) {
        try{
            buy = "Galpão";
            Main.buy = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/buy.fxml"));
            Main.buy.setTitle("Buy " + buy);
            Main.buy.setScene(new Scene(root, 300, 125));
            Main.buy.showAndWait();

            buy = null;
            updateData();
        } catch (IOException e) {
            System.out.println("Erro em " + e);
            e.printStackTrace();
        }
    }

    public void createProduct(ActionEvent actionEvent) {
        CreateOrEditProduct cep = new CreateOrEditProduct(null);
        cep.showAndWait();
        updateData();
    }

    public void transferOrReceive(ActionEvent actionEvent) {
        Transfer t = new Transfer(Game.getBusinessman(), Game.getBusinessman().getCompany());
        t.showAndWait();
        updateData();
    }

    public void spendTime(ActionEvent actionEvent) {
        Game.spendTime();

        new MonthlyReport(Game.getReport()).showAndWait();

        updateData();
    }

    public void saveGame(ActionEvent actionEvent) {
        Game.save();
    }
    
    public void showHowToPlay(ActionEvent actionEvent) {
        HowToPlay cj = new HowToPlay();
        cj.showAndWait();
    }
}
