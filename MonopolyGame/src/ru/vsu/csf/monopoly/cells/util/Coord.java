package ru.vsu.csf.monopoly.cells.util;

public class Coord {
    final double x, y;

    Coord move(Direction d){
        return new Coord(x + d.x, y + d.y);
    }

    Coord move(Direction d, double c){
        return new Coord(x + d.x*c, y + d.y*c);
    }

    public Coord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "ru.vsu.csf.monopoly.cells.util.Coord{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
