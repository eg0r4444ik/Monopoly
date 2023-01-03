package ru.vsu.csf.monopoly.game;

import ru.vsu.csf.monopoly.clients.CurrentGameState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static CurrentGameState parseCurrState(String command){
        String[] state = command.split("_");
        int currentPlayerIndex = Integer.parseInt(state[0]);
        int player1Cash = Integer.parseInt(state[1]);
        int player2Cash = Integer.parseInt(state[2]);
        String string = state[3];
        Steps step = Steps.getStep(state[4]);
        int[] dices = new int[]{Integer.parseInt(state[5]), Integer.parseInt(state[6])};
        int[] playersPos = new int[]{Integer.parseInt(state[7]), Integer.parseInt(state[8])};
        List<Integer> cells = new ArrayList<>();
        String[] cell = String.copyValueOf(state[9].toCharArray(), 1, state[9].length()-2).split(";");
        for(String c : cell){
            cells.add(Integer.parseInt(c));
        }

        return new CurrentGameState(currentPlayerIndex, player1Cash, player2Cash, cells, dices, playersPos, string, step);
    }

    public static void defineGameStep(String command, Game game) throws IOException {
        switch (command){
            case ("make move"):
                game.makeMove();
                return;
            case ("buy company"):
                game.buyCompany();
                return;
            case ("next"):
                game.next();
                return;
            case ("build company"):
                game.buyCompany();
            case ("play in casino 1"):
                game.playInCasino(1);
            case ("play in casino 2"):
                game.playInCasino(2);
            case ("pay for exit"):
                game.payForExit();
            case ("wait for exit"):
                game.waitForExit();
            default:
                return;
        }
    }

}
