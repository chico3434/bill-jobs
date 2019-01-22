package chico3434.billjobs.objects;

import chico3434.billjobs.exceptions.MissingMoney;
import chico3434.billjobs.utils.Utils;

public class Businessman {

    private String name;
    private double money;
    private Company company;

    public Businessman(String name) {
        this.name = name;
        money = Utils.INITIAL_MONEY;
    }

    public void transferMoney(double funds) throws MissingMoney {
        if (money >= funds) {
            company.setFunds(funds);
            money -= funds;
        } else {
            throw new MissingMoney("Dinheiro insuficiente");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
