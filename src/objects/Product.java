package objects;

public class Product {

    private String name;
    private String version;
    private String description;
    private double price;
    private double cost;

    public Product(String name, String version, String description, double cost, double price) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.cost = cost;
        this.price = price;
    }

    public Product(String name, String version) {
        this.name = name;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product) || o == null) return false;
        Product product = (Product) o;
        return getId().equals(product.getId());
    }

    public String getId(){
        return (name + version);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
