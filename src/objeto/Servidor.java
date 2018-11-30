package objeto;

import exceptions.ServidorLotado;

import java.util.ArrayList;
import java.util.List;

public class Servidor extends Infraestrutura{

    private int capacidade;
    List<Software> softwares;

    public Servidor(String id, double preco, int capacidade) {
        super(id, preco);
        this.capacidade = capacidade;
        softwares = new ArrayList<Software>();
    }

    public void hospedarSoftware(Software software) throws ServidorLotado{
        if (softwares.size() < capacidade){
            softwares.add(software);
        } else {
            throw new ServidorLotado("Servidor Lotado");
        }
    }

    public void hospedarSoftwareEmTodo(Software software) {
        softwares.clear();
        for (int i = 0; i < capacidade; i++){
            softwares.add(software);
        }
    }

    public void trocarSoftware(Software s, Software s1) {
        softwares.remove(softwares.indexOf(s1));
        softwares.add(s);
    }

    @Override
    public String toString() {
        return "Servidor{" +
                "capacidade=" + capacidade +
                ", softwares=" + softwares +
                '}';
    }

    public String getTipo(){
        return "Servidor";
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<Software> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<Software> softwares) {
        this.softwares = softwares;
    }
}
