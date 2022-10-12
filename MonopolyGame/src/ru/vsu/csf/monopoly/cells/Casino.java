package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Casino extends Cell{

    private Random rnd = new Random();

    public Casino(){
        super(new Coord(0,0), 30);
    }

    public int play(int betSum, int[] bet, Game game){
        int number = rnd.nextInt(5)+1;
        if(game.isTextGame()){
            game.getTxt().rollOneDice(number);
        }
        for(int i : bet){
            if(i == number){
                return betSum*2;
            }
        }
        return 0;
    }


    public void makeMove(Player player, Game game){
        int n = 0;
        if(game.isTextGame()){
            n = game.getTxt().chooseCasinoCommand(this);
        }
        while (n != 1 && n != 2) {
            System.out.println("Введенная вами команда неверная, повторите попытку");
            n = game.getTxt().chooseCasinoCommand(this);
        }
        if (n == 1) {
            game.printStr("Введите 3 числа - ваши прогнозы");
            int[] k = new int[3];
            if(game.isTextGame()) {
                k = game.getTxt().scanThreeNum();
            }
            int x = play(1000, k, game);
            if(x == 0){
                game.printStr("Ваша ставка не сыграла");
            } else {
                game.printStr("Вы выиграли 1000");
            }
        }
    }
}
