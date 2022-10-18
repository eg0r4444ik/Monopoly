package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Start extends Cell implements CellActions{

    public Start(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public void makeMove(Player player, Game game){
        game.getG().printStr("Вы получаете 2000 за прохождение круга");
        player.setCash(player.getCash() + 2000);
        game.getG().printStr("Ваш бюджет: " + player.getCash());
    }
}
