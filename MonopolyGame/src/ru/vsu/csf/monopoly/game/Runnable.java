package ru.vsu.csf.monopoly.game;

public interface Runnable {

    public void render(int[] dices, GraphicGame.Steps step, String str);

    public enum Steps {
        CHOOSE_COMMAND,
        CHOOSE_COMPANY_COMMAND,
        DRAW_STRING,
        DRAW_DICE,
        CHOOSE_CASINO_COMMAND,
        CHOOSE_RIALTO_COMMAND
    }
}
