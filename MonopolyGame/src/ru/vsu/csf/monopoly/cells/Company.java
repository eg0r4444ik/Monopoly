package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;

import ru.vsu.csf.monopoly.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Company extends Cell implements CellActions{

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


    public Company(int x, int y, int sizeX, int sizeY, Color color, String name, int purchasePrice, int countOfBuildings, int supplyPrice, CompanyType type, int countToBuy) {
        super(x, y, sizeX, sizeY, color, name, new ArrayList<>());
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
        game.getG().printStr("Вы попали на компанию " + getName());
        if (isBought() && !player.getMyCompanies().contains(this)) {
            game.getG().printStr("С вас списалось " + getSupplyPrice() + " за посещение");
            player.payForVisit();
            game.getG().printStr("Ваш текущий баланс: " + player.getCash());
        } else {
            int n = game.getG().chooseCompanyCommand(this);

            while (n != 1 && n != 2) {
                game.getG().printStr("Введенная вами команда неверная, повторите попытку");
                n = game.getG().chooseCompanyCommand(this);
            }
            if (n == 1 && player.getCash() >= purchasePrice) {
                player.buyCompany();
                game.getG().printStr("Ваш бюджет: " + player.getCash());
            }
            if (n == 1 && player.getCash() < purchasePrice) {
                game.getG().printStr("Вам не хватает средств для покупки");
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
