package objeto;

public class Hardware extends Produto {

    public Hardware(String nome, String versao, String descricao, double custo, double preco) {
        super(nome, versao, descricao, custo, preco);
    }

    public Hardware(String nome, String versao) {
        super(nome, versao);
    }
}
