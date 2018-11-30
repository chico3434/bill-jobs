package objeto;

public class Infraestrutura {

    private String id;
    private double preco;

    public Infraestrutura(String id, double preco) {
        this.id = id;
        this.preco = preco;
    }

    public Infraestrutura(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Infraestrutura){
            Infraestrutura infraestrutura = (Infraestrutura) o;
            return (id.equals(infraestrutura.id));
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
