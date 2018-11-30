package janela;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objeto.*;
import utils.Jogo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tela {

    @FXML
    private Label lblNomeEmpresario;

    @FXML
    private Label lblDinheiroEmpresario;

    @FXML
    private Label lblNomeEmpresa;

    @FXML
    private Label lblVerbaEmpresa;

    @FXML
    private Label lblDetalhes;

    @FXML
    private Button btnCriarEmpresa;

    @FXML
    private AnchorPane painelEmpresa;

    @FXML
    private VBox boxServidores;

    @FXML
    private VBox boxFabricas;

    @FXML
    private VBox boxGalpoes;

    @FXML
    private VBox boxProdutos;

    private List<CelulaTabela> cServidores = new ArrayList<>();
    private List<CelulaTabela> cFabricas = new ArrayList<>();
    private List<CelulaTabela> cGalpoes = new ArrayList<>();
    private List<CelulaTabela> cProdutos = new ArrayList<>();

    public static String comprar;

    public void initialize(){
        // F11 para tecla cheia ativado
        Main.tela.setFullScreenExitHint("Tecle F11 para sair da tela cheia");
        Main.tela.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            if (t.getCode() == KeyCode.F11) {
                Main.tela.setFullScreen(!Main.tela.isFullScreen());
            }
        });

        // Definindo componentes
        lblNomeEmpresario.setText(Jogo.getEmpresario().getNome());

        if (Jogo.getEmpresario().getEmpresa() == null){
            btnCriarEmpresa.setText("Criar sua empresa");

            // Consertando bug no dinheiro 0
            lblDinheiroEmpresario.setText(Jogo.getDinheiro());

            // Tornando componentes que necessitamd e uma empresa invisíveis
            painelEmpresa.setVisible(false);
            boxServidores.setVisible(false);
            boxFabricas.setVisible(false);
            boxGalpoes.setVisible(false);
            boxProdutos.setVisible(false);
        } else {
            btnCriarEmpresa.setVisible(false);
            atualizarDados();
        }
    }

    public void criarEmpresaClicado(){
        try {
            Main.criarEmpresa = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/criar-empresa.fxml"));
            Main.criarEmpresa.setTitle("Criar Empresa");
            Scene scene = new Scene(root, 400, 300);
            Main.criarEmpresa.setScene(scene);
            Main.criarEmpresa.showAndWait();

            // Tornando invisível botão criar empresa, pois não é possível criar duas empresas
            btnCriarEmpresa.setVisible(false);

            // Tornando visíveis componentes que dependem de uma Empresa
            painelEmpresa.setVisible(true);
            boxServidores.setVisible(true);
            boxFabricas.setVisible(true);
            boxGalpoes.setVisible(true);
            boxProdutos.setVisible(true);

            atualizarDados();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void atualizarCelulas(){
        List<Servidor> servidores = Jogo.getEmpresario().getEmpresa().getServidores();
        for(Servidor s : servidores){
            CelulaTabela ct = new CelulaTabela(s.getId(), criarLabel(s.getId()), criarButton("Usar", s), criarButton("Vender", s));
            if (!cServidores.contains(ct)){
                cServidores.add(ct);
                boxServidores.getChildren().add(ct);
            }
        }
        List<Fabrica> fabricas = Jogo.getEmpresario().getEmpresa().getFabricas();
        for(Fabrica f : fabricas){
            CelulaTabela ct = new CelulaTabela(f.getId(), criarLabel(f.getId()), criarButton("Usar", f), criarButton("Vender", f));
            if (!cFabricas.contains(ct)){
                cFabricas.add(ct);
                boxFabricas.getChildren().add(ct);
            }
        }

        List<Galpao> galpoes = Jogo.getEmpresario().getEmpresa().getGalpoes();
        for(Galpao g : galpoes){
            CelulaTabela ct = new CelulaTabela(g.getId(), criarLabel(g.getId()), criarButton("Usar", g), criarButton("Vender", g));
            if (!cGalpoes.contains(ct)){
                cGalpoes.add(ct);
                boxGalpoes.getChildren().add(ct);
            }
        }

        List<Produto> produtos = Jogo.getEmpresario().getEmpresa().getProdutos();
        for(Produto p : produtos){
            CelulaTabela ct = new CelulaTabela(p.getId(), criarLabel(p.getId()), criarButton("Usar", p), criarButton("Remover", p));
            if (!cProdutos.contains(ct)){
                cProdutos.add(ct);
                boxProdutos.getChildren().add(ct);
            }
        }
    }

    public void atualizarDados(){
        // Atualizando área monetária
        lblVerbaEmpresa.setText(Jogo.getVerba());
        lblDinheiroEmpresario.setText(Jogo.getDinheiro());

        // Nome empresa - ainda nao é possivel mudar, mas sla
        lblNomeEmpresa.setText(Jogo.getEmpresario().getEmpresa().getNome());

        // Atualizar celulas
        atualizarCelulas();
    }

    private Label criarLabel(String string){
        Label label = new Label(string);

        // Lógica para mostrar os detalhes deste objeto no painel de detalhes
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                List<Infraestrutura>  lista = new ArrayList<>();
                lista.addAll(Jogo.getEmpresario().getEmpresa().getServidores());
                lista.addAll(Jogo.getEmpresario().getEmpresa().getFabricas());
                lista.addAll(Jogo.getEmpresario().getEmpresa().getGalpoes());

                if (lista.contains(new Infraestrutura(string))){
                    Infraestrutura i = lista.get(lista.indexOf(new Infraestrutura(string)));

                    String info = null;

                    if (i instanceof Servidor){
                        Servidor s = (Servidor) i;
                        info = s.getTipo();
                        info += "\nId: " + s.getId() +
                                "\nPreço: " + s.getPreco() +
                                "\nCapacidade: " + s.getCapacidade();
                    } else if (i instanceof Fabrica){
                        Fabrica f = (Fabrica) i;
                        info = f.getTipo();
                        info += "\nId: " + f.getId() +
                                "\nPreço: " + f.getPreco() +
                                "\nPotencial: " + f.getPotencial() +
                                "\nNível: " + f.getNivel();
                    } else if (i instanceof Galpao){
                        Galpao g = (Galpao) i;
                        info = g.getTipo();
                        info += "\nId: " + g.getId() +
                                "\nPreço: " + g.getPreco() +
                                "\nCapacidade: " + g.getCapacidade();
                    }

                    lblDetalhes.setText(info);
                    return;
                }

                List<Produto> produtos = Jogo.getEmpresario().getEmpresa().getProdutos();

                if (produtos.contains(new Produto(string, ""))){
                    Produto p = produtos.get(produtos.indexOf(new Produto(string, "")));
                    String info = "Produto\nId: " + p.getId() + "\nNome: " + p.getNome() + "\nVersão: " + p.getVersao() +
                            "\nDescrição: " + p.getDescricao() + "\nPreço: " + p.getPreco() + "\nCusto: " + p.getCusto();

                    lblDetalhes.setText(info);
                }
            }
        });
        return label;
    }

    private Button criarButton(String string, Object o){
        Button button = new Button(string);
        button.setOnAction((e) -> {
            if (string.equals("Usar")){
                Infraestrutura i = (Infraestrutura) o;
                if (i instanceof Servidor){
                    Servidor s = (Servidor) i;
                    UsarServidor us = new UsarServidor(s);
                    us.showAndWait();
                } else if (i instanceof Fabrica){
                    Fabrica f = (Fabrica) i;
                    UsarFabrica uf = new UsarFabrica(f);
                    uf.showAndWait();
                } else if (i instanceof Galpao) {
                    Galpao g = (Galpao) i;
                    UsarGalpao ug = new UsarGalpao(g);
                    ug.showAndWait();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Função ainda não implementada").show();
                }
            } else if (string.equals("Remover")){

                if (o instanceof Produto) {
                    Produto p = (Produto) o;
                    if (p instanceof Software){
                        Software s = (Software) p;
                        Jogo.getEmpresario().getEmpresa().removerSoftware(s);
                        CelulaTabela ct = cProdutos.get(cProdutos.indexOf(new CelulaTabela(s.getId(), new Label(), new Button(), new Button())));
                        cProdutos.remove(ct);
                        boxProdutos.getChildren().removeAll(ct);
                    } else if (p instanceof Hardware){
                        Hardware h = (Hardware) p;
                        Jogo.getEmpresario().getEmpresa().removerHardware(h);
                        CelulaTabela ct = cProdutos.get(cProdutos.indexOf(new CelulaTabela(h.getId(), new Label(), new Button(), new Button())));
                        cProdutos.remove(ct);
                        boxProdutos.getChildren().removeAll(ct);
                    }
                }
                atualizarDados();
            } else if (string.equals("Vender")){
                Infraestrutura i = (Infraestrutura) o;
                if (i instanceof Servidor){
                    CelulaTabela ct = cServidores.get(cServidores.indexOf(new CelulaTabela(i.getId(), new Label(), new Button(), new Button())));
                    cServidores.remove(ct);
                    boxServidores.getChildren().removeAll(ct);
                    Jogo.getEmpresario().getEmpresa().venderServidor((Servidor) i);
                } else if (i instanceof Fabrica){
                    CelulaTabela ct = cFabricas.get(cFabricas.indexOf(new CelulaTabela(i.getId(), new Label(), new Button(), new Button())));
                    cFabricas.remove(ct);
                    boxFabricas.getChildren().removeAll(ct);
                    Jogo.getEmpresario().getEmpresa().venderFabrica((Fabrica) i);
                } else if (i instanceof Galpao){
                    CelulaTabela ct = cGalpoes.get(cGalpoes.indexOf(new CelulaTabela(i.getId(), new Label(), new Button(), new Button())));
                    cGalpoes.remove(ct);
                    boxGalpoes.getChildren().removeAll(ct);
                    Jogo.getEmpresario().getEmpresa().venderGalpao((Galpao) i);
                }
                atualizarDados();
            }
        });
        return button;
    }

    public void sairJogo(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void abrirSobre(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.INFORMATION, "Jogo para ganhar 2 pontos em Java").show();
    }

    public void comprarServidor(ActionEvent actionEvent) {
        try {
            comprar = "Servidor";
            Main.comprar = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/comprar.fxml"));
            Main.comprar.setTitle("Comprar " + comprar);
            Main.comprar.setScene(new Scene(root, 300, 125));
            Main.comprar.showAndWait();

            comprar = null;
            atualizarDados();
        } catch (IOException e) {
            System.out.println("Erro em " + e);
            e.printStackTrace();
        }

    }

    public void comprarFabrica(ActionEvent actionEvent) {
        try{
            comprar = "Fábrica";
            Main.comprar = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/comprar.fxml"));
            Main.comprar.setTitle("Comprar " + comprar);
            Main.comprar.setScene(new Scene(root, 300, 125));
            Main.comprar.showAndWait();

            comprar = null;
            atualizarDados();
        } catch (IOException e) {
            System.out.println("Erro em " + e);
            e.printStackTrace();
        }
    }

    public void comprarGalpao(ActionEvent actionEvent) {
        try{
            comprar = "Galpão";
            Main.comprar = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/comprar.fxml"));
            Main.comprar.setTitle("Comprar " + comprar);
            Main.comprar.setScene(new Scene(root, 300, 125));
            Main.comprar.showAndWait();

            comprar = null;
            atualizarDados();
        } catch (IOException e) {
            System.out.println("Erro em " + e);
            e.printStackTrace();
        }
    }

    public void criarProduto(ActionEvent actionEvent) {
        try {
            Main.criarProduto = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/criar-produto.fxml"));
            Main.criarProduto.setTitle("Criar Produto");
            Main.criarProduto.setScene(new Scene(root, 400, 600));
            Main.criarProduto.showAndWait();

            atualizarDados();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transferirOuReceber(ActionEvent actionEvent) {
        Transferir t = new Transferir(Jogo.getEmpresario(), Jogo.getEmpresario().getEmpresa());
        t.showAndWait();
        atualizarDados();
    }

    public void passarTempo(ActionEvent actionEvent) {
        Jogo.passarTempo();

    /*
     * Antiga geração de relatório
     *
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Relatório");
        a.setHeaderText("Relatório mensal atualizado");
        a.setContentText(Jogo.getRelatorio());
        a.showAndWait();
    */

        new RelatorioMensal(Jogo.getRelatorio()).showAndWait();

        atualizarDados();
    }

    public void salvarJogo(ActionEvent actionEvent) {
        Jogo.salvar();
    }
}
