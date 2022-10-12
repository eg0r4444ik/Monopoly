package ru.vsu.csf.monopoly;

import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int playersCount;
    private List<Player> players = new ArrayList<>();
    private boolean isTextGame;
    private TextGame txt = new TextGame();


    public Game(int playersCount, boolean isTextGame) {
        this.playersCount = playersCount;
        this.isTextGame = isTextGame;
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
                chooseCommand(player);
            }
        }
    }


    public void chooseCommand(Player player){
        int i = 0;

        if(isTextGame){
            i = txt.chooseCommand(player, players.indexOf(player)+1);
        }

        switch (i){
            case(1):
                makeMove(player);
                break;
            case(2):
                buildCompany(player);
                break;
            case(3):
                offer(player);
                break;
            default:
                printStr("Введена неверная команда");
                chooseCommand(player);
                break;
        }
    }


    public void makeMove(Player player){
        if(!player.isPrisonForVisit()){
            Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            if(currentCell instanceof Rialto){
                Rialto rialto = (Rialto) currentCell;
                rialto.makeMove(player, this);
            } else{
                player.setPrisonForVisit(true);
            }
        } else {
            int[] dice = player.rollDice();
            if(isTextGame){
                txt.rollDice(dice[0], dice[1]);
            }

            player.go(dice);
            Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            currentCell.makeMove(player, this);

            System.out.println();
            if (dice[1] == dice[0]) {
                printStr("Вам выпал дубль, сделайте ход еще раз");
                makeMove(player);
            }
        }
    }


    public void buildCompany(Player player){
        if(player.getCompaniesToBuild().size() == 0){
            printStr("У вас нет компаний, в которых вы можете построить филиалы");
        } else {
            printStr("Стоимость постройки филиала 1500");
            if(player.getCash() >= 1500) {
                int command = 0;
                if(isTextGame) {
                    command = txt.chooseCompanyToBuild(player);
                }
                while (command < 1 || command > player.getCompaniesToBuild().size()) {
                    printStr("Введенная команда неверная попробуйте заново");
                    command = txt.chooseCompanyToBuild(player);;
                }
                player.build(player.getCompaniesToBuild().get(command - 1));
                printStr("Теперь стоимость посещения данной компании: " + player.getCompaniesToBuild().get(command - 1).getSupplyPrice());
            } else{
                printStr("У вас недостаточно средств");
            }
        }
        chooseCommand(player);
    }

    public void offer(Player player){
        int command = txt.choosePlayerToOffer(players, player);
        while(command < 1 || command > players.size()){
            printStr("Введенная команда неверная попробуйте заново");
            command = txt.choosePlayerToOffer(players, player);
        }
        Player offerPlayer = players.get(command-1);

        printStr("Введите сумму, которую хотите предложить от 0 до " + player.getCash());
        int sum1 = txt.chooseSum(player);
        while(sum1 > player.getCash()){
            printStr("Вы не располагаете таким бюджетом, попробуйте еще раз");
            sum1 = txt.chooseSum(player);
        }
        int[] comp1 = offerDeal(player);


        printStr("Введите сумму, которую хотите получить от 0 до " + offerPlayer.getCash());
        int sum2 = txt.chooseSum(offerPlayer);
        while(sum2 > offerPlayer.getCash()){
            printStr("Данный игрок не располагает таким бюджетом, попробуйте еще раз");
            sum2 = txt.chooseSum(offerPlayer);
        }
        int[] comp2 = chooseWhatToGet(offerPlayer);

        System.out.println();
        printStr("Игрок" + command + ":");
        if(!acceptTheDeal(player, offerPlayer, sum1 ,comp1, sum2, comp2)){
            printStr("Пользователь не согласился на сделку");
        }else{
            exchange(player, offerPlayer, sum1, sum2, comp1, comp2);
            printStr("Сделка успешно осуществлена");
        }

        chooseCommand(player);
    }

    public int[] offerDeal(Player player){
        printStr("Выберете компании для сделки: ");
        int[] command = txt.chooseCompanyToExchange(player);
        return command;
    }

    public int[] chooseWhatToGet(Player player){
        return txt.chooseWhatToGet(player);
    }

    public boolean acceptTheDeal(Player player1, Player player2, int sum1, int[] comp1, int sum2, int[] comp2){
        return txt.acceptTheDeal(player1, player2, sum1, comp1, sum2, comp2);
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



    public boolean gameOver(){
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if(player.getCash() < 0){
                printStr("Игра закончена!");
                printStr("Проиграл Игрок" + (i+1));
                return true;
            }
        }
        return false;
    }

    public void printStr(String str){
        if(isTextGame){
            txt.displayTheInscription(str);
        }
    }

    public TextGame getTxt() {
        return txt;
    }

    public boolean isTextGame() {
        return isTextGame;
    }
}
