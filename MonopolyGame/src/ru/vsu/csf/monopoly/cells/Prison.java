package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.game.Game;

import ru.vsu.csf.monopoly.game.Runnable;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Prison extends Cell implements CellActions{

    public Prison(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public void makeMove(Player player, Game game){
        //game.getG().printStr("Вы отправляетесь в тюрьму");
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

        game.getRunnable().render(null, Runnable.Steps.DRAW_STRING, "Вы отправляетесь в тюрьму");
    }
}
