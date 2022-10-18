package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.player.Player;

public interface CellActions {
    void makeMove(Player player, Game game);
}
