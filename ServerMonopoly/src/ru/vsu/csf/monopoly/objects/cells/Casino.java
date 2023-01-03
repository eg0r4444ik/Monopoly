package ru.vsu.csf.monopoly.objects.cells;

import ru.vsu.csf.monopoly.clients.CurrentGameState;
import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.game.GraphicGame;
import ru.vsu.csf.monopoly.game.Steps;
import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Casino extends GraphicCell implements CellActions{

    private Random rnd = new Random();

    public Casino(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public int play(int betSum, int bet, Player player, Game game) throws IOException {
        int number = rnd.nextInt(5)+1;
        player.setCash(player.getCash() - betSum);
        if(bet%2 == number%2){
            player.setCash(player.getCash() + betSum*2);
            CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                    , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                    "Вы выиграли", Steps.DRAW_STRING);
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
                    "Вы проиграли", Steps.DRAW_STRING);
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
        return number;
    }


    public void makeMove(Player player, Game game) throws IOException {
        CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                "", Steps.CHOOSE_CASINO_COMMAND);
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
