package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.cells.util.Coord;
import ru.vsu.csf.monopoly.player.Player;

public class Cell {

    private Coord coord;
    private int size;


    public Cell( Coord coord, int size) {
        this.coord = coord;
        this.size = size;
    }

    public void makeMove(Player player, Game game){
        if (this instanceof Company) {
            Company company = (Company) this;
            company.makeMove(player, game);
        }

        if (this instanceof Chance) {
            Chance chance = (Chance) this;
            chance.makeMove(player, game);
        }

        if (this instanceof Start) {
            Start start = (Start) this;
            game.printStr("Вы получаете 2000 за прохождение круга");
            start.getMoney(player);
            game.printStr("Ваш бюджет: " + player.getCash());
        }

        if (this instanceof Rialto) {
            Rialto rialto = (Rialto) this;
            rialto.makeMove(player, game);
        }

        if (this instanceof Prison) {
            Prison prison = (Prison) this;
            game.printStr("Вы отправляетесь в тюрьму");
            prison.visitPrison(player);
            player.setPrisonForVisit(false);
        }

        if (this instanceof Casino) {
            Casino casino = (Casino) this;
            casino.makeMove(player, game);
        }
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
