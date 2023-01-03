package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.objects.Player;
import ru.vsu.csf.monopoly.objects.cells.Cell;

import java.awt.*;
import java.util.List;

public class GraphicCell extends Cell {

    protected int x,y, sizeX, sizeY;
    protected Color color;
    protected String inscription;

    public GraphicCell(int x, int y, int sizeX, int sizeY, Color color, String inscription, List<Player> players) {
        super(players);
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

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
