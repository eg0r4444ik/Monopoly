package ru.vsu.csf.monopoly.clients;

import ru.vsu.csf.monopoly.game.Runnable;
import ru.vsu.csf.monopoly.game.Steps;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class CurrentGameState {

    private int currentPlayerIndex, player1Cash, player2Cash;
    private List<Integer> cells; // 0 - клетка никому не принадлежит, 1 - принадлежит 1 игроку, 2 - принадлежит 2 игроку
    private int[] dices;
    private int[] playersPos;
    private String string;
    private Steps step;

    public CurrentGameState(int currentPlayerIndex, int player1Cash, int player2Cash, List<Integer> cells, int[] dices, int[] playersPos, String string, Steps step) {
        this.currentPlayerIndex = currentPlayerIndex;
        this.player1Cash = player1Cash;
        this.player2Cash = player2Cash;
        this.cells = cells;
        this.dices = dices;
        this.playersPos = playersPos;
        this.string = string;
        this.step = step;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(currentPlayerIndex).append("_").append(player1Cash).append("_").append(player2Cash).append("_").append(string).append("_").append(step.toString()).append("_");
        sb.append(dices[0]).append("_").append(dices[1]).append("_").append(playersPos[0]).append("_").append(playersPos[1]).append("_").append("[");
        for(int c : cells){
            sb.append(c).append(";");
        }
        sb.delete(sb.length()-1, sb.length());
        sb.append("]");
        return sb.toString().trim();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getPlayer1Cash() {
        return player1Cash;
    }

    public int getPlayer2Cash() {
        return player2Cash;
    }

    public List<Integer> getCells() {
        return cells;
    }

    public int[] getDices() {
        return dices;
    }

    public int[] getPlayersPos() {
        return playersPos;
    }

    public String getString() {
        return string;
    }

    public Steps getStep() {
        return step;
    }
}
