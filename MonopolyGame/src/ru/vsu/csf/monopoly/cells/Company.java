package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.game.Game;

import ru.vsu.csf.monopoly.game.GraphicGame;
import ru.vsu.csf.monopoly.game.Runnable;
import ru.vsu.csf.monopoly.player.Player;

import java.awt.*;
import java.util.ArrayList;

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
        if (player.haveAllCompanies(this) && countOfBuildings <= 2) {
            this.countOfBuildings++;
            this.supplyPrice *= 4;
            player.setCash(player.getCash() - 1500);
            return true;
        }
        return false;
    }

    public void makeMove(Player player, Game game) {
        //game.getG().printStr("Вы попали на компанию " + getName());
        if (isBought() && !player.getMyCompanies().contains(this)) {
            //game.getG().printStr("С вас списалось " + getSupplyPrice() + " за посещение");
            player.payForVisit();
            game.getRunnable().render(null, GraphicGame.Steps.DRAW_STRING, game.getField(), "Вы попали на компанию " + getName() + ", c вас списалось " + getSupplyPrice() + " за посещение");
            //game.getG().printStr("Ваш текущий баланс: " + player.getCash());
        }else if(!isBought()){
            if(player.getCash() >= this.purchasePrice) {
                game.getRunnable().render(null, GraphicGame.Steps.CHOOSE_COMPANY_COMMAND, game.getField(), null);
            } else{
                game.getRunnable().render(null, GraphicGame.Steps.DRAW_STRING, game.getField(), "У вас недостаточно средств для покупки компании");
            }
        } else{
            game.getRunnable().render(null, GraphicGame.Steps.DRAW_STRING, game.getField(), "Вы попали на свою компанию");
        }
//        else {
//            //int n = game.getG().chooseCompanyCommand(this);
//            game.getRunnable().render(null, GraphicGame.Steps.CHOOSE_COMPANY_COMMAND, game.getField());
////            while (n != 1 && n != 2) {
////                //game.getG().printStr("Введенная вами команда неверная, повторите попытку");
////                n = game.getG().chooseCompanyCommand(this);
////            }
////            if (n == 1 && player.getCash() >= purchasePrice) {
////                //game.getG().printStr("Ваш бюджет: " + player.getCash());
////            }
////            if (n == 1 && player.getCash() < purchasePrice) {
////                //game.getG().printStr("Вам не хватает средств для покупки");
////            }
//        }
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
