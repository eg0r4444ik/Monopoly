package ru.vsu.csf.monopoly.objects.cells;

import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.objects.Player;

import java.io.IOException;

public interface CellActions {
    void makeMove(Player player, Game game) throws IOException;
}
