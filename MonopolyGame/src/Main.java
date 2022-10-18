import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.GraphicGame;
import ru.vsu.csf.monopoly.PlayingField;
import ru.vsu.csf.monopoly.TextGame;
import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.graphics.MainWindow;
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
        System.out.println("Введите количество игроков от 2 до 4");
        int k = scanner.nextInt();
        while(k < 2 || k > 4){
            System.out.println("Введено недопустимое число, попробуйте ещё раз");
            k = scanner.nextInt();
        }
        PlayingField field = new PlayingField();
        field.generateField();
        if(n == 1){
            Game game = new Game(k, new TextGame(), field);
            game.start();
        }
        if(n == 2){
            Game game = new Game(k, new GraphicGame(), field);
            game.start();
        }
    }
}
