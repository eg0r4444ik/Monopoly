package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.graphics.DrawUtils;

import java.awt.*;

public class Dice {
    private int x, y, size, value;

    public Dice(int x, int y, int size, int value) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.value = value;
    }

    public void draw(Graphics2D g){
        DrawUtils.drawDice(g, x, y, value, size);
    }
}
