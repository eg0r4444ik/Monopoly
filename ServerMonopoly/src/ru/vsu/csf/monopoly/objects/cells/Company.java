package ru.vsu.csf.monopoly.objects.cells;

import ru.vsu.csf.monopoly.clients.CurrentGameState;
import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.game.Runnable;
import ru.vsu.csf.monopoly.game.Steps;
import ru.vsu.csf.monopoly.graphics.DrawUtils;
import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Company extends GraphicCell implements CellActions{

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

    public void makeMove(Player player, Game game) throws IOException {
        if (isBought() && !player.getMyCompanies().contains(this)) {
//            for(Player player1 : game.getPlayers()){
//                if(player1.getMyCompanies().contains(this)){
//                    player1.setCash(player1.getCash() + getSupplyPrice());
//                }
//            }
            if(game.getPlayer1().getMyCompanies().contains(this)){
                Player p = game.getPlayer1();
                p.setCash(p.getCash() + getSupplyPrice());
                game.setPlayer1(p);
            }else{
                Player p = game.getPlayer2();
                p.setCash(p.getCash() + getSupplyPrice());
                game.setPlayer2(p);
            }


            player.payForVisit();
            CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                    , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                    "Вы попали на компанию " + getName() + ", c вас списалось " + getSupplyPrice() + " за посещение", Steps.DRAW_STRING);
            game.getActiveWriter().write(curr.toString());
            game.getActiveWriter().newLine();
            game.getActiveWriter().flush();
            CurrentGameState state = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                    , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                    "", Steps.NOTHING);
            game.getPassWriter().write(state.toString());
            game.getPassWriter().newLine();
            game.getPassWriter().flush();
        }else if(!isBought()){
            if(player.getCash() >= this.purchasePrice) {
                CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                        , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                        "", Steps.CHOOSE_COMPANY_COMMAND);
                game.getActiveWriter().write(curr.toString());
                game.getActiveWriter().newLine();
                game.getActiveWriter().flush();
                CurrentGameState state = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                        , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                        "", Steps.NOTHING);
                game.getPassWriter().write(state.toString());
                game.getPassWriter().newLine();
                game.getPassWriter().flush();
            } else{
                CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                        , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                        "У вас недостаточно средств для покупки компании", Steps.DRAW_STRING);
                game.getActiveWriter().write(curr.toString());
                game.getActiveWriter().newLine();
                game.getActiveWriter().flush();
                CurrentGameState state = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                        , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                        "", Steps.NOTHING);
                game.getPassWriter().write(state.toString());
                game.getPassWriter().newLine();
                game.getPassWriter().flush();
            }
        } else{
            CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                    , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                    "Вы попали на свою компанию", Steps.DRAW_STRING);
            game.getActiveWriter().write(curr.toString());
            game.getActiveWriter().newLine();
            game.getActiveWriter().flush();
            CurrentGameState state = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                    , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                    "", Steps.NOTHING);
            game.getPassWriter().write(state.toString());
            game.getPassWriter().newLine();
            game.getPassWriter().flush();
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
