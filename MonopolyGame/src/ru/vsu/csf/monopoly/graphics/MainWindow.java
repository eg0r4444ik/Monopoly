package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.game.GraphicGame;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    GraphicGame dp;

    public MainWindow(int playersCount) throws HeadlessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1760, 910);
        this.dp = new GraphicGame(playersCount);
        this.add(dp);
    }
}
