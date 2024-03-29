package ru.vsu.csf.monopoly.game;

import ru.vsu.csf.monopoly.cells.*;
import ru.vsu.csf.monopoly.graphics.GraphicPlayer;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private List<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.ORANGE, Color.GREEN);
    private Runnable runnable;
    private int playersCount;
    private List<Player> players;
    private PlayingField field;


    public Game(int playersCount, Runnable runnable) {
        this.runnable = runnable;
        this.playersCount = playersCount;
        this.field = new PlayingField();
        field.generateField();
        players = new ArrayList<>();
        int s = 0;
        for (int i = 0; i < playersCount; i++) {
            GraphicPlayer player = new GraphicPlayer(0, s, 200, colors.get(i), 0, 15000, field, new ArrayList<>(), 0);
            s += 200;
            players.add(player);
        }
        Cell start = field.getCells().get(0);
        ArrayList<Player> p = new ArrayList<>();
        for (Player pl : players) {
            p.add(pl);
        }
        start.setPlayers(p);
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
            runnable.render(dice, Runnable.Steps.DRAW_DICE, "");

            Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            List<Player> p = currentCell.getPlayers();
            p.remove(player);
            currentCell.setPlayers(p);
            player.go(dice);

            currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            p = currentCell.getPlayers();
            p.add(player);
            currentCell.setPlayers(p);
            currentCell.makeMove(player, this);
        }
    }


    public void buildCompany(Player player, Company company){
        if(player.getCompaniesToBuild().size() == 0){
            runnable.render(null, Runnable.Steps.DRAW_STRING, "У вас недостаточно компаний, чтобы построить филиал");
        } else {
            if(player.getCash() >= 1500) {
                player.build(company);
            } else{
                runnable.render(null, Runnable.Steps.DRAW_STRING, "У вас недостаточно средств для постройки");
            }
        }
    }

//    public void offer(Player player){
//        int command = g.choosePlayerToOffer(players, player);
//        while(command < 1 || command > players.size()){
//            g.printStr("Введенная команда неверная попробуйте заново");
//            command = g.choosePlayerToOffer(players, player);
//        }
//        Player offerPlayer = players.get(command-1);
//
//        g.printStr("Введите сумму, которую хотите предложить от 0 до " + player.getCash());
//        int sum1 = g.chooseSum(player);
//        while(sum1 > player.getCash()){
//            g.printStr("Вы не располагаете таким бюджетом, попробуйте еще раз");
//            sum1 = g.chooseSum(player);
//        }
//        int[] comp1 = offerDeal(player);
//
//
//        g.printStr("Введите сумму, которую хотите получить от 0 до " + offerPlayer.getCash());
//        int sum2 = g.chooseSum(offerPlayer);
//        while(sum2 > offerPlayer.getCash()){
//            g.printStr("Данный игрок не располагает таким бюджетом, попробуйте еще раз");
//            sum2 = g.chooseSum(offerPlayer);
//        }
//        int[] comp2 = chooseWhatToGet(offerPlayer);
//
//        System.out.println();
//        g.printStr("Игрок" + command + ":");
//        if(!acceptTheDeal(player, offerPlayer, sum1 ,comp1, sum2, comp2)){
//            g.printStr("Пользователь не согласился на сделку");
//        }else{
//            exchange(player, offerPlayer, sum1, sum2, comp1, comp2);
//            g.printStr("Сделка успешно осуществлена");
//        }
//
//        chooseCommand(player);
//    }
//
//    public int[] offerDeal(Player player){
//        g.printStr("Выберете компании для сделки: ");
//        int[] command = g.chooseCompanyToExchange(player);
//        return command;
//    }
//
//    public int[] chooseWhatToGet(Player player){
//        return g.chooseWhatToGet(player);
//    }
//
//    public boolean acceptTheDeal(Player player1, Player player2, int sum1, int[] comp1, int sum2, int[] comp2){
//        return g.acceptTheDeal(player1, player2, sum1, comp1, sum2, comp2);
//    }
//
//    public void exchange(Player player1, Player player2, int sum1, int sum2, int[] comp1, int[] comp2){
//        player1.setCash(player1.getCash() - sum1 + sum2);
//        player2.setCash(player2.getCash() - sum2 + sum1);
//
//        List<Company> p1Company = player1.getMyCompanies();
//        List<Company> p2Company = player2.getMyCompanies();
//
//        for(int i = 0; i < comp1.length; i++){
//            Company c = p1Company.get(comp1[i]);
//            p2Company.add(c);
//            p1Company.remove(c);
//        }
//
//        for(int i = 0; i < comp2.length; i++){
//            Company c = p2Company.get(comp2[i]);
//            p1Company.add(c);
//            p2Company.remove(c);
//        }
//
//        player1.setMyCompanies(p1Company);
//        player2.setMyCompanies(p2Company);
//    }


    public List<Player> getPlayers() {
        return players;
    }

    public PlayingField getField() {
        return field;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void setField(PlayingField field) {
        this.field = field;
    }
}
