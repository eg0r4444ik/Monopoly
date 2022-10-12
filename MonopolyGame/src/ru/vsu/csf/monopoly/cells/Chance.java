package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chance extends Cell{

    private Random rnd = new Random();
    private Actions action;

    public Chance() {
        super(new Coord(0,0), 30);
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
            player.setCurrentPosition(10);
            player.setPrisonForVisit(false);
            return "Нарушил закон и отправляешься в тюрьму";
        }
        return "";
    }

    public void makeMove(Player player, Game game){
        game.printStr("Вы попали на поле шанс");
        game.printStr(toString(getAction(), player));
        if(getAction() != Chance.Actions.GO_TO_PRISON){
            game.printStr("Ваш бюджет: " + player.getCash());
        } else{
            player.setPrisonForVisit(false);
            player.setCurrentPosition(10);
        }
        this.action = Actions.values()[rnd.nextInt(Actions.values().length)];
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }
}
