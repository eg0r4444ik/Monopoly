package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.objects.Player;

public interface CellActions {
    void makeMove(Player player, Game game);
}
