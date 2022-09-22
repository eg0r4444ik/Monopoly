package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.List;

public class Cell {

    private List<Player> players;
    private Coord coord;
    private Type type;
    private int size;


    public Cell(List<Player> players, Coord coord, Type type, int size) {
        this.players = players;
        this.coord = coord;
        this.type = type;
        this.size = size;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
