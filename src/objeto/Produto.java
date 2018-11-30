package objeto;

public class Produto {

    private String nome;
    private String versao;
    private String descricao;
    private double preco;
    private double custo;

    public Produto(String nome, String versao, String descricao, double custo, double preco) {
        this.nome = nome;
        this.versao = versao;
        this.descricao = descricao;
        this.custo = custo;
        this.preco = preco;
    }

    public Produto(String nome, String versao) {
        this.nome = nome;
        this.versao = versao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto) || o == null) return false;
        Produto produto = (Produto) o;
        return getId().equals(produto.getId());
    }

    // Id de Produto é a concatenação de nome com versão
    public String getId(){
        return (nome + versao);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }
}
