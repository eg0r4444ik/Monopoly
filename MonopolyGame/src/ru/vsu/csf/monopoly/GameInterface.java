package ru.vsu.csf.monopoly;

import ru.vsu.csf.monopoly.cells.Casino;
import ru.vsu.csf.monopoly.cells.Company;
import ru.vsu.csf.monopoly.cells.Rialto;
import ru.vsu.csf.monopoly.player.Player;

import java.util.List;

public interface GameInterface {
    int chooseCommand(Player player, int index);
    int chooseCompanyCommand(Company company);
    int chooseCasinoCommand(Casino casino);
    int chooseRialtoCommand(Rialto rialto);
    int chooseCompanyToBuild(Player player);
    int[] chooseCompanyToExchange(Player player);
    int choosePlayerToOffer(List<Player> players, Player player);
    int chooseSum(Player player);
    int[] chooseWhatToGet(Player player);
    boolean acceptTheDeal(Player player1, Player player2, int sum1, int[] comp1, int sum2, int[] comp2);
    void rollDice(int dice1, int dice2);
    void rollOneDice(int dice);
    int[] scanThreeNum();
    void printStr(String str);
}
