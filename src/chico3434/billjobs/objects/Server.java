package chico3434.billjobs.objects;

import chico3434.billjobs.exceptions.CrowdedServer;

import java.util.ArrayList;
import java.util.List;

public class Server extends Infrastructure {

    private int capacity;
    private List<Software> softwareList;

    Server(String id, double price, int capacity) {
        super(id, price);
        this.capacity = capacity;
        softwareList = new ArrayList<>();
    }

    public void hostSoftware(Software software) throws CrowdedServer {
        if (softwareList.size() < capacity){
            softwareList.add(software);
        } else {
            throw new CrowdedServer("Server Lotado");
        }
    }

    public void hostSoftwareInAll(Software software) {
        softwareList.clear();
        for (int i = 0; i < capacity; i++){
            softwareList.add(software);
        }
    }

    public void exchangeSoftware(Software s, Software s1) {
        softwareList.remove(softwareList.get(softwareList.indexOf(s1)));
        softwareList.add(s);
    }

    @Override
    public String toString() {
        return "Server{" +
                "capacity=" + capacity +
                ", softwareList=" + softwareList +
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

    public List<Software> getSoftwareList() {
        return softwareList;
    }

    public void setSoftwareList(List<Software> softwareList) {
        this.softwareList = softwareList;
    }
}
