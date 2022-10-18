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

    @Override
    public int chooseCommand(Player player, int index) {
        return dp.chooseCommand(player);
    }

    @Override
    public int chooseCompanyCommand(Company company) {
        return 1;
    }

    @Override
    public int chooseCasinoCommand(Casino casino) {
        return 0;
    }

    @Override
    public int chooseRialtoCommand(Rialto rialto) {
        return 0;
    }

    @Override
    public int chooseCompanyToBuild(Player player) {
        return 0;
    }

    @Override
    public int[] chooseCompanyToExchange(Player player) {
        return new int[0];
    }

    @Override
    public int choosePlayerToOffer(List<Player> players, Player player) {
        return 0;
    }

    @Override
    public int chooseSum(Player player) {
        return 0;
    }

    @Override
    public int[] chooseWhatToGet(Player player) {
        return new int[0];
    }

    @Override
    public boolean acceptTheDeal(Player player1, Player player2, int sum1, int[] comp1, int sum2, int[] comp2) {
        return false;
    }

    @Override
    public void rollDice(int dice1, int dice2) {
        dp.rollDice(dice1, dice2);
    }

    @Override
    public void rollOneDice(int dice) {

    }

    @Override
    public int[] scanThreeNum() {
        return new int[0];
    }

    @Override
    public void printStr(String str) {

    }

    @Override
    public void setDp(DrawPanel dp) {
        this.dp = dp;
    }
}
