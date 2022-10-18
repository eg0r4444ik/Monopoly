package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;

import ru.vsu.csf.monopoly.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Prison extends Cell implements CellActions{

    public Prison(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public void makeMove(Player player, Game game){
        game.getG().printStr("Вы отправляетесь в тюрьму");
        player.setCurrentPosition(10);
        player.setPrisonForVisit(false);
    }
}
