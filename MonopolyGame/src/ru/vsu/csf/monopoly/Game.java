package ru.vsu.csf.monopoly;

import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.graphics.DrawPanel;
import ru.vsu.csf.monopoly.graphics.MainWindow;
import ru.vsu.csf.monopoly.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private int playersCount;
    private ArrayList<Player> players = new ArrayList<>();
    private GameInterface g;
    private PlayingField field;
    private DrawPanel dp;
    private MainWindow mw;
    private List<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.ORANGE, Color.GREEN);


    public Game(int playersCount,GameInterface g, PlayingField field) {
        this.playersCount = playersCount;
        this.g = g;
        this.field = field;
    }

    public void start(){
        if(g instanceof GraphicGame) {
            mw = new MainWindow(this, g);
            dp = mw.getDp();
            mw.setVisible(true);
        }
        for(int i = 0; i < playersCount; i++){
            Player player = new Player(260, 35, 24, colors.get(i), 0, 15000, field, new ArrayList<>(), 0);
            players.add(player);
        }
        Cell start = field.getCells().get(0);
        ArrayList<Player> p = new ArrayList<>();
        for(Player pl : players){
            p.add(pl);
        }
        start.setPlayers(p);
        while(!gameOver()){
            for(int i = 0; i < playersCount; i++){
                Player player = players.get(i);
                chooseCommand(player);
            }
        }
    }


    public void chooseCommand(Player player){
        int i = 0;
        i = g.chooseCommand(player, players.indexOf(player)+1);


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
                g.printStr("Введена неверная команда");
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
            g.rollDice(dice[0], dice[1]);

            Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            List<Player> p = currentCell.getPlayers();
            p.remove(0);
            currentCell.setPlayers(p);
            player.go(dice);

            currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            player.setX(currentCell.getX());
            player.setY(currentCell.getY());
            p = currentCell.getPlayers();
            p.add(player);
            currentCell.setPlayers(p);
            currentCell.makeMove(player, this);

            System.out.println();
            if (dice[1] == dice[0]) {
                g.printStr("Вам выпал дубль, сделайте ход еще раз");
                makeMove(player);
            }
        }
    }


    public void buildCompany(Player player){
        if(player.getCompaniesToBuild().size() == 0){
            g.printStr("У вас нет компаний, в которых вы можете построить филиалы");
        } else {
            g.printStr("Стоимость постройки филиала 1500");
            if(player.getCash() >= 1500) {
                int command = 0;
                command = g.chooseCompanyToBuild(player);
                while (command < 1 || command > player.getCompaniesToBuild().size()) {
                    g.printStr("Введенная команда неверная попробуйте заново");
                    command = g.chooseCompanyToBuild(player);;
                }
                player.build(player.getCompaniesToBuild().get(command - 1));
                g.printStr("Теперь стоимость посещения данной компании: " + player.getCompaniesToBuild().get(command - 1).getSupplyPrice());
            } else{
                g.printStr("У вас недостаточно средств");
            }
        }
        chooseCommand(player);
    }

    public void offer(Player player){
        int command = g.choosePlayerToOffer(players, player);
        while(command < 1 || command > players.size()){
            g.printStr("Введенная команда неверная попробуйте заново");
            command = g.choosePlayerToOffer(players, player);
        }
        Player offerPlayer = players.get(command-1);

        g.printStr("Введите сумму, которую хотите предложить от 0 до " + player.getCash());
        int sum1 = g.chooseSum(player);
        while(sum1 > player.getCash()){
            g.printStr("Вы не располагаете таким бюджетом, попробуйте еще раз");
            sum1 = g.chooseSum(player);
        }
        int[] comp1 = offerDeal(player);


        g.printStr("Введите сумму, которую хотите получить от 0 до " + offerPlayer.getCash());
        int sum2 = g.chooseSum(offerPlayer);
        while(sum2 > offerPlayer.getCash()){
            g.printStr("Данный игрок не располагает таким бюджетом, попробуйте еще раз");
            sum2 = g.chooseSum(offerPlayer);
        }
        int[] comp2 = chooseWhatToGet(offerPlayer);

        System.out.println();
        g.printStr("Игрок" + command + ":");
        if(!acceptTheDeal(player, offerPlayer, sum1 ,comp1, sum2, comp2)){
            g.printStr("Пользователь не согласился на сделку");
        }else{
            exchange(player, offerPlayer, sum1, sum2, comp1, comp2);
            g.printStr("Сделка успешно осуществлена");
        }

        chooseCommand(player);
    }

    public int[] offerDeal(Player player){
        g.printStr("Выберете компании для сделки: ");
        int[] command = g.chooseCompanyToExchange(player);
        return command;
    }

    public int[] chooseWhatToGet(Player player){
        return g.chooseWhatToGet(player);
    }

    public boolean acceptTheDeal(Player player1, Player player2, int sum1, int[] comp1, int sum2, int[] comp2){
        return g.acceptTheDeal(player1, player2, sum1, comp1, sum2, comp2);
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
                g.printStr("Игра закончена!");
                g.printStr("Проиграл Игрок" + (i+1));
                return true;
            }
        }
        return false;
    }

    public GameInterface getG() {
        return g;
    }

    public void setG(GameInterface g) {
        this.g = g;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public PlayingField getField() {
        return field;
    }
}
