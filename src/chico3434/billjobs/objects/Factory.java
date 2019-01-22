package chico3434.billjobs.objects;

import chico3434.billjobs.exceptions.LowLevel;

import java.util.Random;

public class Factory extends Infrastructure {

    private int level;
    private int potential;
    private Hardware product;

    public Factory(String id, double preco, int potential, int level) {
        super(id, preco);
        this.potential = potential;
        this.level = level;
    }

    public int manufacture(){
        Random random = new Random();

        int perc = 1 + random.nextInt(4);

        return (potential /4) * perc;
    }

    public int manufacture(int i){
        return (potential /4)*i;
    }

    public String getType(){
        return "Fábrica";
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public Hardware getProduct() {
        return product;
    }

    public void setProduct(Hardware product) throws LowLevel {
        if ((product.getCost()/100) > level){
            throw new LowLevel("Nível da fábrica inferior ao do product");
        }
        this.product = product;
    }

    public void removeProduct(){
        this.product = null;
    }
}
