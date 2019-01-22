package objects;

public class Hardware extends Product {

    public Hardware(String name, String version, String description, double cost, double price) {
        super(name, version, description, cost, price);
    }

    public Hardware(String name, String version) {
        super(name, version);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hardware) || o == null) return false;
        Hardware hardware = (Hardware) o;
        return getId().equals(hardware.getId());
    }
}
