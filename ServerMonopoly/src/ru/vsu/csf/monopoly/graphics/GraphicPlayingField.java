package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.objects.cells.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicPlayingField {

    private List<GraphicCell> cells = new ArrayList<>();

    public List<GraphicCell> getCells() {
        return cells;
    }

    public void setCells(List<GraphicCell> cells) {
        this.cells = cells;
    }

    public void generateField() {
        cells.add(new Start(260, 37, 120, 70, new Color(214, 89, 232, 255), "Старт"));
        cells.add(new Company(380, 37, 120, 70, Color.WHITE,"Chanel", 600, 0, 20, Company.CompanyType.WEAR, 2));
        cells.add(new Chance(500, 37, 120, 70, new Color(35, 192, 36),"?"));
        cells.add(new Company(620, 37, 120, 70, Color.WHITE,"Hugo Boss", 600, 0, 40, Company.CompanyType.WEAR, 2));
        cells.add(new Chance(740, 37, 120, 70, new Color(35, 192, 36),"?"));
        cells.add(new Company(860, 37, 120, 70, Color.WHITE,"Mercedes", 2000, 0, 250, Company.CompanyType.AUTO, 10));
        cells.add(new Company(980, 37, 120, 70, Color.WHITE,"Adidas", 1000, 0, 60, Company.CompanyType.SPORT_WEAR, 3));
        cells.add(new Chance(1100, 37, 120, 70, new Color(35, 192, 36),"?"));
        cells.add(new Company(1220, 37, 120, 70, Color.WHITE,"Puma", 1000, 0, 60, Company.CompanyType.SPORT_WEAR, 3));
        cells.add(new Company(1340, 37, 120, 70, Color.WHITE,"Lacoste", 1200, 0, 80, Company.CompanyType.SPORT_WEAR, 3));
        cells.add(new Rialto(1460, 37, 120, 70, new Color(57, 218, 202, 240),"Биржа"));
        cells.add(new Company(1460, 107, 120, 70, Color.WHITE,"VK", 1400, 0, 100, Company.CompanyType.SOCIAL_NETWORK, 3));
        cells.add(new Company(1460, 177, 120, 70, Color.WHITE,"RStar", 1500, 0, 1000, Company.CompanyType.GAME, 10));
        cells.add(new Company(1460, 247, 120, 70, Color.WHITE,"Facebook", 1400, 0, 100, Company.CompanyType.SOCIAL_NETWORK, 3));
        cells.add(new Company(1460, 317, 120, 70, Color.WHITE,"Twitter", 1600, 0, 120, Company.CompanyType.SOCIAL_NETWORK, 3));
        cells.add(new Company(1460, 387, 120, 70, Color.WHITE,"Audi", 2000, 0, 250, Company.CompanyType.AUTO, 10));
        cells.add(new Company(1460, 457, 120, 70, Color.WHITE,"Coca Cola", 1800, 0, 140, Company.CompanyType.DRINKS, 3));
        cells.add(new Chance(1460, 527, 120, 70, new Color(35, 192, 36),"?"));
        cells.add(new Company(1460, 597, 120, 70, Color.WHITE,"Pepsi", 1800, 0, 140, Company.CompanyType.DRINKS, 3));
        cells.add(new Company(1460, 667, 120, 70, Color.WHITE,"Fanta", 2000, 0, 160, Company.CompanyType.DRINKS, 3));
        cells.add(new Casino(1460, 737, 120, 70, new Color(229, 111, 33, 240),"Казино"));
        cells.add(new Company(1340, 737, 120, 70, Color.WHITE,"American Airlines", 2200, 0, 180, Company.CompanyType.AIRLINES, 3));
        cells.add(new Chance(1220, 737, 120, 70, new Color(35, 192, 36),"?"));
        cells.add(new Company(1100, 737, 120, 70, Color.WHITE,"Lufthansa", 2200, 0, 180, Company.CompanyType.AIRLINES, 3));
        cells.add(new Company(980, 737, 120, 70, Color.WHITE,"British Airlines", 2400, 0, 200, Company.CompanyType.AIRLINES, 3));
        cells.add(new Company(860, 737, 120, 70, Color.WHITE,"Ford", 2000, 0, 250, Company.CompanyType.AUTO, 10));
        cells.add(new Company(740, 737, 120, 70, Color.WHITE,"McDonalds", 2600, 0, 220, Company.CompanyType.FAST_FOOD, 3));
        cells.add(new Company(620, 737, 120, 70, Color.WHITE,"Burger King", 2600, 0, 220, Company.CompanyType.FAST_FOOD, 3));
        cells.add(new Company(500, 737, 120, 70, Color.WHITE,"Rovio", 1500, 0, 1000, Company.CompanyType.GAME, 10));
        cells.add(new Company(380, 737, 120, 70, Color.WHITE,"KFC", 2800, 0, 240, Company.CompanyType.FAST_FOOD, 3));
        cells.add(new Prison(260, 737, 120, 70, new Color(176, 168, 168),"Тюрьма"));
        cells.add(new Company(260, 667, 120, 70, Color.WHITE,"Holiday Inn", 3000, 0, 260, Company.CompanyType.HOTEL, 3));
        cells.add(new Company(260, 597, 120, 70, Color.WHITE,"Radisson", 3000, 0, 260, Company.CompanyType.HOTEL, 3));
        cells.add(new Chance(260, 527, 120, 70, new Color(35, 192, 36),"?"));
        cells.add(new Company(260, 457, 120, 70, Color.WHITE,"Novotel", 3200, 0, 280, Company.CompanyType.HOTEL, 3));
        cells.add(new Company(260, 387, 120, 70, Color.WHITE,"Land Rover", 2000, 0, 250, Company.CompanyType.AUTO, 10));
        cells.add(new Chance(260, 317, 120, 70, new Color(35, 192, 36),"?"));
        cells.add(new Company(260, 247, 120, 70, Color.WHITE,"Apple", 3500, 0, 350, Company.CompanyType.TECHNO, 2));
        cells.add(new Chance(260, 177, 120, 70, new Color(35, 192, 36),"?"));
        cells.add(new Company(260, 107, 120, 70, Color.WHITE,"Nokia", 4000, 0, 500, Company.CompanyType.TECHNO, 2));
    }
}
