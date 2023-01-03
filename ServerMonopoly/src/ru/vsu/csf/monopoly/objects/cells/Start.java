package ru.vsu.csf.monopoly.objects.cells;

import ru.vsu.csf.monopoly.clients.CurrentGameState;
import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.game.Steps;
import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;import ru.vsu.csf.monopoly.game.Runnable;

public class Start extends GraphicCell implements CellActions{

    public Start(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public void makeMove(Player player, Game game) throws IOException {
        player.setCash(player.getCash() + 1000);
        CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                "Вы получаете 1000 за попадание на Старт", Steps.DRAW_STRING);
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
