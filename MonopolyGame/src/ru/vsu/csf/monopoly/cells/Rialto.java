package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.game.Game;

import ru.vsu.csf.monopoly.game.GraphicGame;
import ru.vsu.csf.monopoly.game.Runnable;
import ru.vsu.csf.monopoly.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Rialto extends Cell implements CellActions{
    private Random rnd = new Random();

    public Rialto(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public void payToExit(Player player){
        player.setPrisonForVisit(true);
        player.setCash(player.getCash() - 500);
    }

    public int[] rollDice(Player player, Game game){
        rnd = new Random();
        int[] dice = new int[2];
        dice[0] = rnd.nextInt(5) + 1;
        dice[1] = rnd.nextInt(5) + 1;

        if(dice[0] == dice[1]){
            player.setPrisonForVisit(true);
            player.setCountOfThrowsInPrison(0);
            game.getRunnable().render(null, Runnable.Steps.DRAW_STRING, game.getField(), "Вы вышли из тюрьмы");

        }else{
            player.setCountOfThrowsInPrison(player.getCountOfThrowsInPrison()+1);
            game.getRunnable().render(null, Runnable.Steps.DRAW_STRING, game.getField(), "Вы остаетесь в тюрьме");
        }

        return dice;
    }

    public void makeMove(Player player, Game game){
        if(player.isPrisonForVisit()) {
            game.getRunnable().render(null, Runnable.Steps.DRAW_STRING, game.getField(), "Вы попали на биржу");
        } else{
            game.getRunnable().render(null, Runnable.Steps.CHOOSE_RIALTO_COMMAND, game.getField(), "");
        }
    }
}
