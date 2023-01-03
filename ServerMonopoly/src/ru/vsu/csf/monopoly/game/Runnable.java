package ru.vsu.csf.monopoly.game;

import java.util.List;

public interface Runnable {

    public void render(int[] dices, Steps step, String str, int[] playersPos, List<Integer> cells,
                       int currentPlayerIndex, int player1Cash, int player2Cash);
}
