package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.game.Game;

import ru.vsu.csf.monopoly.game.GraphicGame;
import ru.vsu.csf.monopoly.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chance extends Cell implements CellActions{

    private Random rnd = new Random();
    private Actions action;

    public Chance(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
        this.action = Actions.values()[rnd.nextInt(Actions.values().length)];
    }

    public enum Actions{
        GET_1000,
        GET_1500,
        PAY_1000,
        PAY_2000,
        GO_TO_PRISON;
    }

    public String toString(Actions a, Player player){
        if(a == Actions.GET_1000){
            player.setCash(player.getCash()+1000);
            return "Пользователь получает 1000";
        }
        if(a == Actions.GET_1500){
            player.setCash(player.getCash()+1500);
            return "Пользователь получает 1500";
        }
        if(a == Actions.PAY_1000){
            player.setCash(player.getCash()-1000);
            return "Пользователь должен заплатить 1000";
        }if(a == Actions.PAY_2000){
            player.setCash(player.getCash()-2000);
            return "Пользователь должен заплатить налог 2000";
        }
        if(a == Actions.GO_TO_PRISON){
            Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            List<Player> p = currentCell.getPlayers();
            p.remove(player);
            currentCell.setPlayers(p);

            player.setCurrentPosition(10);
            player.setPrisonForVisit(false);

            currentCell = player.getPlayingField().getCells().get(10);
            p = currentCell.getPlayers();
            p.add(player);
            currentCell.setPlayers(p);
            return "Нарушил закон и отправляешься в тюрьму";
        }
        return "";
    }

    public void makeMove(Player player, Game game){
        //game.getG().printStr("Вы попали на поле шанс");
        //game.getG().printStr(toString(getAction(), player));
//        if(getAction() != Chance.Actions.GO_TO_PRISON){
//            toString(getAction(), player);
//            //game.getG().printStr("Ваш бюджет: " + player.getCash());
//        } else{
//            player.setPrisonForVisit(false);
//            player.setCurrentPosition(10);
//        }
        game.getRunnable().render(null, GraphicGame.Steps.DRAW_STRING, game.getField(), toString(action, player));
        this.action = Actions.values()[rnd.nextInt(Actions.values().length)];
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }
}
