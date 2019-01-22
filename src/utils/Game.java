package utils;

import com.google.gson.Gson;
import exceptions.MissingMoney;
import exceptions.ExistingProduct;
import exceptions.MissingFunds;
import objects.*;

import java.io.*;
import java.lang.reflect.Type;

public class Game {
	
	// precisa estar dentro de businessman para ser salvo pelo gson
	// Pode ser um dado interessante para balancear melhor o jogo no futuro
    private static int time = 0;

    private static String monthlyReport;
    
    private static Businessman businessman;

    public static void loadBusinessman(String name) throws FileNotFoundException{
        Gson gson = new Gson();

        BufferedReader br;
        if (System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Darwin")) {
             br = new BufferedReader(new FileReader("saves/" + name + ".json"));
        } else {
             br = new BufferedReader(new FileReader("saves\\" + name + ".json"));
        }

        businessman = gson.fromJson(br, (Type) Businessman.class);

    }

    public static void save(){
        Gson gson = new Gson();
        String json = gson.toJson(businessman);

        try {
            // Criar pasta
            if (System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Darwin")) {
                String path = System.getProperty("user.dir");
                System.out.println(path);
                File file = new File(path + "/saves");
                file.mkdir();


                FileWriter writer = new FileWriter("saves/" + businessman.getName() + ".json");
                writer.write(json);
                writer.close();
            } else {
                File file = new File("saves");
                file.mkdir();


                FileWriter writer = new FileWriter("saves\\" + businessman.getName() + ".json");
                writer.write(json);
                writer.close();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void createBusinessman(String name){
        businessman = new Businessman(name);
    }

    public static void createCompany(String name, double funds) throws MissingMoney {
        businessman.setCompany(new Company(name));
        businessman.transferMoney(funds);
    }

    public static Businessman getBusinessman() {
        return businessman;
    }

    public static void setBusinessman(Businessman businessman) {
        Game.businessman = businessman;
    }

    public static String getMoney(){
        return ("R$ " + String.format("%.2f", businessman.getMoney()));
    }

    public static String getFunds(){
        return ("R$ " + String.format("%.2f", businessman.getCompany().getFunds()));
    }

    public static void buyServer(double price, int capacity) throws MissingFunds {
        businessman.getCompany().buyServer(price, capacity);
    }

    public static void buyFactory(double price, int potential, int level) throws MissingFunds {
        businessman.getCompany().buyFactory(price, potential, level);
    }

    public static void buyShed(double price, int capacity) throws MissingFunds {
        businessman.getCompany().buyShed(price, capacity);
    }

    public static void createSoftware(String name, String version, String description, double cost, double price) throws MissingFunds, ExistingProduct {
        businessman.getCompany().createSoftware(name, version, description, cost, price);
    }

    public static void createHardware(String name, String version, String description, double cost, double price) throws ExistingProduct {
        businessman.getCompany().createHardware(name, version, description, cost, price);
    }

    public static void updateProduct(Product p, String description, double cost, double price) throws MissingFunds {
        businessman.getCompany().updateProduct(p, description, cost, price);
    }

    public static void updateSoftware(Product p, String version, String description, double cost, double price) throws MissingFunds {
        businessman.getCompany().updateSoftware(p, version, description, cost, price);
    }
    
    // Host Software in a Server Slot
    public static void hostSoftware(Server server, String softwareId) throws Exception {
        if (businessman.getCompany().getSoftwares().contains(new Software(softwareId, ""))) {
            Software s = businessman.getCompany().getSoftwares().get(businessman.getCompany().getSoftwares().indexOf(new Software(softwareId, "")));
            server.hostSoftware(s);
        } else {
            throw new ExistingProduct("Software não existe!");
        }
    }

    //Host Software in all Server Slots
    public static void hostSoftwareInAll(Server server, String softwareId) throws Exception {
        if (businessman.getCompany().getSoftwares().contains(new Software(softwareId, ""))) {
        	Software s = businessman.getCompany().getSoftwares().get(businessman.getCompany().getSoftwares().indexOf(new Software(softwareId, "")));
            server.hostSoftwareInAll(s);
        } else {
            throw new ExistingProduct("Software não existe!");
        }
    }
    
    // Exchange a software for another in a Server Slot
    public static void exchangeSoftware(Server server, String softwareId, Software software) throws Exception {
        if (businessman.getCompany().getSoftwares().contains(new Software(softwareId, ""))) {
            Software s = businessman.getCompany().getSoftwares().get(businessman.getCompany().getSoftwares().indexOf(new Software(softwareId, "")));
            server.exchangeSoftware(s, software);
        } else {
            throw new ExistingProduct("Software não existe!");
        }
    }

    //Allocate hardware in a Factory so it can be produced over time
    public static void allocateHardwareInFactory(Factory factory, String hardwareId) throws Exception {
        if (businessman.getCompany().getHardwares().contains(new Hardware(hardwareId, ""))){
            Hardware h = businessman.getCompany().getHardwares().get(businessman.getCompany().getHardwares().indexOf(new Hardware(hardwareId, "")));
            factory.setProduct(h);
        } else {
            throw new ExistingProduct("Hardware não existe!");
        }
    }

    public static void spendTime(){
        monthlyReport = "";
        time++;

        // Buy Softwares
        businessman.getCompany().buySoftwares();

        // Manufacture and stock hardware
        businessman.getCompany().manufactureHardware();
        
        // Buy Hardwares
        businessman.getCompany().buyHardware();
        
        // Tempo precisa estar na classe Businessman (para poder ser salvo) para poder ser mostrado na windows
        System.out.println("Tempo: " + time);
    }
    
    // Método criado para que outras classes possam adicionar informações no relatório mensal
    public static void addData(String str){
        monthlyReport += str + "\n";
    }
    
    public static String getReport(){
        return monthlyReport;
    }

    public static int getTime(){
        return time;
    }
}
