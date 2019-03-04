package chico3434.billjobs.objects;

import chico3434.billjobs.exceptions.CrowdedShed;
import chico3434.billjobs.exceptions.ExistingProduct;
import chico3434.billjobs.exceptions.MissingFunds;
import chico3434.billjobs.utils.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Company {

    private String name;
    private double funds;
    private List<Server> servers;
    private List<Factory> factories;
    private List<Shed> shedList;
    private List<Software> softwareList;
    private List<Hardware> hardwareList;

    public Company(String name) {
        this.name = name;
        servers = new ArrayList<>();
        factories = new ArrayList<>();
        shedList = new ArrayList<>();
        softwareList = new ArrayList<>();
        hardwareList = new ArrayList<>();
    }

    public void sellServer(double price, int capacity) throws MissingFunds {
        if (funds >= price) {
            String id = "Server" + (servers.size() + 1);
            funds -= price;
            servers.add(new Server(id, price, capacity));
        } else {
            throw new MissingFunds("Verba Insuficiente");
        }
    }

    public void sellFactory(double price, int potential, int level) throws MissingFunds {
        if (funds >= price) {
            String id = "Fábrica" + (factories.size() + 1);
            funds -= price;
            factories.add(new Factory(id, price, potential, level));
        } else {
            throw new MissingFunds("Verba Insuficiente");
        }
    }

    public void sellShed(double price, int capacity) throws MissingFunds {
        if (funds >= price) {
            String id = "Galpão" + (shedList.size() + 1);
            funds -= price;
            shedList.add(new Shed(id, price, capacity));
        } else {
            throw new MissingFunds("Verba Insuficiente");
        }
    }

    public void createSoftware(String name, String version, String description, double cost, double price) throws MissingFunds, ExistingProduct {
        if (funds < cost){
            throw new MissingFunds("Verba Insuficiente");
        } else if (softwareList.contains(new Software(name, version))) {
            throw new ExistingProduct("Product já existe!");
        } else {
            funds -= cost;
            softwareList.add(new Software(name, version, description, cost, price));
        }
    }

    public void createHardware(String name, String version, String description, double cost, double price) throws ExistingProduct {
        if (hardwareList.contains(new Hardware(name, version))){
            throw new ExistingProduct("Product já existe!");
        } else {
            hardwareList.add(new Hardware(name, version, description, cost, price));
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

    public void sellSoftware() {
        Iterator<Server> it = servers.iterator();
        Random random = new Random();
        while (it.hasNext()){
            Server server = it.next();
            for (Software software : server.getSoftwareList()) {
                double cost = software.getCost();
                double price = software.getPrice();
                double diff = price - cost;
                int total;
                if (diff < 0) {
                    int d = (int) (cost - price);
                    d /= 100;
                    total = 1 + random.nextInt(d);
                } else {
                    double c;
                    if (price > 100) {
                        total = 0;
                    } else {
                        c = 30 / diff;
                        total = random.nextInt((int) c);
                    }
                }
                funds += price * total;
                Game.addData(software.getId() + " em " + server.getId() + " vendas: " + total + " renda: " + (price * total));
            }
        }
    }

    public void manufactureHardware() {
        for (Factory factory : factories) {
            Hardware product = factory.getProduct();

            if (product == null) {
                continue;
            }

            int quantity = factory.manufacture();
            if (funds < (quantity * product.getCost())) {
                quantity = factory.manufacture(2);
                if (funds < (quantity * product.getCost())) {
                    quantity = factory.manufacture(1);
                    if (funds < (quantity * product.getCost())) {
                        Game.addData("Não foi possível manufacture nenhuma unidade\nde " + product.getId() + " por falta de funds!");
                        continue;
                    }
                }
            }

            Game.addData(quantity + " unidades de " + product.getId() + " produzidas!");
            for (Shed shed : shedList) {
                try {
                    int over = shed.storeHardware(product, quantity);
                    Game.addData((quantity - over) + " unidades de " + product.getId() + " estocadas em " + shed.getId() + "!");
                    if (over == 0) {
                        break;
                    } else {
                        quantity = over;
                    }
                } catch (CrowdedShed ignored) {

                }
            }
            if (shedList.get(shedList.size() - 1).isCrowded()) {
                Game.addData("\nTodos os galpões lotaram\nAlguns produtos podem não ter sido produzidos\n");
                break;
            }
        }
    }

    public void sellHardware() {
        for (Shed shed : shedList) {
            List<Hardware> hardwareList = shed.getHardwares();
            List<Integer> quantity = shed.getQuantity();

            for (int i = 0; i < hardwareList.size(); i++) {
                Hardware hard = hardwareList.get(i);
                double aux = hard.getPrice() / hard.getCost();
                double percent;

                Random random = new Random();

                if (aux <= 3) {
                    percent = 1 + random.nextInt(4);
                } else if (aux < 6) {
                    percent = random.nextInt(3);
                } else if (aux <= 10) {
                    percent = random.nextInt(2);
                } else {
                    percent = 0;
                }

                int totalSold = (int) ((quantity.get(i) / 4) * percent);

                Game.addData(totalSold + " vendidos de " + hard.getId() + " de " + shed.getId() + ".");

                if (totalSold == quantity.get(i)) {
                    quantity.remove(i);
                    hardwareList.remove(i);
                } else {
                    quantity.add(i, (quantity.get(i) - totalSold));
                    quantity.remove(i + 1);
                }

                funds += (totalSold * hard.getPrice());

            }
        }
    }

    public void removeSoftware(Software software){
        softwareList.remove(softwareList.get(softwareList.indexOf(software)));
        for(Server se: servers){
            while (se.getSoftwareList().contains(software)){
                se.getSoftwareList().remove(software);
            }
        }
    }

    public void removeHardware(Hardware hardware) {
        hardwareList.remove(hardware);
        for (Factory factory : factories)
            if (factory.getProduct().equals(hardware)) factory.removeProduct();

        for (Shed shed : shedList) {
            List<Hardware> hs = shed.getHardwares();
            if (hs.contains(hardware)) {
                int index = hs.indexOf(hardware);
                shed.getHardwares().remove(index);
                shed.getQuantity().remove(index);
            }
        }
    }

    public void sellServer(Server server) {
        funds += server.getPrice()/2;
        servers.remove(server);
    }

    public void sellFactory(Factory factory) {
        funds += factory.getPrice()/2;
        factories.remove(factory);
    }

    public void sellShed(Shed shed) {
        funds += shed.getPrice()/2;
        shedList.remove(shed);
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

    public List<Shed> getShedList() {
        return shedList;
    }

    public void setShedList(List<Shed> shedList) {
        this.shedList = shedList;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

	public List<Hardware> getHardwareList() {
		return hardwareList;
	}

	public void setHardwareList(List<Hardware> hardwareList) {
		this.hardwareList = hardwareList;
	}

	public List<Software> getSoftwareList() {
		return softwareList;
	}

	public void setSoftwareList(List<Software> softwareList) {
		this.softwareList = softwareList;
	}
}
