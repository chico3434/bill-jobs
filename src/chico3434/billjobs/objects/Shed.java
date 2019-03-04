package chico3434.billjobs.objects;

import chico3434.billjobs.exceptions.CrowdedShed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shed extends Infrastructure {

    private int capacity;
    private List<Hardware> hardwares;
    private List<Integer> quantity;

    Shed(String id, double price, int capacity) {
        super(id, price);
        this.capacity = capacity;
        hardwares = new ArrayList<>();
        quantity = new ArrayList<>();
    }
    int storeHardware(Hardware hardware, int quantity) throws CrowdedShed {
        int q = 0;
        int stock;
        int returned = 0;
        for (Integer integer : this.quantity) {
            q += integer;
        }
        if (q == capacity){
            throw new CrowdedShed("Galpão Lotado");
        } else if (q + quantity > capacity) {
            stock = capacity - q;
            returned = quantity - stock;
        } else {
            stock = quantity;
        }
        if (hardwares.contains(hardware)){
            int index = hardwares.indexOf(hardware);
            this.quantity.add(index, (this.quantity.get(index) + stock));
            this.quantity.remove(index + 1);
        } else {
            hardwares.add(hardware);
            this.quantity.add(quantity);
        }
        return returned;
    }

    public void removeHardware(int i){
        hardwares.remove(i);
        quantity.remove(i);
    }

    public void removeAll(){
        hardwares.clear();
        quantity.clear();
    }

    boolean isCrowded(){
        Iterator<Integer> it = quantity.iterator();
        int quantity = 0;
        while(it.hasNext()){
            quantity += it.next();
        }
        return (capacity == quantity);
    }

    public String getType(){
        return "Galpão";
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Hardware> getHardwares() {
        return hardwares;
    }

    public void setHardwares(List<Hardware> hardwares) {
        this.hardwares = hardwares;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }
}
