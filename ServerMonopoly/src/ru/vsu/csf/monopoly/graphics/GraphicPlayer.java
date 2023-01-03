package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.game.PlayingField;
import ru.vsu.csf.monopoly.objects.Player;
import ru.vsu.csf.monopoly.objects.cells.Cell;
import ru.vsu.csf.monopoly.objects.cells.Company;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicPlayer extends Player {

    protected int x, y, size;

    public GraphicPlayer(int x, int y, int size, Color color, int currentPosition, int cash, PlayingField playingField, List<Company> myCompanies, int countOfDouble) {
        super(color, currentPosition, cash, playingField, myCompanies, countOfDouble);
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(Graphics2D g, int number){
        if(cash >= 0) {
            DrawUtils.drawPlayer(g, x, y, size, new Color(94, 89, 89), number, cash);
        }else{
            DrawUtils.drawPlayer(g, x, y, size, new Color(51, 49, 49), number, cash);
        }
    }

    public void drawActive(Graphics2D g, int number){
        DrawUtils.drawActivePlayer(g, x, y, size, color, number, cash);
    }
}
