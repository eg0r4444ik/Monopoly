package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Prison extends Cell{

    private Random rnd = new Random();

    public Prison() {
        super(new Coord(0, 0), Type.RIALTO,30);
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

    public void makeMove(Player player){
        Scanner scanner = new Scanner(System.in);
        if(player.isPrisonForVisit()) {
            System.out.println("Вы попали на биржу");
        } else{
            System.out.println("Чтобы выйти из тюрьмы: ");
            System.out.println("1 - заплатить 500");
            System.out.println("2 - бросать кубики");
            int n = scanner.nextInt();
            while (n != 1 && n != 2) {
                System.out.println("Введенная вами команда неверная, повторите попытку");
                n = scanner.nextInt();
            }
            if (n == 1) {
                payToExit(player);
                player.setCountOfThrowsInPrison(0);
                System.out.println("Ваш бюджет: " + player.getCash());
            }
            if(n == 2){
                if(player.getCountOfThrowsInPrison() == 3){
                    System.out.println("Вы больше не можете бросать кубик. С вас спишется 500 за выход.");
                    player.setCash(player.getCash()-500);
                    player.setCountOfThrowsInPrison(0);
                    player.setPrisonForVisit(true);
                    System.out.println("Ваш текущий баланс: " + player.getCash());
                } else{
                    int[] d = rollDice(player);
                    System.out.println("Вам выпало: " + d[0] + " " + d[1]);
                    if(player.isPrisonForVisit()){
                        System.out.println("Вы вышли из тюрьмы");
                    } else{
                        System.out.println("Вы остаетесь в тюрьме");
                    }
                }
            }
        }
    }
}
