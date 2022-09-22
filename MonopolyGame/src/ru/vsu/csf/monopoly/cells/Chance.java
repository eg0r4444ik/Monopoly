package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chance extends Cell{

    Random rnd = new Random();

    public Chance() {
        super(new ArrayList<>(), new Coord(0,0), Type.START, 0);
    }

    public enum Actions{
        DO_ACTIONS;
    }

}
