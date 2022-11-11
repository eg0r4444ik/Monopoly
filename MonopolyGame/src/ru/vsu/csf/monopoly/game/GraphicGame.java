package ru.vsu.csf.monopoly.game;

import ru.vsu.csf.monopoly.cells.Casino;
import ru.vsu.csf.monopoly.cells.Cell;
import ru.vsu.csf.monopoly.cells.Company;
import ru.vsu.csf.monopoly.cells.Rialto;
import ru.vsu.csf.monopoly.graphics.Button;
import ru.vsu.csf.monopoly.graphics.Dice;
import ru.vsu.csf.monopoly.graphics.DrawUtils;
import ru.vsu.csf.monopoly.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GraphicGame extends JPanel implements Runnable {

    public enum Steps {
        CHOOSE_COMMAND,
        CHOOSE_COMPANY_COMMAND,
        DRAW_STRING,
        DRAW_DICE,
        CHOOSE_CASINO_COMMAND,
        CHOOSE_RIALTO_COMMAND
    }

    private Game game;
    private List<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.ORANGE, Color.GREEN);
    private List<Button> buttons;
    private List<Dice> dices;
    private int[] dice;
    private List<Player> players;
    private int playersCount, currentPlayerIndex = 0;
    private boolean canBuildCompany = true;
    Steps step = Steps.CHOOSE_COMMAND;
    private PlayingField field;
    private Random random = new Random();
    private final Button rollDice, buildCompany, refuseToBuild, buyCompany, refuseToBuy, okButton, playInCasino, refuseToPlay;
    private final Button firstCasinoChoice, secondCasinoChoice, payForExit, waitForExit, onlyPayForExit;
    private Player currentPlayer;
    private String currentString;
    private Company currentCompany;

    @Override
    public void render(int[] dices, Steps step, PlayingField field, String str) {
        this.field = field;
        if(str != null) {
            this.currentString = str;
        }
        if (dices != null) {
            this.dice = dices;
        }
        this.step = step;
        defineStep();
    }

    public GraphicGame(int playersCount) {
        this.playersCount = playersCount;
        this.field = new PlayingField();
        field.generateField();
        players = new ArrayList<>();
        currentString = "";
        currentCompany = null;
        dice = new int[2];
        int s = 0;
        for (int i = 0; i < playersCount; i++) {
            Player player = new Player(0, s, 200, colors.get(i), 0, 15000, field, new ArrayList<>(), 0);
            s += 200;
            players.add(player);
        }
        Cell start = field.getCells().get(0);
        ArrayList<Player> p = new ArrayList<>();
        for (Player pl : players) {
            p.add(pl);
        }
        start.setPlayers(p);
        currentPlayer = players.get(currentPlayerIndex);
        this.game = new Game(playersCount, field, this, players);


        dices = new ArrayList<>();
        buttons = new ArrayList<>();
        rollDice = new Button(800, 150, 300, 70, Color.GREEN, "Сделать ход");
        buildCompany = new Button(800, 150, 300, 70, Color.ORANGE, "Построить филиал");
        refuseToBuild = new Button(800, 250, 300, 70, Color.PINK, "Отказаться");
        buyCompany = new Button(600, 150, 300, 70, Color.GREEN, "Купить компанию");
        refuseToBuy = new Button(1000, 150, 300, 70, new Color(248, 46, 46), "Отказаться");
        playInCasino = new Button(600, 150, 300, 70, Color.GREEN, "Сыграть в казино");
        refuseToPlay = new Button(1000, 150, 300, 70, new Color(248, 46, 46), "Отказаться");

        firstCasinoChoice = new Button(600, 150, 300, 70, Color.ORANGE, "Четное число");
        secondCasinoChoice = new Button(1000, 150, 300, 70, Color.PINK, "Нечетное число");
        payForExit = new Button(600, 150, 300, 70, Color.ORANGE, "Заплатить 500");
        waitForExit = new Button(1000, 150, 300, 70, Color.PINK, "Бросать кубики");
        onlyPayForExit = new Button(800, 150, 300, 70, Color.ORANGE, "Заплатить 500");

        okButton = new Button(825, 500, 200, 50, Color.GREEN, "OK");

        buttons.add(rollDice);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isButtonClicked(e, rollDice)) {
                    buttons.clear();
                    repaint();
                    game.makeMove(currentPlayer);
                } else if(isCompanyClicked(e) && canBuildCompany){
                    Company company = whatCompanyClicked(e);
                    if(currentPlayer.getMyCompanies().contains(company) && company.getCountOfBuildings() <= 2){
                        currentCompany = company;
                        buttons.clear();
                        buttons.add(buildCompany);
                        buttons.add(refuseToBuild);
                    }
                    repaint();
                } else if (isButtonClicked(e, buildCompany)) {
                    game.buildCompany(currentPlayer, currentCompany);
                    canBuildCompany = false;
                    currentCompany = null;
                    buttons.clear();
                    render(null, Steps.CHOOSE_COMMAND, field, "");
                    repaint();
                } else if (isButtonClicked(e, refuseToBuild)) {
                    buttons.clear();
                    render(null, Steps.CHOOSE_COMMAND, field, "");
                    repaint();
                } else if (isButtonClicked(e, buyCompany)) {
                    currentPlayer.buyCompany();
                    dices.clear();
                    buttons.clear();
                    repaint();
                    if (dice[0] != dice[1]) {
                        canBuildCompany = true;
                        currentPlayerIndex++;
                    }
                    currentPlayer = players.get(currentPlayerIndex % players.size());
                    chooseCommand(currentPlayer);
                } else if (isButtonClicked(e, refuseToBuy)) {
                    buttons.clear();
                    dices.clear();
                    repaint();
                    if (dice[0] != dice[1]){
                        canBuildCompany = true;
                        currentPlayerIndex++;
                    }
                    currentPlayer = players.get(currentPlayerIndex % players.size());
                    chooseCommand(currentPlayer);
                } else if (isButtonClicked(e, okButton)) {
                    buttons.clear();
                    dices.clear();
                    currentString = "";
                    repaint();
                    if (dice[0] != dice[1]) {
                        canBuildCompany = true;
                        currentPlayerIndex++;
                    }
                    currentPlayer = players.get(currentPlayerIndex % players.size());
                    chooseCommand(currentPlayer);
                } else if (isButtonClicked(e, playInCasino)) {
                    buttons.clear();
                    dices.clear();
                    playInCasino();
                    repaint();
                } else if (isButtonClicked(e, refuseToPlay)) {
                    buttons.clear();
                    dices.clear();
                    repaint();
                    if (dice[0] != dice[1]) {
                        canBuildCompany = true;
                        currentPlayerIndex++;
                    }
                    currentPlayer = players.get(currentPlayerIndex % players.size());
                    chooseCommand(currentPlayer);
                } else if (isButtonClicked(e, firstCasinoChoice)) {
                    dices.clear();
                    buttons.clear();
                    if (field.getCells().get(currentPlayer.getCurrentPosition()) instanceof Casino) {
                        Casino casino = (Casino) field.getCells().get(currentPlayer.getCurrentPosition());
                        dices.add(new Dice(850, 620, 120, casino.play(1000, 2, currentPlayer, game)));
                    }
                    repaint();
                } else if (isButtonClicked(e, secondCasinoChoice)) {
                    dices.clear();
                    buttons.clear();
                    if (field.getCells().get(currentPlayer.getCurrentPosition()) instanceof Casino) {
                        Casino casino = (Casino) field.getCells().get(currentPlayer.getCurrentPosition());
                        dices.add(new Dice(850, 620, 120, casino.play(1000, 1, currentPlayer, game)));
                    }
                    repaint();
                } else if (isButtonClicked(e, payForExit)) {
                    dices.clear();
                    buttons.clear();
                    if (field.getCells().get(currentPlayer.getCurrentPosition()) instanceof Rialto) {
                        Rialto rialto = (Rialto) field.getCells().get(currentPlayer.getCurrentPosition());
                        rialto.payToExit(currentPlayer);
                        currentPlayer.setCountOfThrowsInPrison(0);
                    }
                    repaint();
                    chooseCommand(currentPlayer);
                } else if (isButtonClicked(e, waitForExit)) {
                    dices.clear();
                    buttons.clear();
                    if (field.getCells().get(currentPlayer.getCurrentPosition()) instanceof Rialto) {
                        Rialto rialto = (Rialto) field.getCells().get(currentPlayer.getCurrentPosition());
                        render(rialto.rollDice(currentPlayer, game), Steps.DRAW_DICE, field, null);
                    }
                    repaint();
                } else if (isButtonClicked(e, onlyPayForExit)) {
                    dices.clear();
                    buttons.clear();
                    if (field.getCells().get(currentPlayer.getCurrentPosition()) instanceof Rialto) {
                        Rialto rialto = (Rialto) field.getCells().get(currentPlayer.getCurrentPosition());
                        rialto.payToExit(currentPlayer);
                        currentPlayer.setCountOfThrowsInPrison(0);
                    }
                    repaint();
                    chooseCommand(currentPlayer);
                }
                revalidate();
                repaint();
            }
        });

        repaint();
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


    private boolean isButtonClicked(MouseEvent e, Button button) {
        if (buttons.contains(button) && e.getX() < button.getX() + button.getSizeX() / 2 && e.getX() > button.getX() - button.getSizeX() / 2 && e.getY() < button.getY() + button.getSizeY() / 2 && e.getY() > button.getY() - button.getSizeY() / 2) {
            return true;
        }
        return false;
    }

    private boolean isCompanyClicked(MouseEvent e){
        for(Cell cell : field.getCells()){
            if(cell instanceof Company && e.getX() < cell.getX() + cell.getSizeX() / 2 && e.getX() > cell.getX() - cell.getSizeX() / 2 && e.getY() < cell.getY() + cell.getSizeY() / 2 && e.getY() > cell.getY() - cell.getSizeY() / 2){
                return true;
            }
        }
        return false;
    }

    private Company whatCompanyClicked(MouseEvent e){
        for(Cell cell : field.getCells()){
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

        for (Button b : buttons) {
            b.draw(g2d);
        }

        for (int j = 0; j < players.size(); j++) {
            if (players.get(j) == currentPlayer) {
                players.get(j).drawActive(g2d, j + 1);
            } else {
                players.get(j).draw(g2d, j + 1);
            }
        }

        if (currentString != null && !currentString.equals("")) {
            DrawUtils.drawStr(g2d, currentString);
        }


        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if(player.getCash() < 0){
                for(Company company : player.getMyCompanies()){
                    company.setBought(false);
                    if(company.getCountOfBuildings() != 0) {
                        company.setSupplyPrice(company.getSupplyPrice() / (4 * company.getCountOfBuildings()));
                        company.setCountOfBuildings(0);
                    }
                    company.setColor(Color.WHITE);
                }
                players.remove(i);
            }
            if(players.size() == 1){
                g2d.setColor(new Color(51, 49, 49));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                players.get(0).setColor(new Color(189, 171, 61, 240));
                players.get(0).drawActive(g2d, 1);
                buttons.clear();
                g2d.setColor(Color.WHITE);
                DrawUtils.drawCenteredString(g, "Победа!", new Rectangle(0, 0, getWidth(), getHeight()), new Font("TimesRoman", Font.PLAIN, getHeight()/8));
            }
        }
    }
}
