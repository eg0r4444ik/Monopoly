package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.game.Runnable;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Casino extends Cell implements CellActions{

    private Random rnd = new Random();

    public Casino(int x, int y, int sizeX, int sizeY, Color color, String inscription) {
        super(x, y, sizeX, sizeY, color, inscription, new ArrayList<>());
    }

    public int play(int betSum, int bet, Player player, Game game){
        int number = rnd.nextInt(5)+1;
        player.setCash(player.getCash() - betSum);
        if(bet%2 == number%2){
            player.setCash(player.getCash() + betSum*2);
            game.getRunnable().render(null, Runnable.Steps.DRAW_STRING, "Вы выиграли");
        } else{
            game.getRunnable().render(null, Runnable.Steps.DRAW_STRING, "Вы проиграли");
        }
        return number;
    }


    public void makeMove(Player player, Game game){
        game.getRunnable().render(null, Runnable.Steps.CHOOSE_CASINO_COMMAND, "");

        //int n = game.getG().chooseCasinoCommand(this);
//        while (n != 1 && n != 2) {
//            System.out.println("Введенная вами команда неверная, повторите попытку");
//            n = game.getG().chooseCasinoCommand(this);
//        }
//        if (n == 1) {
//            game.getG().printStr("Введите 3 числа - ваши прогнозы");
//            int[] k = game.getG().scanThreeNum();
//            int x = play(1000, k, game);
//            if(x == 0){
//                game.getG().printStr("Ваша ставка не сыграла");
//            } else {
//                game.getG().printStr("Вы выиграли 1000");
//            }
//        }
    }
}
