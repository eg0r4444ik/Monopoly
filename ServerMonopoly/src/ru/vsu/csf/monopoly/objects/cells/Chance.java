package ru.vsu.csf.monopoly.objects.cells;

import ru.vsu.csf.monopoly.clients.CurrentGameState;
import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.game.Steps;
import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;import ru.vsu.csf.monopoly.game.Runnable;

public class Chance extends GraphicCell implements CellActions{

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

    public void makeMove(Player player, Game game) throws IOException {
        CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                toString(action, player), Steps.DRAW_STRING);
        game.getActiveWriter().write(curr.toString());
        game.getActiveWriter().newLine();
        game.getActiveWriter().flush();
        CurrentGameState state = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                "", Steps.NOTHING);
        game.getPassWriter().write(state.toString());
        game.getPassWriter().newLine();
        game.getPassWriter().flush();
        this.action = Actions.values()[rnd.nextInt(Actions.values().length)];
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }
}
