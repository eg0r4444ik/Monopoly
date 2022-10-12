package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Prison extends Cell{

    public Prison() {
        super(new Coord(0, 0),30);
    }

    public void visitPrison(Player player){
        player.setCurrentPosition(10);
    }
}
