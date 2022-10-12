package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Company extends Cell {

    private String name;
    private int purchasePrice, countOfBuildings, supplyPrice, countToBuy;
    private boolean isBought = false;
    private CompanyType type;


    public enum CompanyType {
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
        super(new Coord(0, 0), 30);
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

    public boolean buildCompany(Player player) {
        if (player.haveAllCompanies(this)) {
            this.countOfBuildings++;
            this.supplyPrice *= 4;
            player.setCash(player.getCash() - 1500);
            return true;
        }
        return false;
    }

    public void makeMove(Player player, Game game) {
        game.printStr("Вы попали на компанию " + getName());
        if (isBought() && !player.getMyCompanies().contains(this)) {
            game.printStr("С вас списалось " + getSupplyPrice() + " за посещение");
            player.payForVisit();
            game.printStr("Ваш текущий баланс: " + player.getCash());
        } else {
            int n = 0;
            if(game.isTextGame()){
                n = game.getTxt().chooseCompanyCommand(this);
            }
            while (n != 1 && n != 2) {
                game.printStr("Введенная вами команда неверная, повторите попытку");
                n = game.getTxt().chooseCompanyCommand(this);
            }
            if (n == 1 && player.getCash() >= purchasePrice) {
                player.buyCompany();
                game.printStr("Ваш бюджет: " + player.getCash());
            }
            if (n == 1 && player.getCash() < purchasePrice) {
                game.printStr("Вам не хватает средств для покупки");
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
