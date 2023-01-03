package ru.vsu.csf.monopoly.objects.cells;

import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Cell{

    protected List<Player> players;

    public Cell(List<Player> players) {
        this.players = players;
    }

    public void makeMove(Player player, Game game) throws IOException{}

    public void addPlayer(Player player){players.add(player);}

    public void emptyCell(){
        players = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
