package chico3434.billjobs.windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import chico3434.billjobs.objects.*;
import chico3434.billjobs.utils.Game;

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

    private List<TableCell> serverTableCellList = new ArrayList<>();
    private List<TableCell> factoryTableCellList = new ArrayList<>();
    private List<TableCell> shedTableCellList = new ArrayList<>();
    private List<TableCell> productTableCellList = new ArrayList<>();

    static String buy;

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

    private void updateInfrastructureCell(String id, Button use, Button sell, List<TableCell> tableCells, VBox vBox) {
        TableCell tableCell = new TableCell(id, createLabel(id), use, sell);
        if (!tableCells.contains(tableCell)) {
            tableCells.add(tableCell);
            vBox.getChildren().add(tableCell);
        }
    }

    private void updateCells() {
        List<Server> servers = Game.getBusinessman().getCompany().getServers();
        for (Server server : servers) {
            updateInfrastructureCell(server.getId(), createButton("Usar", server), createButton("Vender", server), serverTableCellList, boxServers);
        }

        List<Factory> factories = Game.getBusinessman().getCompany().getFactories();
        for(Factory factory : factories){
            updateInfrastructureCell(factory.getId(), createButton("Usar", factory), createButton("Vender", factory), factoryTableCellList, boxFactories);
        }

        List<Shed> sheds = Game.getBusinessman().getCompany().getShedList();
        for(Shed shed : sheds){
            updateInfrastructureCell(shed.getId(), createButton("Usar", shed), createButton("Vender", shed), shedTableCellList, boxSheds);
        }

        List<Product> products = new ArrayList<>();
        products.addAll(Game.getBusinessman().getCompany().getSoftwareList());
        products.addAll(Game.getBusinessman().getCompany().getHardwareList());
        for(TableCell tableCell : productTableCellList){
            boxProducts.getChildren().remove(tableCell);
        }
        productTableCellList.clear();
        for(Product p : products){
            TableCell ct = new TableCell(p.getId(), createLabel(p.getId()), createButton("Editar", p), createButton("Remover", p));
            if (!productTableCellList.contains(ct)){
                productTableCellList.add(ct);
                boxProducts.getChildren().add(ct);
                boxProducts.getChildren();
            }
        }
    }

    private void updateData() {
        // Atualizando área monetária
        lblCompanyFunds.setText(Game.getFunds());
        lblBusinessmanMoney.setText(Game.getMoney());

        // Nome empresa - ainda nao é possivel mudar, mas sla
        lblCompanyName.setText(Game.getBusinessman().getCompany().getName());

        // Atualizar celulas
        updateCells();
    }

    private Label createLabel(String string) {
        Label label = new Label(string);

        // Lógica para mostrar os detalhes deste chico3434.billjobs.objects no painel de detalhes
        label.setOnMouseClicked(mouseEvent -> {
            List<Infrastructure>  infrastructures = new ArrayList<>();
            infrastructures.addAll(Game.getBusinessman().getCompany().getServers());
            infrastructures.addAll(Game.getBusinessman().getCompany().getFactories());
            infrastructures.addAll(Game.getBusinessman().getCompany().getShedList());

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
            products.addAll(Game.getBusinessman().getCompany().getSoftwareList());
            products.addAll(Game.getBusinessman().getCompany().getHardwareList());

            if (products.contains(new Product(string, ""))){
                Product p = products.get(products.indexOf(new Product(string, "")));
                String info = "Product\nId: " + p.getId() + "\nNome: " + p.getName() + "\nVersão: " + p.getVersion() +
                        "\nDescrição: " + p.getDescription() + "\nPreço: " + p.getPrice() + "\nCusto: " + p.getCost();

                lblDetails.setText(info);
            }
        });
        return label;
    }

    private Button createButton(String string, Object o){
        Button button = new Button(string);
        button.setOnAction((e) -> {
            switch (string) {
                case "Usar": {
                    Infrastructure i = (Infrastructure) o;
                    if (i instanceof Server) {
                        Server s = (Server) i;
                        UseServer us = new UseServer(s);
                        us.showAndWait();
                    } else if (i instanceof Factory) {
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
                    break;
                }
                case "Remover":

                    if (o instanceof Product) {
                        Product product = (Product) o;
                        if (product instanceof Software) {
                            Software software = (Software) product;
                            Game.getBusinessman().getCompany().removeSoftware(software);
                            removeTableCell(software.getId());
                        } else if (product instanceof Hardware) {
                            Hardware hardware = (Hardware) product;
                            Game.getBusinessman().getCompany().removeHardware(hardware);
                            removeTableCell(hardware.getId());
                        }
                    }
                    updateData();
                    break;
                case "Vender": {
                    Infrastructure infrastructure = (Infrastructure) o;
                    if (infrastructure instanceof Server) {
                        removeInfrastructure(infrastructure, serverTableCellList, boxServers);
                        Game.getBusinessman().getCompany().sellServer((Server) infrastructure);
                    } else if (infrastructure instanceof Factory) {
                        removeInfrastructure(infrastructure, factoryTableCellList, boxFactories);
                        Game.getBusinessman().getCompany().sellFactory((Factory) infrastructure);
                    } else if (infrastructure instanceof Shed) {
                        removeInfrastructure(infrastructure, shedTableCellList, boxSheds);
                        Game.getBusinessman().getCompany().sellShed((Shed) infrastructure);
                    }
                    updateData();
                    break;
                }
                case "Editar":
                    //new Alert(Alert.AlertType.WARNING, "Função ainda não implementada").show();
                    Product p = (Product) o;
                    CreateOrEditProduct cep = new CreateOrEditProduct(p);
                    cep.showAndWait();
                    updateData();
                    break;
            }
        });
        return button;
    }

    private void removeInfrastructure(Infrastructure infrastructure, List<TableCell> tableCellList, VBox vBox) {
        TableCell tableCell = tableCellList.get(tableCellList.indexOf(new TableCell(infrastructure.getId(), new Label(), new Button(), new Button())));
        tableCellList.remove(tableCell);
        vBox.getChildren().removeAll(tableCell);
    }

    private void removeTableCell(String id) {
        TableCell tableCell = productTableCellList.get(productTableCellList.indexOf(new TableCell(id, new Label(), new Button(), new Button())));
        productTableCellList.remove(tableCell);
        boxProducts.getChildren().removeAll(tableCell);
    }

    public void exitGame(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void showAbout(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.INFORMATION, "Jogo para ganhar 2 pontos em Java").show();
    }

    public void buyServer(ActionEvent actionEvent) {
        try {
            buy = "Server";
            openWindow();
        } catch (IOException e) {
            System.out.println("Erro em " + e);
            e.printStackTrace();
        }

    }

    private void openWindow() throws IOException {
        Main.buy = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/buy.fxml"));
        Main.buy.setTitle("Buy " + buy);
        Main.buy.setScene(new Scene(root, 300, 125));
        Main.buy.showAndWait();

        buy = null;
        updateData();
    }

    public void buyFactory(ActionEvent actionEvent) {
        try{
            buy = "Fábrica";
            openWindow();
        } catch (IOException e) {
            System.out.println("Erro em " + e);
            e.printStackTrace();
        }
    }

    public void buyShed(ActionEvent actionEvent) {
        try{
            buy = "Galpão";
            openWindow();
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
        Transfer transfer = new Transfer(Game.getBusinessman(), Game.getBusinessman().getCompany());
        transfer.showAndWait();
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
        HowToPlay howToPlay = new HowToPlay();
        howToPlay.showAndWait();
    }
}
