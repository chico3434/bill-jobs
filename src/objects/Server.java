package objects;

import exceptions.CrowdedServer;

import java.util.ArrayList;
import java.util.List;

public class Server extends Infrastructure {

    private int capacity;
    List<Software> softwares;

    public Server(String id, double price, int capacity) {
        super(id, price);
        this.capacity = capacity;
        softwares = new ArrayList<>();
    }

    public void hostSoftware(Software software) throws CrowdedServer {
        if (softwares.size() < capacity){
            softwares.add(software);
        } else {
            throw new CrowdedServer("Server Lotado");
        }
    }

    public void hostSoftwareInAll(Software software) {
        softwares.clear();
        for (int i = 0; i < capacity; i++){
            softwares.add(software);
        }
    }

    public void exchangeSoftware(Software s, Software s1) {
        softwares.remove(softwares.indexOf(s1));
        softwares.add(s);
    }

    @Override
    public String toString() {
        return "Server{" +
                "capacity=" + capacity +
                ", softwares=" + softwares +
                '}';
    }

    public String getType(){
        return "Server";
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Software> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<Software> softwares) {
        this.softwares = softwares;
    }
}
