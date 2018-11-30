package objeto;

import exceptions.GalpaoLotado;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Galpao extends Infraestrutura{

    private int capacidade;
    private List<Hardware> hardwares;
    private List<Integer> quantidade;

    public Galpao(String id, double preco, int capacidade) {
        super(id, preco);
        this.capacidade = capacidade;
        hardwares = new ArrayList<>();
        quantidade = new ArrayList<>();
    }
    public int estocarHardware(Hardware hardware, int quantidade) throws GalpaoLotado {
        int q = 0;
        for (int i = 0; i < this.quantidade.size(); i++){
            q += this.quantidade.get(i);
        }
        if (q == capacidade){
            throw new GalpaoLotado("Galpão Lotado");
        } else if (q + quantidade > capacidade) {
            int x = capacidade - q;
            if (hardwares.contains(hardware)){
                int indice = hardwares.indexOf(hardware);
                this.quantidade.add(indice, (this.quantidade.get(indice) + x));
                this.quantidade.remove(indice + 1);
            } else {
                hardwares.add(hardware);
                this.quantidade.add(x);
            }
            return quantidade - x;
        } else {
            if (hardwares.contains(hardware)){
                int indice = hardwares.indexOf(hardware);
                this.quantidade.add(indice, (this.quantidade.get(indice) + quantidade));
                this.quantidade.remove(indice + 1);
            } else {
                hardwares.add(hardware);
                this.quantidade.add(quantidade);
            }
        }
        return 0;
    }

    public void removerHardware(int i){
        hardwares.remove(i);
        quantidade.remove(i);
    }

    public void removerTudo(){
        hardwares.clear();
        quantidade.clear();
    }

    public boolean isLotado(){
        Iterator<Integer> it = quantidade.iterator();
        int quant = 0;
        while(it.hasNext()){
            quant += it.next();
        }
        return (capacidade == quant);
    }

    public String getTipo(){
        return "Galpão";
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<Hardware> getHardwares() {
        return hardwares;
    }

    public void setHardwares(List<Hardware> hardwares) {
        this.hardwares = hardwares;
    }

    public List<Integer> getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(List<Integer> quantidade) {
        this.quantidade = quantidade;
    }
}
