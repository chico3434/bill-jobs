package objeto;

import exceptions.GalpaoLotado;
import exceptions.NivelBaixo;
import exceptions.ProdutoExistente;
import exceptions.VerbaInsuficiente;
import utils.Jogo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Empresa {

    private String nome;
    private double verba;
    private List<Servidor> servidores;
    private List<Fabrica> fabricas;
    private List<Galpao> galpoes;
    private List<Software> softwares;
    private List<Hardware> hardwares;

    public Empresa(String nome) {
        this.nome = nome;
        servidores = new ArrayList<>();
        fabricas = new ArrayList<>();
        galpoes = new ArrayList<>();
    }

    public void comprarServidor(double preco, int capacidade) throws VerbaInsuficiente {
        if (verba >= preco) {
            String id = "Servidor" + (servidores.size() + 1);
             verba -= preco;
            servidores.add(new Servidor(id, preco, capacidade));
        } else {
            throw new VerbaInsuficiente("Verba Insuficiente");
        }
    }

    public void comprarFabrica(double preco, int potencial, int nivel) throws VerbaInsuficiente {
        if (verba >= preco) {
            String id = "Fábrica" + (fabricas.size() + 1);
             verba -= preco;
            fabricas.add(new Fabrica(id, preco, potencial, nivel));
        } else {
            throw new VerbaInsuficiente("Verba Insuficiente");
        }
    }

    public void comprarGalpao(double preco, int capacidade) throws VerbaInsuficiente {
        if (verba >= preco) {
            String id = "Galpão" + (galpoes.size() + 1);
             verba -= preco;
            galpoes.add(new Galpao(id, preco, capacidade));
        } else {
            throw new VerbaInsuficiente("Verba Insuficiente");
        }
    }

    public void criarSoftware(String nome, String versao, String descricao, double custo, double preco) throws VerbaInsuficiente, ProdutoExistente {
        if (verba < custo){
            throw new VerbaInsuficiente("Verba Insuficiente");
        } else if (produtos.contains(new Software(nome, versao))) {
            throw new ProdutoExistente("Produto já existe!");
        } else {
            verba -= custo;
            produtos.add(new Software(nome, versao, descricao, custo, preco));
        }
    }

    public void criarHardware(String nome, String versao, String descricao, double custo, double preco) throws ProdutoExistente {
        if (produtos.contains(new Hardware(nome, versao))){
            throw new ProdutoExistente("Produto já existe!");
        } else {
            produtos.add(new Hardware(nome, versao, descricao, custo, preco));
        }
    }

    public void venderSoftwares(){
        Iterator<Servidor> it = servidores.iterator();
        Random random = new Random();
        while (it.hasNext()){
            Servidor s = it.next();
            Iterator<Software> i = s.getSoftwares().iterator();
            while(i.hasNext()){
                Software software = i.next();
                double custo = software.getCusto();
                double preco = software.getPreco();
                double dif = preco - custo;
                int tot;
                if (dif<0){
                    int d = (int) (custo-preco);
                    d /= 100;
                    tot = 1 + random.nextInt(d);
                } else {
                    double c;
                    if (preco > 100){
                        tot = 0;
                    } else {
                        c = 30/dif;
                        tot = random.nextInt((int) c);
                    }
                }
                verba += preco * tot;
                Jogo.addDado(software.getId() + " em " + s.getId() + " vendas: " + tot + " renda: " + (preco*tot));
            }
        }
    }

    public void produzirHardware() {
        Iterator<Fabrica> it = fabricas.iterator();
        while (it.hasNext()){
            Fabrica f = it.next();
            Hardware produto = f.getProduto();

            if (produto == null){
                continue;
            }

            int qtd = f.produzir();
            if (verba < (qtd * produto.getCusto())){
                qtd = f.produzir(2);
                if (verba < (qtd * produto.getCusto())){
                    qtd = f.produzir(1);
                    if (verba < (qtd * produto.getCusto())){
                        Jogo.addDado("Não foi possível produzir nenhuma unidade\nde " + produto.getId() + " por falta de verba!");
                        continue;
                    }
                }
            }

            Jogo.addDado(qtd + " unidades de " + produto.getId() + " produzidas!");
            for (int i = 0; i < galpoes.size(); i++){
                try {
                    int falhas = galpoes.get(i).estocarHardware(produto, qtd);
                    Jogo.addDado((qtd - falhas) + " unidades de " + produto.getId() + " estocadas em " + galpoes.get(i).getId() + "!");
                    if (falhas == 0){
                        break;
                    } else {
                        qtd = falhas;
                    }
                } catch (GalpaoLotado galpaoLotado) {
                    continue;
                }
            }
            if (galpoes.get(galpoes.size()-1).isLotado()){
                Jogo.addDado("\nTodos os galpões lotaram\nAlguns produtos podem não ter sido produzidos\n");
                break;
            }
        }
    }

    public void venderHardware(){
        Iterator<Galpao> it = galpoes.iterator();

        while(it.hasNext()){
            Galpao galpao = it.next();
            List<Hardware> hardwares = galpao.getHardwares();
            List<Integer> quantidade = galpao.getQuantidade();

            for (int i = 0; i < hardwares.size(); i++){
                Hardware hard = hardwares.get(i);
                double aux = hard.getPreco()/hard.getCusto();
                double perc;

                Random rand = new Random();

                if (aux <= 3){
                    perc = 1 + rand.nextInt(4);
                } else  if (aux < 6){
                    perc = rand.nextInt(3);
                } else if (aux <= 10){
                    perc = rand.nextInt(2);
                } else {
                    perc = 0;
                }

                int totalVendido = (int)((quantidade.get(i)/4) * perc);

                Jogo.addDado(totalVendido + " vendidos de " + hard.getId() + " de " + galpao.getId() + ".");

                if (totalVendido == quantidade.get(i)){
                    quantidade.remove(i);
                    hardwares.remove(i);
                } else {
                    quantidade.add(i, (quantidade.get(i) - totalVendido));
                    quantidade.remove(i+1);
                }

                verba += (totalVendido * hard.getPreco());

            }
        }
    }

    public void removerSoftware(Software s){
        produtos.remove(produtos.indexOf(s));
        for(Servidor se: servidores){
            while (se.getSoftwares().contains(s)){
                se.getSoftwares().remove(s);
            }
        }
    }

    public void removerHardware(Hardware h){
        produtos.remove(h);
        for (Fabrica f : fabricas){
            if (f.getProduto().equals(h)){
                f.removerProduto();
            }
        }

        for (Galpao g : galpoes){
            List<Hardware> hs = g.getHardwares();
            if (hs.contains(h)) {
                int indice = hs.indexOf(h);
                g.getHardwares().remove(indice);
                g.getQuantidade().remove(indice);
            }
        }
    }

    public void venderServidor(Servidor s){
        verba += s.getPreco()/2;
        servidores.remove(s);
    }

    public void venderFabrica(Fabrica f){
        verba += f.getPreco()/2;
        fabricas.remove(f);
    }

    public void venderGalpao(Galpao g){
        verba += g.getPreco()/2;
        galpoes.remove(g);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getVerba() {
        return verba;
    }

    public void setVerba(double verba) {
        this.verba = verba;
    }

    public List<Fabrica> getFabricas() {
        return fabricas;
    }

    public void setFabricas(List<Fabrica> fabricas) {
        this.fabricas = fabricas;
    }

    public List<Galpao> getGalpoes() {
        return galpoes;
    }

    public void setGalpoes(List<Galpao> galpoes) {
        this.galpoes = galpoes;
    }

    public List<Servidor> getServidores() {
        return servidores;
    }

    public void setServidores(List<Servidor> servidores) {
        this.servidores = servidores;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
