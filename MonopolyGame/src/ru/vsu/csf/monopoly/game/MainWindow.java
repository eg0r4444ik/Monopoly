package ru.vsu.csf.monopoly.game;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static GraphicGame dp;

    public MainWindow(int playersCount) throws HeadlessException{
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1760, 910);
        dp = new GraphicGame(playersCount);
        this.add(dp);
    }

    public GraphicGame getDp() {
        return dp;
    }
}
