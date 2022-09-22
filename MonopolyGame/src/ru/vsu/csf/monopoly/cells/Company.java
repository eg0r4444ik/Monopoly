package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;

import java.util.ArrayList;

public class Company extends Cell {

    private String name;
    private int purchasePrice, countOfBuildings, supplyPrice;


    public Company(String name, int purchasePrice, int countOfBuildings, int supplyPrice) {
        super(new ArrayList<>(), new Coord(0,0), Type.COMPANY, 30);
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.countOfBuildings = countOfBuildings;
        this.supplyPrice = supplyPrice;
    }

    public int getCountOfBuildings() {
        return countOfBuildings;
    }

    public void buildCompany(){
        this.countOfBuildings++;
        this.supplyPrice *= 4;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
