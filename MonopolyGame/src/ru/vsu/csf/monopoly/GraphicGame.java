package ru.vsu.csf.monopoly;

import ru.vsu.csf.monopoly.cells.Casino;
import ru.vsu.csf.monopoly.cells.Company;
import ru.vsu.csf.monopoly.cells.Rialto;
import ru.vsu.csf.monopoly.graphics.DrawPanel;
import ru.vsu.csf.monopoly.graphics.MainWindow;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GraphicGame implements GameInterface{

    private DrawPanel dp;

    public GraphicGame() {

    }

    public int chooseCommand(Player player, int index) {
        return 3;
    }

    public int chooseCompanyCommand(Company company) {
        return 1;
    }

    public int chooseCasinoCommand(Casino casino) {
        return 0;
    }

    public int chooseRialtoCommand(Rialto rialto) {
        return 0;
    }

    public int chooseCompanyToBuild(Player player) {
        return 0;
    }

    public int[] chooseCompanyToExchange(Player player) {
        return new int[0];
    }

    public int choosePlayerToOffer(List<Player> players, Player player) {
        return 0;
    }

    public int chooseSum(Player player) {
        return 0;
    }

    public int[] chooseWhatToGet(Player player) {
        return new int[0];
    }

    public boolean acceptTheDeal(Player player1, Player player2, int sum1, int[] comp1, int sum2, int[] comp2) {
        return false;
    }

    public void rollDice(int dice1, int dice2) {
//        ArrayList<Dice> dices = new ArrayList<>();
//        dices.add(new Dice(50, 60, 30, dice1));
//        dices.add(new Dice(50, 60, 30, dice2));
//        dp.setDices(dices);
        return;
    }

    public void rollOneDice(int dice) {

    }

    public int[] scanThreeNum() {
        return new int[0];
    }

    public void printStr(String str) {

    }
}
