package ru.vsu.csf.monopoly;

import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.cells.util.Coord;

import java.util.ArrayList;
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
        cells.add(new Cell(new ArrayList<>(), new Coord(0, 0), Type.START, 30));
        cells.add(new Company("Chanel", 600, 0, 20));
        cells.add(new Chance());
        cells.add(new Company("Hugo Boss", 600, 0, 40));
        cells.add(new Chance());
        cells.add(new Company("Mercedes", 2000, 0, 250));
        cells.add(new Company("Adidas", 1000, 0, 60));
        cells.add(new Chance());
        cells.add(new Company("Puma", 1000, 0, 60));
        cells.add(new Company("Lacoste", 1200, 0, 80));
        cells.add(new Cell(new ArrayList<>(), new Coord(0,0), Type.RIALTO, 30));
        cells.add(new Company("VK", 1400, 0, 100));
        cells.add(new Company("RStar", 1500, 0, 1000));
        cells.add(new Company("Facebook", 1400, 0, 100));
        cells.add(new Company("Twitter", 1600, 0, 120));
        cells.add(new Company("Audi", 2000, 0, 250));
        cells.add(new Company("Coca Cola", 1800, 0, 140));
        cells.add(new Chance());
        cells.add(new Company("Pepsi", 1800, 0, 140));
        cells.add(new Company("Fanta", 2000, 0, 160));
        cells.add(new Casino());
        cells.add(new Company("American Airlines", 2200, 0, 180));
        cells.add(new Chance());
        cells.add(new Company("Lufthansa", 2200, 0, 180));
        cells.add(new Company("British Airlines", 2400, 0, 200));
        cells.add(new Company("Ford", 2000, 0, 250));
        cells.add(new Company("McDonalds", 2600, 0, 220));
        cells.add(new Company("Burger King", 2600, 0, 220));
        cells.add(new Company("Rovio", 1500, 0, 1000));
        cells.add(new Company("KFC", 2800, 0, 240));
        cells.add(new Cell(new ArrayList<>(), new Coord(0, 0), Type.PRISON, 30));
        cells.add(new Company("Holiday Inn", 3000, 0, 260));
        cells.add(new Company("Radisson", 3000, 0, 260));
        cells.add(new Chance());
        cells.add(new Company("Novotel", 3200, 0, 280));
        cells.add(new Company("Land Rover", 2000, 0, 250));
        cells.add(new Chance());
        cells.add(new Company("Apple", 3500, 0, 350));
        cells.add(new Chance());
        cells.add(new Company("Nokia", 4000, 0, 500));
    }
}
