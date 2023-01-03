package ru.vsu.csf.monopoly.objects.cells;

import ru.vsu.csf.monopoly.clients.CurrentGameState;
import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.game.Steps;
import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;import ru.vsu.csf.monopoly.game.Runnable;

public class Rialto extends GraphicCell implements CellActions{
    private Random rnd = new Random();

    public Rialto(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public void payToExit(Player player){
        player.setPrisonForVisit(true);
        player.setCash(player.getCash() - 500);
    }

    public int[] rollDice(Player player, Game game) throws IOException {
        rnd = new Random();
        int[] dice = new int[2];
        dice[0] = rnd.nextInt(5) + 1;
        dice[1] = rnd.nextInt(5) + 1;

        if(dice[0] == dice[1]){
            player.setPrisonForVisit(true);
            player.setCountOfThrowsInPrison(0);
            CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                    , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                    "Вы вышли из тюрьмы", Steps.DRAW_STRING);
            game.getActiveWriter().write(curr.toString());
            game.getActiveWriter().newLine();
            game.getActiveWriter().flush();
            CurrentGameState state = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                    , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                    "", Steps.NOTHING);
            game.getPassWriter().write(state.toString());
            game.getPassWriter().newLine();
            game.getPassWriter().flush();
        }else{
            player.setCountOfThrowsInPrison(player.getCountOfThrowsInPrison()+1);
            CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                    , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                    "Вы остаетесь в тюрьме", Steps.DRAW_STRING);
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

        return dice;
    }

    public void makeMove(Player player, Game game) throws IOException {
        if(player.isPrisonForVisit()) {
            CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                    , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                    "Вы попали на биржу", Steps.DRAW_STRING);
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
                    "", Steps.CHOOSE_RIALTO_COMMAND);
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
}
