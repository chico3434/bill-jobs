package objeto;

public class Software extends Produto {

    public Software(String nome, String versao, String descricao, double custo, double preco) {
        super(nome, versao, descricao, custo, preco);
    }

    public Software(String nome, String versao) {
        super(nome, versao);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Software) || o == null) return false;
        Software software = (Software) o;
        return getId().equals(software.getId());
    }
}
