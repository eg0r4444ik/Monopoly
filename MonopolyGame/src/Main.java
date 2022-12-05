import ru.vsu.csf.monopoly.game.TextGame;
import ru.vsu.csf.monopoly.graphics.MainWindow;

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
        if(n == 1){
            TextGame txt = new TextGame(k);
        }
        if(n == 2){
            MainWindow mw = new MainWindow(k);
            mw.setVisible(true);
        }
    }
}
