package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.Dice;
import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.PlayingField;
import ru.vsu.csf.monopoly.cells.Cell;
import ru.vsu.csf.monopoly.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DrawPanel extends JPanel {

    private Random random = new Random();
    private int currentCommand = 0;
    private Game game;
    private JButton rollDice = new JButton("Бросать кубики");
    private ArrayList<Dice> dices = new ArrayList<>();

    public DrawPanel(Game game) {
        this.game = game;
        //this.add(rollDice);
        rollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCommand = 1;
                repaint();
            }
        });
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(51, 49, 49));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (Cell c: game.getField().getCells()) {
            c.draw(g2d);
            c.drawPlayers(g2d);
        }

        for(Dice d : dices){
            d.draw(g2d);
        }
    }

    public void setDices(ArrayList<Dice> dices) {
        this.dices = dices;
    }

    public int getCurrentCommand() {
        return currentCommand;
    }
}
