package objeto;

public class Software extends Produto {

    public Software(String nome, String versao, String descricao, double custo, double preco) {
        super(nome, versao, descricao, custo, preco);
    }

    public Software(String nome, String versao) {
        super(nome, versao);
    }
}
