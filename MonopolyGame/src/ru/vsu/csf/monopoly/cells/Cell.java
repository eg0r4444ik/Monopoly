package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.graphics.DrawUtils;
import ru.vsu.csf.monopoly.player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class Cell extends JPanel {

    private int x, y, sizeX, sizeY;
    private Color color;
    private String inscription;
    private List<Player> players;


    public Cell(int x, int y, int sizeX, int sizeY, Color color, String inscription, List<Player> players) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.color = color;
        this.inscription = inscription;
        this.players = players;
    }

    public abstract void makeMove(Player player, Game game);

    public void draw(Graphics2D g){
        if(this instanceof Company){
            Company c = (Company) this;
            int price = 0;
            if(c.isBought()){
                price = c.getSupplyPrice();
            } else{
                price = c.getPurchasePrice();
            }
            DrawUtils.drawCell(g, x, y, sizeX, sizeY, color, inscription, price, c.getCompanyType());
        } else{
            DrawUtils.drawCell(g, x, y, sizeX, sizeY, color, inscription);
        }
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
}
