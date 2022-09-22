package ru.vsu.csf.monopoly;

import ru.vsu.csf.monopoly.cells.Cell;
import ru.vsu.csf.monopoly.cells.Company;

import java.util.List;

public class PlayingField {

    private List<Cell> cells;

    public PlayingField(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public void generateField(){

    }
}
