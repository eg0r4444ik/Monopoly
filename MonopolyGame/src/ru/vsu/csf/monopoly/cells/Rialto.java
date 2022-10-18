package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.cells.Cell;

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

    public int[] rollDice(Player player){
        rnd = new Random();
        int[] dice = new int[2];
        dice[0] = rnd.nextInt(5) + 1;
        dice[1] = rnd.nextInt(5) + 1;

        if(dice[0] == dice[1]){
            player.setPrisonForVisit(true);
        }else{
            player.setCountOfThrowsInPrison(player.getCountOfThrowsInPrison()+1);
        }

        return dice;
    }

    public void makeMove(Player player, Game game){
        Scanner scanner = new Scanner(System.in);
        if(player.isPrisonForVisit()) {
            game.getG().printStr("Вы попали на биржу");
        } else{
            int n = game.getG().chooseRialtoCommand(this);
            while (n != 1 && n != 2) {
                game.getG().printStr("Введенная вами команда неверная, повторите попытку");
                n = scanner.nextInt();
            }
            if (n == 1) {
                payToExit(player);
                player.setCountOfThrowsInPrison(0);
                game.getG().printStr("Ваш бюджет: " + player.getCash());
            }
            if(n == 2){
                if(player.getCountOfThrowsInPrison() == 3){
                    game.getG().printStr("Вы больше не можете бросать кубик. С вас спишется 500 за выход.");
                    player.setCash(player.getCash()-500);
                    player.setCountOfThrowsInPrison(0);
                    player.setPrisonForVisit(true);
                    game.getG().printStr("Ваш текущий баланс: " + player.getCash());
                } else{
                    int[] d = rollDice(player);
                    game.getG().rollDice(d[0],d[1]);
                    if(player.isPrisonForVisit()){
                        game.getG().printStr("Вы вышли из тюрьмы");
                    } else{
                        game.getG().printStr("Вы остаетесь в тюрьме");
                    }
                }
            }
        }
    }
}
