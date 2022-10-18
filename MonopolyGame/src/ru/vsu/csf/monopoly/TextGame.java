package ru.vsu.csf.monopoly;

import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.graphics.DrawPanel;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextGame implements GameInterface{

    private Scanner scanner = new Scanner(System.in);


    public int chooseCommand(Player player, int index){
        System.out.println("Игрок" + index + ": ");
        System.out.println("Ваш бюджет " + player.getCash());
        System.out.println("Ваши компании: ");
        for (Company c: player.getMyCompanies()) {
            System.out.print(c.getName() + "   ");
        }
        System.out.println();
        System.out.println("Выберите действие: ");
        System.out.println("1 - Сделать ход");
        System.out.println("2 - Построить филиал");
        System.out.println("3 - Предложить сделку");

        int i = scanner.nextInt();
        return i;
    }

    public int chooseCompanyCommand(Company company){
        System.out.println("Введите: ");
        System.out.println("1 - купить компанию за " + company.getPurchasePrice());
        System.out.println("2 - не покупать");
        int n = scanner.nextInt();
        return n;
    }

    public int chooseCasinoCommand(Casino casino){
        System.out.println("Вы попали в казино, выберете: ");
        System.out.println("1 - поставить 1000");
        System.out.println("2 - отказаться");
        int n = scanner.nextInt();
        return n;
    }

    public int chooseRialtoCommand(Rialto rialto){
        System.out.println("Чтобы выйти из тюрьмы: ");
        System.out.println("1 - заплатить 500");
        System.out.println("2 - бросать кубики");
        int n = scanner.nextInt();
        return n;
    }

    public int chooseCompanyToBuild(Player player){
        System.out.println("Вот список ваших компаний, в которых вы можете строить филиалы: ");
        for (int k = 0; k < player.getCompaniesToBuild().size(); k++) {
            System.out.println((k + 1) + " - " + player.getCompaniesToBuild().get(k).getName());
        }
        System.out.println("Выберете компанию");
        int command = scanner.nextInt();
        return command;
    }

    public int[] chooseCompanyToExchange(Player player){
        for (int k = 0; k < player.getMyCompanies().size(); k++) {
            System.out.println((k + 1) + " - " + player.getMyCompanies().get(k).getName());
        }
        System.out.println("Введите количество компаний, которые хотите предложить");
        int n = scanner.nextInt();
        System.out.println("Выберете компании");
        int[] command = new int[n];
        for(int i = 0; i < n; i++){
            command[i] = scanner.nextInt()-1;
        }
        return command;
    }

    public int choosePlayerToOffer(List<Player> players, Player player){
        System.out.println("Выберете игрока, которому хотите предложить сделку: ");
        for(int k = 0; k < players.size(); k++){
            if(!players.get(k).equals(player)) {
                System.out.println((k+1) + " - Игрок" + (k+1));
            }
        }
        int command = scanner.nextInt();
        return command;
    }

    public int chooseSum(Player player){
        int sum = scanner.nextInt();
        return sum;
    }

    public int[] chooseWhatToGet(Player player){
        System.out.println("Выберете компании, которые вы хотите получить: ");
        for (int k = 0; k < player.getMyCompanies().size(); k++) {
            System.out.println((k + 1) + " - " + player.getMyCompanies().get(k).getName());
        }
        System.out.println("Введите количество компаний, которые хотите получить");
        int n = scanner.nextInt();
        System.out.println("Выберете компании");
        int[] command = new int[n];
        for(int i = 0; i < n; i++){
            command[i] = scanner.nextInt()-1;
        }

        return command;
    }

    public boolean acceptTheDeal(Player player1, Player player2, int sum1, int[] comp1, int sum2, int[] comp2){
        if(comp1.length > 0) {
            System.out.println("Вам предложили " + sum1 + " и следующие компании");
            for(int i = 0; i < comp1.length; i++){
                System.out.println(player1.getMyCompanies().get(comp1[i]).getName());
            }
        } else{
            System.out.println("Вам предложили " + sum1);
        }


        if(comp2.length > 0) {
            System.out.println("Вы должны будете заплатить " + sum2 + " и отдать следующие компании");
            for(int i = 0; i < comp2.length; i++){
                System.out.println(player2.getMyCompanies().get(comp2[i]).getName());
            }
        } else{
            System.out.println("Вы должны будете заплатить " + sum2);
        }

        System.out.println("Введите 1, если согласны на сделку, и 2, если не согласны");
        int command = scanner.nextInt();
        while(command != 1 && command != 2){
            System.out.println("Введена неверная команда, попробуйте заново");
            command = scanner.nextInt();
        }
        if(command == 1){
            return  true;
        } else{
            return false;
        }
    }

    public void rollDice(int dice1, int dice2){
        System.out.println("Выпадает: " + dice1 + " " + dice2);
    }
    public void rollOneDice(int dice){
        System.out.println("Выпадает: " + dice);
    }
    public int[] scanThreeNum(){
        int[] k = new int[3];
        k[0] = scanner.nextInt();
        k[1] = scanner.nextInt();
        k[2] = scanner.nextInt();
        return k;
    }

    public void printStr(String str){
        System.out.println(str);
    }

    @Override
    public void setDp(DrawPanel dp) {

    }
}
