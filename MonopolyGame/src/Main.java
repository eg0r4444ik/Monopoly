import ru.vsu.csf.monopoly.PlayingField;
import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PlayingField field = new PlayingField(new ArrayList<>());
        field.generateField();
        Player player1 = new Player(0, 15000, field, new ArrayList<>(), 0);
        Player player2 = new Player(0, 15000, field, new ArrayList<>(), 0);
        while(player1.getCash() > 0 && player2.getCash() > 0){
            System.out.println("Игрок 1: ");
            chooseCommand(player1);
            System.out.println("Игрок 2: ");
            chooseCommand(player2);
        }
    }

    public static void chooseCommand(Player player){
        System.out.println("Выберите действие: ");
        System.out.println("1 - Бросить кубики");
        System.out.println("2 - Построить филиал");
        System.out.println("3 - Предложить сделку");

        int i = scanner.nextInt();
        switch (i){
            case(1):
                makeMove(player);
                break;
            case(2):

                break;
            case(3):

                break;
            default:

                break;
        }
    }

    public static void makeMove(Player player){
        Cell currCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
        List<Player> p1 = currCell.getPlayers();
        p1.remove(player);
        currCell.setPlayers(p1);
        int[] dice = player.rollDice();
        System.out.println("Выпадает: " + dice[0] + " " + dice[1]);
        player.go(dice);
        Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
        List<Player> p2 = currentCell.getPlayers();
        p2.add(player);
        currentCell.setPlayers(p2);


        if(currentCell instanceof Company){
            Company c = (Company) currentCell;
            System.out.println("Вы попали на компанию " + c.getName());
            if(c.isBought()){
                System.out.println("С вас списалось " + c.getSupplyPrice() + " за посещение");
                player.payForVisit();
                System.out.println("Ваш текущий баланс: " + player.getCash());
            } else{
                System.out.println("Введите: ");
                System.out.println("1 - купить компанию за " + c.getPurchasePrice());
                System.out.println("2 - не покупать");
                 int n = scanner.nextInt();
                 while (n != 1 && n != 2) {
                     System.out.println("Введенная вами команда неверная, повторите попытку");
                     n = scanner.nextInt();
                 }
                if (n == 1) {
                    player.buyCompany();
                    System.out.println("Ваш бюджет: " + player.getCash());
                }
            }
        }


        if(currentCell instanceof Chance){
            Chance c = (Chance) currentCell;
            System.out.println("Вы попали на поле шанс");
            System.out.println(c.toString(c.getAction()));
            if(c.getAction() != Chance.Actions.GO_TO_PRISON){
                System.out.println("Ваш бюджет: " + player.getCash());
            } else{
                p2.remove(player);
                currentCell.setPlayers(p2);
            }
        }


        if(currentCell.getType() == Type.START){
            System.out.println("Вы получаете 2000 за прохождение круга");
            player.setCash(player.getCash() + 2000);
            System.out.println("Ваш бюджет: " + player.getCash());
        }


        if(currentCell.getType() == Type.RIALTO){
            System.out.println("Вы попали на биржу");
        }


        if(currentCell.getType() == Type.PRISON){
            System.out.println("Вы отправляетесь в тюрьму");
            p2.remove(player);
            currentCell.setPlayers(p2);
            player.setCurrentPosition(10);
        }


        if(currentCell instanceof Casino){
            Casino c = (Casino) currCell;
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
                int x = c.play(1000, k);
                if(x == 0){
                    System.out.println("Ваша ставка не сыграла");
                } else {
                    System.out.println("Вы выиграли 1000");
                }
            }
        }
        if(dice[1] == dice[0]){
            System.out.println("Вам выпал дубль, сделайте ход еще раз");
            makeMove(player);
        }
    }
}
