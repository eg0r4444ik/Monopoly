package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.Dice;
import ru.vsu.csf.monopoly.Game;
import ru.vsu.csf.monopoly.PlayingField;
import ru.vsu.csf.monopoly.cells.Cell;
import ru.vsu.csf.monopoly.cells.Company;
import ru.vsu.csf.monopoly.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DrawPanel extends JPanel {

    private Random random = new Random();
    private int currentCommand = 0;
    private Game game;
    private Button rollDice, buildCompany, offerExchange, buyCompany, refuseToBuy;
    private List<Button> buttons;
    private ArrayList<Dice> dices;
    private Timer t;

    public DrawPanel(Game game) {
        this.game = game;
        dices = new ArrayList<>();
        buttons = new ArrayList<>();
        rollDice = new Button(800, 150, 300, 70, Color.GREEN, "Бросать кубики");
        buildCompany = new Button(800, 250, 300, 70, Color.ORANGE, "Построить филиал");
        offerExchange = new Button(800, 350, 300, 70, Color.PINK, "Предложить обмен");
        buyCompany = new Button(600, 150, 300, 70, Color.GREEN, "Купить компанию");
        refuseToBuy = new Button(1000, 150, 300, 70, new Color(248, 46, 46), "Отказаться");

        t = new Timer(100, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dices.clear();
                dices.add(new Dice(800, 400, 70, random.nextInt(5)+1));
                dices.add(new Dice(1000, 400, 70, random.nextInt(5)+1));
                repaint();
            }
        });
        repaint();
    }

    public int chooseCommand(Player player) {
        buttons.add(rollDice);
        buttons.add(buildCompany);
        buttons.add(offerExchange);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() < rollDice.getX() + rollDice.getSizeX() / 2 && e.getX() > rollDice.getX() - rollDice.getSizeX() / 2 && e.getY() < rollDice.getY() + rollDice.getSizeY() / 2 && e.getY() > rollDice.getY() - rollDice.getSizeY() / 2) {
                    currentCommand = 1;
                    buttons.remove(rollDice);
                    buttons.remove(buildCompany);
                    buttons.remove(offerExchange);
                    t.start();
                }
                if (e.getX() < buildCompany.getX() + buildCompany.getSizeX() / 2 && e.getX() > buildCompany.getX() - buildCompany.getSizeX() / 2 && e.getY() < buildCompany.getY() + buildCompany.getSizeY() / 2 && e.getY() > buildCompany.getY() - buildCompany.getSizeY() / 2) {
                    currentCommand = 2;
                    buttons.remove(rollDice);
                    buttons.remove(buildCompany);
                    buttons.remove(offerExchange);
                }
                if (e.getX() < offerExchange.getX() + offerExchange.getSizeX() / 2 && e.getX() > offerExchange.getX() - offerExchange.getSizeX() / 2 && e.getY() < offerExchange.getY() + offerExchange.getSizeY() / 2 && e.getY() > offerExchange.getY() - offerExchange.getSizeY() / 2) {
                    currentCommand = 3;
                    buttons.remove(rollDice);
                    buttons.remove(buildCompany);
                    buttons.remove(offerExchange);
                }
                revalidate();
                repaint();
            }
        });
        while(currentCommand == 0){}
        int c = currentCommand;
        currentCommand = 0;
        return c;
    }

    public void rollDice(int dice1, int dice2){
        Timer t1 = new Timer(100, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.stop();
                dices.clear();
                dices.add(new Dice(400, 400, 70, dice1));
                dices.add(new Dice(600, 400, 70, dice2));
                revalidate();
                repaint();
            }
        });
        t1.start();
    }

    public int chooseCompanyCommand(Company company) {
        buttons.add(buyCompany);
        buttons.add(refuseToBuy);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() < buyCompany.getX() + buyCompany.getSizeX() / 2 && e.getX() > buyCompany.getX() - buyCompany.getSizeX() / 2 && e.getY() < buyCompany.getY() + buyCompany.getSizeY() / 2 && e.getY() > buyCompany.getY() - buyCompany.getSizeY() / 2) {
                    currentCommand = 1;
                    buttons.remove(buyCompany);
                    buttons.remove(refuseToBuy);
                }
                if (e.getX() < refuseToBuy.getX() + refuseToBuy.getSizeX() / 2 && e.getX() > refuseToBuy.getX() - refuseToBuy.getSizeX() / 2 && e.getY() < refuseToBuy.getY() + refuseToBuy.getSizeY() / 2 && e.getY() > refuseToBuy.getY() - refuseToBuy.getSizeY() / 2) {
                    currentCommand = 2;
                    buttons.remove(buyCompany);
                    buttons.remove(refuseToBuy);
                }
                repaint();
            }
        });
        while(currentCommand == 0){}
        int c = currentCommand;
        currentCommand = 0;
        return c;
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

        for(Button b : buttons){
            b.draw(g2d);
        }
    }

    public void setDices(ArrayList<Dice> dices) {
        this.dices = dices;
        repaint();
    }

    public int getCurrentCommand() {
        return currentCommand;
    }

    public void update() {
        this.removeAll();
        this.repaint();
        this.revalidate();
    }
}
