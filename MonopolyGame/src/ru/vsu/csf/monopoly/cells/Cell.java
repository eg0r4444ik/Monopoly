package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.graphics.DrawUtils;
import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.objects.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class Cell extends GraphicCell{

    public Cell(int x, int y, int sizeX, int sizeY, Color color, String inscription, List<Player> players) {
        super(x,y, sizeX, sizeY, color, inscription,  players);
    }

    public abstract void makeMove(Player player, Game game);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
