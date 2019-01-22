package chico3434.billjobs.objects;

public class Software extends Product {

    public Software(String name, String version, String description, double cost, double price) {
        super(name, version, description, cost, price);
    }

    public Software(String name, String version) {
        super(name, version);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Software) || o == null) return false;
        Software software = (Software) o;
        return getId().equals(software.getId());
    }
}
