package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Casino extends Cell{

    private Random rnd = new Random();

    public Casino(){
        super(new Coord(0,0), Type.START, 30);
    }

    public int play(int betSum, int[] bet){
        int number = rnd.nextInt(5)+1;
        System.out.println("Выпало число " + number);
        for(int i : bet){
            if(i == number){
                return betSum*2;
            }
        }
        return 0;
    }


    public void makeMove(Player player){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вы попали в казино, выберете: ");
        System.out.println("1 - поставить 1000");
        System.out.println("2 - отказаться");
        int n = scanner.nextInt();
        while (n != 1 && n != 2) {
            System.out.println("Введенная вами команда неверная, повторите попытку");
            n = scanner.nextInt();
        }
        if (n == 1) {
            System.out.println("Введите 3 числа - ваши прогнозы");
            int[] k = new int[3];
            k[0] = scanner.nextInt();
            k[1] = scanner.nextInt();
            k[2] = scanner.nextInt();
            int x = play(1000, k);
            if(x == 0){
                System.out.println("Ваша ставка не сыграла");
            } else {
                System.out.println("Вы выиграли 1000");
            }
        }
    }
}
