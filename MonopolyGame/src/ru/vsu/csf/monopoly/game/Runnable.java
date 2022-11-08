package ru.vsu.csf.monopoly.game;

import ru.vsu.csf.monopoly.player.Player;

public interface Runnable {

    public void render(int[] dices, GraphicGame.Steps step, PlayingField field, String str);

}
