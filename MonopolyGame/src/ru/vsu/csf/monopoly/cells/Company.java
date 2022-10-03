package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Company extends Cell {

    private String name;
    private int purchasePrice, countOfBuildings, supplyPrice, countToBuy;
    private boolean isBought = false;
    private CompanyType type;


    public enum CompanyType{
        WEAR,
        SPORT_WEAR,
        SOCIAL_NETWORK,
        AUTO,
        GAME,
        DRINKS,
        AIRLINES,
        FAST_FOOD,
        HOTEL,
        TECHNO;
    }


    public Company(String name, int purchasePrice, int countOfBuildings, int supplyPrice, CompanyType type, int countToBuy) {
        super(new Coord(0,0), Type.COMPANY, 30);
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.countOfBuildings = countOfBuildings;
        this.supplyPrice = supplyPrice;
        this.type = type;
        this.countToBuy = countToBuy;
    }

    public int getCountOfBuildings() {
        return countOfBuildings;
    }

    public boolean buildCompany(Player player){
        if(player.haveAllCompanies(this)) {
            this.countOfBuildings++;
            this.supplyPrice *= 4;
            player.setCash(player.getCash() - 1500);
            return true;
        }
        return false;
    }

    public void makeMove(Player player){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вы попали на компанию " + getName());
        if(isBought() && !player.getMyCompanies().contains(this)){
            System.out.println("С вас списалось " + getSupplyPrice() + " за посещение");
            player.payForVisit();
            System.out.println("Ваш текущий баланс: " + player.getCash());
        } else{
            System.out.println("Введите: ");
            System.out.println("1 - купить компанию за " + getPurchasePrice());
            System.out.println("2 - не покупать");
            int n = scanner.nextInt();
            while (n != 1 && n != 2) {
                System.out.println("Введенная вами команда неверная, повторите попытку");
                n = scanner.nextInt();
            }
            if (n == 1 && player.getCash() >= purchasePrice) {
                player.buyCompany();
                System.out.println("Ваш бюджет: " + player.getCash());
            }
            if(n == 1 && player.getCash() < purchasePrice){
                System.out.println("Вам не хватает средств для покупки");
            }
        }
    }

    public int getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(int supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountOfBuildings(int countOfBuildings) {
        this.countOfBuildings = countOfBuildings;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public CompanyType getCompanyType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }

    public int getCountToBuy() {
        return countToBuy;
    }

    public void setCountToBuy(int countToBuy) {
        this.countToBuy = countToBuy;
    }

}
