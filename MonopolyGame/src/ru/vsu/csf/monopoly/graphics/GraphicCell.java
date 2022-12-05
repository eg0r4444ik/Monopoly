package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.cells.Cell;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.util.List;

public class GraphicCell{

    protected int x, y, sizeX, sizeY;
    protected Color color;
    protected String inscription;
    protected List<Player> players;

    public GraphicCell(int x, int y, int sizeX, int sizeY, Color color, String inscription, List<Player> players) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.color = color;
        this.inscription = inscription;
        this.players = players;
    }

    public void draw(Graphics2D g){
        DrawUtils.drawCell(g, x, y, sizeX, sizeY, color, inscription);
    }

    public void drawPlayers(Graphics2D g){
        if(players.size() > 0){
            DrawUtils.drawPlayers(g, x, y, sizeX, sizeY, players);
        }
    }
}
