package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.PlayingField;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private DrawPanel dp;

    public MainWindow(Game game) throws HeadlessException{
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1760, 910);
        dp = new DrawPanel(game);
        this.add(dp);
    }

    public DrawPanel getDp() {
        return dp;
    }
}
