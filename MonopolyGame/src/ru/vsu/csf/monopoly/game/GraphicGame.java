package ru.vsu.csf.monopoly.game;

import ru.vsu.csf.monopoly.cells.Casino;
import ru.vsu.csf.monopoly.cells.Cell;
import ru.vsu.csf.monopoly.cells.Company;
import ru.vsu.csf.monopoly.cells.Rialto;
import ru.vsu.csf.monopoly.objects.Dice;
import ru.vsu.csf.monopoly.graphics.DrawUtils;
import ru.vsu.csf.monopoly.objects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphicGame extends JPanel implements Runnable {

    private Game game;
    private List<JButton> buttons;
    private List<Dice> dices;
    private int[] dice;
    private int currentPlayerIndex = 0;
    private boolean canBuildCompany = true;
    Steps step = Steps.CHOOSE_COMMAND;
    private Random random = new Random();
    private final JButton rollDice, buildCompany, refuseToBuild, buyCompany, refuseToBuy, okButton, playInCasino, refuseToPlay;
    private final JButton firstCasinoChoice, secondCasinoChoice, payForExit, waitForExit, onlyPayForExit;
    private Player currentPlayer;
    private String currentString;
    private Company currentCompany;

    @Override
    public void render(int[] dices, Steps step, String str) {
        if(str != null) {
            this.currentString = str;
        }
        if (dices != null) {
            this.dice = dices;
        }
        this.step = step;
        defineStep();
        repaint();
    }

    public GraphicGame(int playersCount) {
        game = new Game(playersCount, this);

        currentString = "";
        currentCompany = null;
        dice = new int[2];
        currentPlayer = game.getPlayers().get(currentPlayerIndex);

        dices = new ArrayList<>();
        buttons = new ArrayList<>();

        this.setLayout(null);

        rollDice = new JButton("Сделать ход");
        rollDice.setBackground(Color.GREEN);
        rollDice.setBounds(650, 150, 300,70);
        this.add(rollDice);
        buildCompany = new JButton("Построить филиал");
        buildCompany.setBackground(Color.ORANGE);
        buildCompany.setBounds(650, 150, 300, 70);
        this.add(buildCompany);
        refuseToBuild = new JButton("Отказаться");
        refuseToBuild.setBackground(Color.PINK);
        refuseToBuild.setBounds(650, 250, 300, 70);
        this.add(refuseToBuild);
        buyCompany = new JButton("Купить компанию");
        buyCompany.setBackground(Color.GREEN);
        buyCompany.setBounds(450, 150, 300, 70);
        this.add(buyCompany);
        refuseToBuy = new JButton("Отказаться");
        refuseToBuy.setBackground(new Color(248, 46, 46));
        refuseToBuy.setBounds(850, 150, 300, 70);
        this.add(refuseToBuy);
        playInCasino = new JButton("Сыграть в казино");
        playInCasino.setBackground(Color.GREEN);
        playInCasino.setBounds(450, 150, 300, 70);
        this.add(playInCasino);
        refuseToPlay = new JButton( "Отказаться");
        refuseToPlay.setBackground(new Color(248, 46, 46));
        refuseToPlay.setBounds(850, 150, 300, 70);
        this.add(refuseToPlay);

        firstCasinoChoice = new JButton("Четное число");
        firstCasinoChoice.setBackground(Color.ORANGE);
        firstCasinoChoice.setBounds(450, 150, 300, 70);
        this.add(firstCasinoChoice);
        secondCasinoChoice = new JButton( "Нечетное число");
        secondCasinoChoice.setBackground(Color.PINK);
        secondCasinoChoice.setBounds(850, 150, 300, 70);
        this.add(secondCasinoChoice);
        payForExit = new JButton( "Заплатить 500");
        payForExit.setBackground(Color.ORANGE);
        payForExit.setBounds(450, 150, 300, 70);
        this.add(payForExit);
        waitForExit = new JButton( "Бросать кубики");
        waitForExit.setBackground(Color.PINK);
        waitForExit.setBounds(850, 150, 300, 70);
        this.add(waitForExit);
        onlyPayForExit = new JButton( "Заплатить 500");
        onlyPayForExit.setBackground(Color.ORANGE);
        onlyPayForExit.setBounds(650, 150, 300, 70);
        this.add(onlyPayForExit);

        okButton = new JButton( "OK");
        okButton.setBackground(Color.GREEN);
        okButton.setBounds(725, 450, 200, 50);
        this.add(okButton);

        buttons.add(rollDice);


        rollDice.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.clear();
                game.makeMove(currentPlayer);
                repaint();
            }
        });

        buildCompany.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                game.buildCompany(currentPlayer, currentCompany);
                canBuildCompany = false;
                currentCompany = null;
                buttons.clear();
                render(null, Steps.CHOOSE_COMMAND, "");
                repaint();
            }
        });
        refuseToBuild.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                buttons.clear();
                render(null, Steps.CHOOSE_COMMAND, "");
                repaint();
            }
        });
        buyCompany.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                currentPlayer.buyCompany();
                dices.clear();
                buttons.clear();
                repaint();
                next();
            }
        });
        refuseToBuy.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                buttons.clear();
                dices.clear();
                repaint();
                next();
            }
        });
        playInCasino.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                buttons.clear();
                dices.clear();
                playInCasino();
                repaint();
            }
        });
        refuseToPlay.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                buttons.clear();
                dices.clear();
                repaint();
                next();
            }
        });

        firstCasinoChoice.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                if (game.getField().getCells().get(currentPlayer.getCurrentPosition()) instanceof Casino) {
                    Casino casino = (Casino) game.getField().getCells().get(currentPlayer.getCurrentPosition());
                    dices.add(new Dice(850, 620, 120, casino.play(1000, 2, currentPlayer, game)));
                }
                repaint();
            }
        });
        secondCasinoChoice.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                if (game.getField().getCells().get(currentPlayer.getCurrentPosition()) instanceof Casino) {
                    Casino casino = (Casino) game.getField().getCells().get(currentPlayer.getCurrentPosition());
                    dices.add(new Dice(850, 620, 120, casino.play(1000, 1, currentPlayer, game)));
                }
                repaint();
            }
        });
        payForExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                if (game.getField().getCells().get(currentPlayer.getCurrentPosition()) instanceof Rialto) {
                    Rialto rialto = (Rialto) game.getField().getCells().get(currentPlayer.getCurrentPosition());
                    rialto.payToExit(currentPlayer);
                    currentPlayer.setCountOfThrowsInPrison(0);
                }
                repaint();
                chooseCommand(currentPlayer);
            }
        });
        waitForExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                if (game.getField().getCells().get(currentPlayer.getCurrentPosition()) instanceof Rialto) {
                    Rialto rialto = (Rialto) game.getField().getCells().get(currentPlayer.getCurrentPosition());
                    render(rialto.rollDice(currentPlayer, game), Steps.DRAW_DICE, null);
                }
                repaint();
            }
        });
        onlyPayForExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                if (game.getField().getCells().get(currentPlayer.getCurrentPosition()) instanceof Rialto) {
                    Rialto rialto = (Rialto) game.getField().getCells().get(currentPlayer.getCurrentPosition());
                    rialto.payToExit(currentPlayer);
                    currentPlayer.setCountOfThrowsInPrison(0);
                }
                repaint();
                chooseCommand(currentPlayer);
            }
        });

        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.clear();
                dices.clear();
                currentString = "";
                repaint();
                next();
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isCompanyClicked(e) && canBuildCompany){
                    Company company = whatCompanyClicked(e);
                    if(currentPlayer.getMyCompanies().contains(company) && company.getCountOfBuildings() <= 2){
                        currentCompany = company;
                        buttons.clear();
                        buttons.add(buildCompany);
                        buttons.add(refuseToBuild);
                    }
                }
                repaint();
                revalidate();
            }
        });

    }

    private void defineStep() {
        if (step == Steps.CHOOSE_COMMAND) {
            chooseCommand(currentPlayer);
        } else if (step == Steps.CHOOSE_COMPANY_COMMAND) {
            chooseCompanyCommand();
        } else if (step == Steps.DRAW_STRING) {
            drawString();
        } else if (step == Steps.DRAW_DICE) {
            drawDice();
        } else if (step == Steps.CHOOSE_CASINO_COMMAND) {
            chooseCasinoCommand();
        } else if (step == Steps.CHOOSE_RIALTO_COMMAND) {
            chooseRialtoCommand();
        }
        repaint();
    }

    private void chooseCommand(Player player) {
        buttons.add(rollDice);
        repaint();
    }

    private void chooseCompanyCommand() {
        buttons.add(buyCompany);
        buttons.add(refuseToBuy);
        repaint();
    }

    private void chooseCasinoCommand() {
        buttons.add(playInCasino);
        buttons.add(refuseToPlay);
        repaint();
    }

    private void chooseRialtoCommand() {
        if (currentPlayer.getCountOfThrowsInPrison() == 3) {
            buttons.add(onlyPayForExit);
        } else {
            buttons.add(payForExit);
            buttons.add(waitForExit);
        }
        repaint();
    }


    private void drawString() {
        buttons.add(okButton);
        repaint();
    }

    private void drawDice() {
        dices.add(new Dice(700, 600, 100, dice[0]));
        dices.add(new Dice(1000, 600, 100, dice[1]));
        repaint();
    }

    private void playInCasino() {
        buttons.clear();
        buttons.add(firstCasinoChoice);
        buttons.add(secondCasinoChoice);
        repaint();
    }

    private void next(){
        if (dice[0] != dice[1]) {
            canBuildCompany = true;
            currentPlayerIndex++;
        }
        currentPlayer = game.getPlayers().get(currentPlayerIndex % game.getPlayers().size());
        repaint();
        chooseCommand(currentPlayer);
    }

    private boolean isCompanyClicked(MouseEvent e){
        for(Cell cell : game.getField().getCells()){
            if(cell instanceof Company && e.getX() < cell.getX() + cell.getSizeX() / 2 && e.getX() > cell.getX() - cell.getSizeX() / 2 && e.getY() < cell.getY() + cell.getSizeY() / 2 && e.getY() > cell.getY() - cell.getSizeY() / 2){
                return true;
            }
        }
        return false;
    }

    private Company whatCompanyClicked(MouseEvent e){
        for(Cell cell : game.getField().getCells()){
            if(cell instanceof Company && e.getX() < cell.getX() + cell.getSizeX() / 2 && e.getX() > cell.getX() - cell.getSizeX() / 2 && e.getY() < cell.getY() + cell.getSizeY() / 2 && e.getY() > cell.getY() - cell.getSizeY() / 2){
                return (Company)cell;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(51, 49, 49));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (Cell c : game.getField().getCells()) {
            c.draw(g2d);
            c.drawPlayers(g2d);
        }

        for (Dice d : dices) {
            d.draw(g2d);
        }

        rollDice.setVisible(false);
        buildCompany.setVisible(false);
        refuseToBuild.setVisible(false);
        buyCompany.setVisible(false);
        refuseToBuy.setVisible(false);
        okButton.setVisible(false);
        playInCasino.setVisible(false);
        refuseToPlay.setVisible(false);
        firstCasinoChoice.setVisible(false);
        secondCasinoChoice.setVisible(false);
        payForExit.setVisible(false);
        waitForExit.setVisible(false);
        onlyPayForExit.setVisible(false);
        for (JButton b : buttons) {
            b.setVisible(true);
        }

        for (int j = 0; j < game.getPlayers().size(); j++) {
            if (game.getPlayers().get(j) == currentPlayer) {
                game.getPlayers().get(j).drawActive(g2d, j + 1);
            } else {
                game.getPlayers().get(j).draw(g2d, j + 1);
            }
        }

        if (currentString != null && !currentString.equals("")) {
            DrawUtils.drawStr(g2d, currentString);
        }


        for (int i = 0; i < game.getPlayers().size(); i++) {
            Player player = game.getPlayers().get(i);
            if(player.getCash() < 0){
                for(Company company : player.getMyCompanies()){
                    company.setBought(false);
                    if(company.getCountOfBuildings() != 0) {
                        company.setSupplyPrice(company.getSupplyPrice() / (4 * company.getCountOfBuildings()));
                        company.setCountOfBuildings(0);
                    }
                    company.setColor(Color.WHITE);
                }
                game.getPlayers().remove(i);
            }
            if(game.getPlayers().size() == 1){
                g2d.setColor(new Color(51, 49, 49));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                game.getPlayers().get(0).setColor(new Color(189, 171, 61, 240));
                game.getPlayers().get(0).drawActive(g2d, 1);
                buttons.clear();
                g2d.setColor(Color.WHITE);
                DrawUtils.drawCenteredString(g, "Победа!", new Rectangle(0, 0, getWidth(), getHeight()), new Font("TimesRoman", Font.PLAIN, getHeight()/8));
            }
        }
    }
}
