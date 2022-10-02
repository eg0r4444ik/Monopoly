package ru.vsu.csf.monopoly;

import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextGame {

    private Scanner scanner = new Scanner(System.in);
    private int playersCount;
    private List<Player> players = new ArrayList<>();

    public TextGame(int playersCount) {
        this.playersCount = playersCount;
    }

    public void start(){
        PlayingField field = new PlayingField(new ArrayList<>());
        field.generateField();
        for(int i = 0; i < playersCount; i++){
            Player player = new Player(0, 15000, field, new ArrayList<>(), 0);
            players.add(player);
        }
        while(!gameOver()){
            for(int i = 0; i < playersCount; i++){
                Player player = players.get(i);
                System.out.println("Игрок" + (i+1) + ": ");
                System.out.println("Ваш бюджет " + player.getCash());
                System.out.println("Ваши компании: ");
                for (Company c: player.getMyCompanies()) {
                    System.out.print(c.getName() + "   ");
                }
                System.out.println();
                chooseCommand(player);
            }
        }
    }


    public void chooseCommand(Player player){
        System.out.println("Выберите действие: ");
        System.out.println("1 - Сделать ход");
        System.out.println("2 - Построить филиал");
        System.out.println("3 - Предложить сделку");

        int i = scanner.nextInt();
        switch (i){
            case(1):
                if(!player.isPrisonForVisit()){
                    Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
                    if(currentCell instanceof Prison){
                        Prison prison = (Prison) currentCell;
                        prison.makeMove(player);
                    } else{
                        player.setPrisonForVisit(true);
                    }
                } else {
                    makeMove(player);
                }
                break;
            case(2):
                if(player.getCompaniesToBuild().size() == 0){
                    System.out.println("У вас нет компаний, в которых вы можете построить филиалы");
                } else {
                    System.out.println("Вот список ваших компаний, в которых вы можете строить филиалы: ");
                    for (int k = 0; k < player.getCompaniesToBuild().size(); k++) {
                        System.out.println((k + 1) + " - " + player.getCompaniesToBuild().get(k).getName());
                    }
                    System.out.println("Выберете компанию");
                    int command = scanner.nextInt();
                    while (command < 1 || command > player.getCompaniesToBuild().size()) {
                        System.out.println("Введенная команда неверная попробуйте заново");
                        command = scanner.nextInt();
                    }
                    player.build(player.getCompaniesToBuild().get(command - 1));
                    System.out.println("Теперь стоимость посещения данной компании: " + player.getCompaniesToBuild().get(command - 1).getSupplyPrice());
                }
                chooseCommand(player);
                break;
            case(3):
                System.out.println("Выберете игрока, которому хотите предложить сделку: ");
                for(int k = 0; k < players.size(); k++){
                    if(!players.get(k).equals(player)) {
                        System.out.println((k+1) + " - Игрок" + (k+1));
                    }
                }
                int command = scanner.nextInt();
                while(command < 1 || command > players.size()){
                    System.out.println("Введенная команда неверная попробуйте заново");
                    command = scanner.nextInt();
                }
                Player offerPlayer = players.get(command-1);

                System.out.println("Введите сумму, которую хотите предложить от 0 до " + player.getCash());
                int sum1 = scanner.nextInt();
                while(sum1 > player.getCash()){
                    System.out.println("Вы не располагаете таким бюджетом, попробуйте еще раз");
                    sum1 = scanner.nextInt();
                }
                int[] comp1 = offerDeal(player);


                System.out.println("Введите сумму, которую хотите получить от 0 до " + offerPlayer.getCash());
                int sum2 = scanner.nextInt();
                while(sum2 > offerPlayer.getCash()){
                    System.out.println("Данный игрок не располагает таким бюджетом, попробуйте еще раз");
                    sum2 = scanner.nextInt();
                }
                int[] comp2 = chooseWhatToGet(offerPlayer);

                System.out.println();
                System.out.println("Игрок" + command + ":");
                if(!acceptTheDeal(player, offerPlayer, sum1 ,comp1, sum2, comp2)){
                    System.out.println("Пользователь не согласился на сделку");
                }else{
                    exchange(player, offerPlayer, sum1, sum2, comp1, comp2);
                    System.out.println("Сделка успешно осуществлена");
                }

                chooseCommand(player);
                break;
            default:
                System.out.println("Введена неверная команда");
                chooseCommand(player);
                break;
        }
    }

    public void makeMove(Player player){
        int[] dice = player.rollDice();
        System.out.println("Выпадает: " + dice[0] + " " + dice[1]);
        player.go(dice);
        Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());

        if(currentCell instanceof Company){
            Company company = (Company) currentCell;
            company.makeMove(player);
        }


        if(currentCell instanceof Chance){
            Chance chance = (Chance) currentCell;
            chance.makeMove(player);
        }


        if(currentCell.getType() == Type.START){
            System.out.println("Вы получаете 2000 за прохождение круга");
            player.setCash(player.getCash() + 2000);
            System.out.println("Ваш бюджет: " + player.getCash());
        }


        if(currentCell instanceof Prison){
            Prison prison = (Prison) currentCell;
            prison.makeMove(player);
        }


        if(currentCell.getType() == Type.PRISON){
            System.out.println("Вы отправляетесь в тюрьму");
            player.setCurrentPosition(10);
            player.setPrisonForVisit(false);
        }


        if(currentCell instanceof Casino){
            Casino casino = (Casino) currentCell;
            casino.makeMove(player);
        }

        System.out.println();

        if(dice[1] == dice[0]){
            System.out.println("Вам выпал дубль, сделайте ход еще раз");
            makeMove(player);
        }

    }


    public boolean gameOver(){
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if(player.getCash() <= 0){
                System.out.println("Игра закончена!");
                System.out.println("Проиграл Игрок" + i+1);
                return true;
            }
        }
        return false;
    }

    public int[] offerDeal(Player player){
        System.out.println("Выберете компании для сделки: ");
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

    public void exchange(Player player1, Player player2, int sum1, int sum2, int[] comp1, int[] comp2){
        player1.setCash(player1.getCash() - sum1 + sum2);
        player2.setCash(player2.getCash() - sum2 + sum1);

        List<Company> p1Company = player1.getMyCompanies();
        List<Company> p2Company = player2.getMyCompanies();

        for(int i = 0; i < comp1.length; i++){
            Company c = p1Company.get(comp1[i]);
            p2Company.add(c);
            p1Company.remove(c);
        }

        for(int i = 0; i < comp2.length; i++){
            Company c = p2Company.get(comp2[i]);
            p1Company.add(c);
            p2Company.remove(c);
        }

        player1.setMyCompanies(p1Company);
        player2.setMyCompanies(p2Company);
    }
}
