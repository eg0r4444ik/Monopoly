package ru.vsu.csf.monopoly.game;

import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.graphics.Dice;
import ru.vsu.csf.monopoly.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextGame implements Runnable{

    private Scanner scanner = new Scanner(System.in);
    private int playersCount, currentPlayerIndex = 0;
    private boolean canBuildCompany = true;
    private Game game;
    private PlayingField field;
    private List<Player> players;
    private Player currentPlayer;
    private Steps step;
    private int[] dice;

    private String currentString;

    @Override
    public void render(int[] dices, GraphicGame.Steps step, PlayingField field, String str) {
        this.field = field;
        if(str != null && str != "") {
            this.currentString = str;
        }
        if (dices != null) {
            this.dice = dices;
        }
        this.step = step;
        defineStep();
    }

    public TextGame(int playersCount) {
        this.playersCount = playersCount;
        currentString = "";
        dice = new int[2];
        this.field = new PlayingField();
        field.generateField();
        players = new ArrayList<>();
        int s = 0;
        for (int i = 0; i < playersCount; i++) {
            Player player = new Player(0, s, 200, Color.black, 0, 15000, field, new ArrayList<>(), 0);
            s += 200;
            players.add(player);
        }
        Cell start = field.getCells().get(0);
        ArrayList<Player> p = new ArrayList<>();
        for (Player pl : players) {
            p.add(pl);
        }
        start.setPlayers(p);
        currentPlayer = players.get(currentPlayerIndex);
        this.game = new Game(playersCount, field, this, players);
        chooseCommand(currentPlayer);
    }

    private void defineStep() {
        if (step == Steps.CHOOSE_COMMAND) {
            chooseCommand(currentPlayer);
        } else if (step == Steps.CHOOSE_COMPANY_COMMAND) {
            chooseCompanyCommand();
        } else if (step == Steps.DRAW_STRING) {
            printStr(currentString);
        } else if (step == Steps.DRAW_DICE) {
            rollDice(dice[0], dice[1]);
        } else if (step == Steps.CHOOSE_CASINO_COMMAND) {
            chooseCasinoCommand();
        } else if (step == Steps.CHOOSE_RIALTO_COMMAND) {
            chooseRialtoCommand();
        }
    }
    
    private void chooseCommand(Player player){
        System.out.println("Игрок" + (currentPlayerIndex%players.size()+1) + ": ");
        System.out.println("Ваш бюджет " + player.getCash());
        System.out.println("Ваши компании: ");
        for (Company c: player.getMyCompanies()) {
            System.out.print(c.getName() + "   ");
        }
        System.out.println();
        System.out.println("Выберите действие: ");
        System.out.println("1 - Сделать ход");
        if(canBuildCompany) {
            System.out.println("2 - Построить филиал");
            int i = scanner.nextInt();
            while (i < 1 || i > 3) {
                System.out.println("Введенная команда неверная попробуйте еще раз");
                i = scanner.nextInt();
            }

            if (i == 1) {
                game.makeMove(player);
            } else if (i == 2) {
                game.buildCompany(player, (Company) field.getCells().get(currentPlayer.getCurrentPosition()));
                canBuildCompany = false;
                chooseCommand(player);
            }
        }else {
            //System.out.println("3 - Предложить сделку");

            int i = scanner.nextInt();
            while (i != 1) {
                System.out.println("Введенная команда неверная попробуйте еще раз");
                i = scanner.nextInt();
            }

            game.makeMove(player);
        }
    }

    private void chooseCompanyCommand(){
        Company company = (Company) field.getCells().get(currentPlayer.getCurrentPosition());
        System.out.println("Введите: ");
        System.out.println("1 - купить компанию за " + company.getPurchasePrice());
        System.out.println("2 - не покупать");

        int n = scanner.nextInt();
        while(n < 1 || n > 2){
            System.out.println("Введенная команда неверная попробуйте еще раз");
            n = scanner.nextInt();
        }

        if(n == 1){
            currentPlayer.buyCompany();
        }
        next();
    }

    private void chooseCasinoCommand(){
        System.out.println("Вы попали в казино, выберете: ");
        System.out.println("1 - поставить 1000");
        System.out.println("2 - отказаться");

        int n = scanner.nextInt();
        while(n < 1 || n > 2){
            System.out.println("Введенная команда неверная попробуйте еще раз");
            n = scanner.nextInt();
        }

        if(n == 1){
            playInCasino();
        } else {
            next();
        }
    }

    private void playInCasino(){
        System.out.println("Выберите какое число выпадет: ");
        System.out.println("1 - четное");
        System.out.println("2 - нечетное");

        int n = scanner.nextInt();
        while(n < 1 || n > 2){
            System.out.println("Введенная команда неверная попробуйте еще раз");
            n = scanner.nextInt();
        }

        Casino casino = (Casino) field.getCells().get(currentPlayer.getCurrentPosition());

        if(n == 1){
            rollOneDice(casino.play(1000, 2, currentPlayer, game));
        } else{
            rollOneDice(casino.play(1000, 1, currentPlayer, game));
        }
    }

    private void chooseRialtoCommand(){
        System.out.println("Чтобы выйти из тюрьмы: ");
        Rialto rialto = (Rialto) field.getCells().get(currentPlayer.getCurrentPosition());
        int n = 0;
        if (currentPlayer.getCountOfThrowsInPrison() == 3) {
            System.out.println("1 - заплатить 500");

            n = scanner.nextInt();
            while(n != 1){
                System.out.println("Введенная команда неверная попробуйте еще раз");
                n = scanner.nextInt();
            }

            rialto.payToExit(currentPlayer);
            currentPlayer.setCountOfThrowsInPrison(0);
            chooseCommand(currentPlayer);
        } else {
            System.out.println("1 - заплатить 500");
            System.out.println("2 - бросать кубики");

            n = scanner.nextInt();
            while(n < 1 || n > 2){
                System.out.println("Введенная команда неверная попробуйте еще раз");
                n = scanner.nextInt();
            }

            if(n == 2){
                render(rialto.rollDice(currentPlayer, game), Steps.DRAW_DICE, field, null);
            } else{
                rialto.payToExit(currentPlayer);
                currentPlayer.setCountOfThrowsInPrison(0);
                chooseCommand(currentPlayer);
            }
        }
    }

    private void chooseCompanyToBuild(Player player){
        System.out.println("Вот список ваших компаний, в которых вы можете строить филиалы: ");
        for (int k = 0; k < player.getCompaniesToBuild().size(); k++) {
            System.out.println((k + 1) + " - " + player.getCompaniesToBuild().get(k).getName());
        }
        System.out.println("Выберете компанию");

        int command = scanner.nextInt();
        while(command < 0 || command >= player.getCompaniesToBuild().size()){
            System.out.println("Введенная команда неверная попробуйте еще раз");
            command = scanner.nextInt();
        }

        game.buildCompany(player, player.getCompaniesToBuild().get(command));
    }

//    private void chooseCompanyToExchange(Player player){
//        for (int k = 0; k < player.getMyCompanies().size(); k++) {
//            System.out.println((k + 1) + " - " + player.getMyCompanies().get(k).getName());
//        }
//        System.out.println("Введите количество компаний, которые хотите предложить");
//        int n = scanner.nextInt();
//        System.out.println("Выберете компании");
//        int[] command = new int[n];
//        for(int i = 0; i < n; i++){
//            command[i] = scanner.nextInt()-1;
//        }
//        return command;
//    }
//
//    private void choosePlayerToOffer(List<Player> players, Player player){
//        System.out.println("Выберете игрока, которому хотите предложить сделку: ");
//        for(int k = 0; k < players.size(); k++){
//            if(!players.get(k).equals(player)) {
//                System.out.println((k+1) + " - Игрок" + (k+1));
//            }
//        }
//        int command = scanner.nextInt();
//        return command;
//    }
//
//    private void chooseSum(Player player){
//        int sum = scanner.nextInt();
//        return sum;
//    }
//
//    private void chooseWhatToGet(Player player){
//        System.out.println("Выберете компании, которые вы хотите получить: ");
//        for (int k = 0; k < player.getMyCompanies().size(); k++) {
//            System.out.println((k + 1) + " - " + player.getMyCompanies().get(k).getName());
//        }
//        System.out.println("Введите количество компаний, которые хотите получить");
//        int n = scanner.nextInt();
//        System.out.println("Выберете компании");
//        int[] command = new int[n];
//        for(int i = 0; i < n; i++){
//            command[i] = scanner.nextInt()-1;
//        }
//
//        return command;
//    }
//
//    private boolean acceptTheDeal(Player player1, Player player2, int sum1, int[] comp1, int sum2, int[] comp2){
//        if(comp1.length > 0) {
//            System.out.println("Вам предложили " + sum1 + " и следующие компании");
//            for(int i = 0; i < comp1.length; i++){
//                System.out.println(player1.getMyCompanies().get(comp1[i]).getName());
//            }
//        } else{
//            System.out.println("Вам предложили " + sum1);
//        }
//
//
//        if(comp2.length > 0) {
//            System.out.println("Вы должны будете заплатить " + sum2 + " и отдать следующие компании");
//            for(int i = 0; i < comp2.length; i++){
//                System.out.println(player2.getMyCompanies().get(comp2[i]).getName());
//            }
//        } else{
//            System.out.println("Вы должны будете заплатить " + sum2);
//        }
//
//        System.out.println("Введите 1, если согласны на сделку, и 2, если не согласны");
//        int command = scanner.nextInt();
//        while(command != 1 && command != 2){
//            System.out.println("Введена неверная команда, попробуйте заново");
//            command = scanner.nextInt();
//        }
//        if(command == 1){
//            return  true;
//        } else{
//            return false;
//        }
//    }

    private void rollDice(int dice1, int dice2){
        System.out.println("Выпадает: " + dice1 + " " + dice2);
    }
    private void rollOneDice(int dice){
        System.out.println("Выпадает: " + dice);
    }
    private int[] scanThreeNum(){
        int[] k = new int[3];
        k[0] = scanner.nextInt();
        k[1] = scanner.nextInt();
        k[2] = scanner.nextInt();
        return k;
    }

    private void printStr(String str){
        System.out.println(str);
        if (dice[0] != dice[1]) {
            canBuildCompany = true;
            currentPlayerIndex++;
        }
        currentPlayer = players.get(currentPlayerIndex % players.size());
        chooseCommand(currentPlayer);
    }

    private void next(){
        if (dice[0] != dice[1]) {
            canBuildCompany = true;
            currentPlayerIndex++;
        }
        currentPlayer = players.get(currentPlayerIndex % players.size());
        chooseCommand(currentPlayer);
    }
}
