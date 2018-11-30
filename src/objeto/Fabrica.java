package objeto;

import exceptions.NivelBaixo;

import java.util.Random;

public class Fabrica extends Infraestrutura{

    private int nivel;
    private int potencial;
    private Hardware produto;

    public Fabrica(String id, double preco, int potencial, int nivel) {
        super(id, preco);
        this.potencial = potencial;
        this.nivel = nivel;
    }

    public int produzir(){
        Random random = new Random();

        int porc = 1 + random.nextInt(4);

        return (potencial/4)*porc;
    }

    public int produzir(int i){
        return (potencial/4)*i;
    }

    public String getTipo(){
        return "Fábrica";
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPotencial() {
        return potencial;
    }

    public void setPotencial(int potencial) {
        this.potencial = potencial;
    }

    public Hardware getProduto() {
        return produto;
    }

    public void setProduto(Hardware produto) throws NivelBaixo {
        if ((produto.getCusto()/100) > nivel){
            throw new NivelBaixo("Nível da fábrica inferior ao do produto");
        }
        this.produto = produto;
    }

    public void removerProduto(){
        this.produto = null;
    }
}
