package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.game.Game;

import ru.vsu.csf.monopoly.game.Runnable;
import ru.vsu.csf.monopoly.graphics.DrawUtils;
import ru.vsu.csf.monopoly.objects.Player;

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
        if (isBought() && !player.getMyCompanies().contains(this)) {
            for(Player player1 : game.getPlayers()){
                if(player1.getMyCompanies().contains(this)){
                    player1.setCash(player1.getCash() + getSupplyPrice());
                }
            }
            player.payForVisit();
            game.getRunnable().render(null, Runnable.Steps.DRAW_STRING, "Вы попали на компанию " + getName() + ", c вас списалось " + getSupplyPrice() + " за посещение");
        }else if(!isBought()){
            if(player.getCash() >= this.purchasePrice) {
                game.getRunnable().render(null, Runnable.Steps.CHOOSE_COMPANY_COMMAND, null);
            } else{
                game.getRunnable().render(null, Runnable.Steps.DRAW_STRING, "У вас недостаточно средств для покупки компании");
            }
        } else{
            game.getRunnable().render(null, Runnable.Steps.DRAW_STRING, "Вы попали на свою компанию");
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int price;
        if(isBought()){
            price = getSupplyPrice();
        } else{
            price = getPurchasePrice();
        }
        DrawUtils.drawCell(g, x, y, sizeX, sizeY, color, inscription, price, getCompanyType(), getCountOfBuildings());
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
