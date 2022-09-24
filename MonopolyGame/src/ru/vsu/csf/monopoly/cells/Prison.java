package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Prison extends Cell{

    public Prison() {
        super(new ArrayList<>(), new Coord(0, 0), Type.RIALTO,30);
    }

    
}
