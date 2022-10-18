package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Casino extends Cell implements CellActions{

    private Random rnd = new Random();

    public Casino(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public int play(int betSum, int[] bet, Game game){
        int number = rnd.nextInt(5)+1;
        game.getG().rollOneDice(number);
        for(int i : bet){
            if(i == number){
                return betSum*2;
            }
        }
        return 0;
    }


    public void makeMove(Player player, Game game){
        int n = game.getG().chooseCasinoCommand(this);
        while (n != 1 && n != 2) {
            System.out.println("Введенная вами команда неверная, повторите попытку");
            n = game.getG().chooseCasinoCommand(this);
        }
        if (n == 1) {
            game.getG().printStr("Введите 3 числа - ваши прогнозы");
            int[] k = game.getG().scanThreeNum();
            int x = play(1000, k, game);
            if(x == 0){
                game.getG().printStr("Ваша ставка не сыграла");
            } else {
                game.getG().printStr("Вы выиграли 1000");
            }
        }
    }
}
