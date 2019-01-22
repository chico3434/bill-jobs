package objects;

import exceptions.CrowdedShed;
import exceptions.ExistingProduct;
import exceptions.MissingFunds;
import utils.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Company {

    private String name;
    private double funds;
    private List<Server> servers;
    private List<Factory> factories;
    private List<Shed> sheds;
    private List<Software> softwares;
    private List<Hardware> hardwares;

    public Company(String name) {
        this.name = name;
        servers = new ArrayList<>();
        factories = new ArrayList<>();
        sheds = new ArrayList<>();
        softwares = new ArrayList<>();
        hardwares = new ArrayList<>();
    }

    public void buyServer(double price, int capacity) throws MissingFunds {
        if (funds >= price) {
            String id = "Server" + (servers.size() + 1);
            funds -= price;
            servers.add(new Server(id, price, capacity));
        } else {
            throw new MissingFunds("Verba Insuficiente");
        }
    }

    public void buyFactory(double price, int potential, int level) throws MissingFunds {
        if (funds >= price) {
            String id = "Fábrica" + (factories.size() + 1);
            funds -= price;
            factories.add(new Factory(id, price, potential, level));
        } else {
            throw new MissingFunds("Verba Insuficiente");
        }
    }

    public void buyShed(double price, int capacity) throws MissingFunds {
        if (funds >= price) {
            String id = "Galpão" + (sheds.size() + 1);
            funds -= price;
            sheds.add(new Shed(id, price, capacity));
        } else {
            throw new MissingFunds("Verba Insuficiente");
        }
    }

    public void createSoftware(String name, String version, String description, double cost, double price) throws MissingFunds, ExistingProduct {
        if (funds < cost){
            throw new MissingFunds("Verba Insuficiente");
        } else if (softwares.contains(new Software(name, version))) {
            throw new ExistingProduct("Product já existe!");
        } else {
            funds -= cost;
            softwares.add(new Software(name, version, description, cost, price));
        }
    }

    public void createHardware(String name, String version, String description, double cost, double price) throws ExistingProduct {
        if (hardwares.contains(new Hardware(name, version))){
            throw new ExistingProduct("Product já existe!");
        } else {
            hardwares.add(new Hardware(name, version, description, cost, price));
        }
    }

    public void updateProduct(Product p, String description, double cost, double price) throws MissingFunds {
        if (p instanceof Software){
            if (funds < cost){
                throw new MissingFunds("Verba Insuficiente");
            }
            p.setCost(p.getCost() + cost);
            funds -= cost;
        } else {
            p.setCost(cost);
        }
        p.setDescription(description);
        p.setPrice(price);
    }

    public void updateSoftware(Product p, String version, String description, double cost, double price) throws MissingFunds {
        if (funds < cost){
            throw new MissingFunds("Verba Insuficiente");
        }
        funds -= cost;
        p.setVersion(version);
        p.setDescription(description);
        p.setCost(p.getCost() + cost);
        p.setPrice(price);
    }

    public void buySoftwares(){
        Iterator<Server> it = servers.iterator();
        Random random = new Random();
        while (it.hasNext()){
            Server s = it.next();
            Iterator<Software> i = s.getSoftwares().iterator();
            while(i.hasNext()){
                Software software = i.next();
                double cost = software.getCost();
                double price = software.getPrice();
                double diff = price - cost;
                int total;
                if (diff <0){
                    int d = (int) (cost - price);
                    d /= 100;
                    total = 1 + random.nextInt(d);
                } else {
                    double c;
                    if (price > 100){
                        total = 0;
                    } else {
                        c = 30/ diff;
                        total = random.nextInt((int) c);
                    }
                }
                funds += price * total;
                Game.addData(software.getId() + " em " + s.getId() + " vendas: " + total + " renda: " + (price * total));
            }
        }
    }

    public void manufactureHardware() {
        Iterator<Factory> it = factories.iterator();
        while (it.hasNext()){
            Factory f = it.next();
            Hardware product = f.getProduct();

            if (product == null){
                continue;
            }

            int quantity = f.manufacture();
            if (funds < (quantity * product.getCost())){
                quantity = f.manufacture(2);
                if (funds < (quantity * product.getCost())){
                    quantity = f.manufacture(1);
                    if (funds < (quantity * product.getCost())){
                        Game.addData("Não foi possível manufacture nenhuma unidade\nde " + product.getId() + " por falta de funds!");
                        continue;
                    }
                }
            }

            Game.addData(quantity + " unidades de " + product.getId() + " produzidas!");
            for (int i = 0; i < sheds.size(); i++){
                try {
                    int falhas = sheds.get(i).storeHardware(product, quantity);
                    Game.addData((quantity - falhas) + " unidades de " + product.getId() + " estocadas em " + sheds.get(i).getId() + "!");
                    if (falhas == 0){
                        break;
                    } else {
                        quantity = falhas;
                    }
                } catch (CrowdedShed galpaoLotado) {
                    continue;
                }
            }
            if (sheds.get(sheds.size()-1).isCrowded()){
                Game.addData("\nTodos os galpões lotaram\nAlguns produtos podem não ter sido produzidos\n");
                break;
            }
        }
    }

    public void buyHardware(){
        Iterator<Shed> it = sheds.iterator();

        while(it.hasNext()){
            Shed shed = it.next();
            List<Hardware> hardwares = shed.getHardwares();
            List<Integer> quantity = shed.getQuantity();

            for (int i = 0; i < hardwares.size(); i++){
                Hardware hard = hardwares.get(i);
                double aux = hard.getPrice()/hard.getCost();
                double perc;

                Random rand = new Random();

                if (aux <= 3){
                    perc = 1 + rand.nextInt(4);
                } else  if (aux < 6){
                    perc = rand.nextInt(3);
                } else if (aux <= 10){
                    perc = rand.nextInt(2);
                } else {
                    perc = 0;
                }

                int totalSold = (int)((quantity.get(i)/4) * perc);

                Game.addData(totalSold + " vendidos de " + hard.getId() + " de " + shed.getId() + ".");

                if (totalSold == quantity.get(i)){
                    quantity.remove(i);
                    hardwares.remove(i);
                } else {
                    quantity.add(i, (quantity.get(i) - totalSold));
                    quantity.remove(i+1);
                }

                funds += (totalSold * hard.getPrice());

            }
        }
    }

    public void removeSoftware(Software s){
        softwares.remove(softwares.indexOf(s));
        for(Server se: servers){
            while (se.getSoftwares().contains(s)){
                se.getSoftwares().remove(s);
            }
        }
    }

    public void removeHardware(Hardware h){
        hardwares.remove(h);
        for (Factory f : factories){
            if (f.getProduct().equals(h)){
                f.removeProduct();
            }
        }

        for (Shed g : sheds){
            List<Hardware> hs = g.getHardwares();
            if (hs.contains(h)) {
                int index = hs.indexOf(h);
                g.getHardwares().remove(index);
                g.getQuantity().remove(index);
            }
        }
    }

    public void buyServer(Server s){
        funds += s.getPrice()/2;
        servers.remove(s);
    }

    public void buyFactory(Factory f){
        funds += f.getPrice()/2;
        factories.remove(f);
    }

    public void buyShed(Shed g){
        funds += g.getPrice()/2;
        sheds.remove(g);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public void setFactories(List<Factory> factories) {
        this.factories = factories;
    }

    public List<Shed> getSheds() {
        return sheds;
    }

    public void setSheds(List<Shed> sheds) {
        this.sheds = sheds;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

	public List<Hardware> getHardwares() {
		return hardwares;
	}

	public void setHardwares(List<Hardware> hardwares) {
		this.hardwares = hardwares;
	}

	public List<Software> getSoftwares() {
		return softwares;
	}

	public void setSoftwares(List<Software> softwares) {
		this.softwares = softwares;
	}
}
