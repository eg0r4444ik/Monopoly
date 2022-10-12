package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.cells.Cell;
import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.Random;
import java.util.Scanner;

public class Rialto extends Cell {
    private Random rnd = new Random();

    public Rialto() {
        super(new Coord(0, 0),30);
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
            game.printStr("Вы попали на биржу");
        } else{
            int n = 0;
            if(game.isTextGame()){
                n = game.getTxt().chooseRialtoCommand(this);
            }
            while (n != 1 && n != 2) {
                game.printStr("Введенная вами команда неверная, повторите попытку");
                n = scanner.nextInt();
            }
            if (n == 1) {
                payToExit(player);
                player.setCountOfThrowsInPrison(0);
                game.printStr("Ваш бюджет: " + player.getCash());
            }
            if(n == 2){
                if(player.getCountOfThrowsInPrison() == 3){
                    game.printStr("Вы больше не можете бросать кубик. С вас спишется 500 за выход.");
                    player.setCash(player.getCash()-500);
                    player.setCountOfThrowsInPrison(0);
                    player.setPrisonForVisit(true);
                    game.printStr("Ваш текущий баланс: " + player.getCash());
                } else{
                    int[] d = rollDice(player);
                    game.getTxt().rollDice(d[0],d[1]);
                    if(player.isPrisonForVisit()){
                        game.printStr("Вы вышли из тюрьмы");
                    } else{
                        game.printStr("Вы остаетесь в тюрьме");
                    }
                }
            }
        }
    }
}
