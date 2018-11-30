package objeto;

import exceptions.DinheiroInsuficiente;
import utils.Utils;

public class Empresario {

    private String nome;
    private double dinheiro;
    private Empresa empresa;

    public Empresario(String nome) {
        this.nome = nome;
        dinheiro = Utils.DINHEIRO_INICIAL;
    }

    public void tranferirDinheiro(double verba) throws DinheiroInsuficiente {
        if (dinheiro >= verba) {
            empresa.setVerba(verba);
            dinheiro -= verba;
        } else {
            throw new DinheiroInsuficiente("Dinheiro insuficiente");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(double dinheiro) {
        this.dinheiro = dinheiro;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
