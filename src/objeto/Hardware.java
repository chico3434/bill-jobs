package objeto;

public class Hardware extends Produto {

    public Hardware(String nome, String versao, String descricao, double custo, double preco) {
        super(nome, versao, descricao, custo, preco);
    }

    public Hardware(String nome, String versao) {
        super(nome, versao);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hardware) || o == null) return false;
        Hardware hardware = (Hardware) o;
        return getId().equals(hardware.getId());
    }
}
