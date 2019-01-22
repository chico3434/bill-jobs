package chico3434.billjobs.objects;

public class Infrastructure {

    private String id;
    private double price;

    public Infrastructure(String id, double price) {
        this.id = id;
        this.price = price;
    }

    public Infrastructure(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Infrastructure){
            Infrastructure infrastructure = (Infrastructure) o;
            return (id.equals(infrastructure.id));
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
