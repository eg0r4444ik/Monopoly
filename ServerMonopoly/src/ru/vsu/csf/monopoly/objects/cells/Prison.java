package ru.vsu.csf.monopoly.objects.cells;

import ru.vsu.csf.monopoly.clients.CurrentGameState;
import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.game.Runnable;
import ru.vsu.csf.monopoly.game.Steps;
import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Prison extends GraphicCell implements CellActions{

    public Prison(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public void makeMove(Player player, Game game) throws IOException {
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

        CurrentGameState curr = new CurrentGameState(game.getCurrentPlayerIndex(), game.getPlayer1().getCash(), game.getPlayer2().getCash(), game.getCells()
                , new int[]{0,0}, new int[]{game.getPlayer1().getCurrentPosition(), game.getPlayer2().getCurrentPosition()},
                "Вы отправляетесь в тюрьму", Steps.DRAW_STRING);
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
