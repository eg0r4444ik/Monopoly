package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

public class Start extends Cell{

    public Start() {
        super(new Coord(0,0), 30);
    }

    public void getMoney(Player player){
        player.setCash(player.getCash() + 2000);
    }
}
