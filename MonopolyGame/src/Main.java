import ru.vsu.csf.monopoly.PlayingField;
import ru.vsu.csf.monopoly.TextGame;
import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Если хотите выбрать текстовую версию, нажмите 1");
        System.out.println("Если хотите выбрать графическую версию, нажмите 2");
        int n = scanner.nextInt();
        while(n != 1 && n != 2){
            System.out.println("Введена недопустимая команда, попробуйте ещё раз");
            n = scanner.nextInt();
        }
        if(n == 1){
            System.out.println("Введите количество игроков от 2 до 4");
            int k = scanner.nextInt();
            while(k < 2 || k > 4){
                System.out.println("Введено недопустимое число, попробуйте ещё раз");
                k = scanner.nextInt();
            }
            TextGame txt = new TextGame(k);
            txt.start();
        }
        if(n == 2){

        }
    }
}
