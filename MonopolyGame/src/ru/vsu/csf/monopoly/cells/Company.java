package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;

import java.util.ArrayList;

public class Company extends Cell {

    private String name;
    private int purchasePrice, size, countOfBuildings, supplyPrice;


    public Company(String name, int purchasePrice, int size, int countOfBuildings, int supplyPrice) {
        super(new ArrayList<>(), new Coord(0,0), Type.START, 0);
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.size = size;
        this.countOfBuildings = countOfBuildings;
        this.supplyPrice = supplyPrice;
    }

    public int getCountOfBuildings() {
        return countOfBuildings;
    }

    public void setCountOfBuildings(int countOfBuildings) {
        this.countOfBuildings = countOfBuildings;
    }

    public int getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(int supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
