package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.game.GraphicGame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;

public class MainWindow extends JFrame {
    GraphicGame dp;

    public MainWindow(BufferedWriter writer) throws HeadlessException, IOException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1760, 910);
        this.dp = new GraphicGame(writer);
        this.add(dp);
    }

    public GraphicGame getDp() {
        return dp;
    }
}
