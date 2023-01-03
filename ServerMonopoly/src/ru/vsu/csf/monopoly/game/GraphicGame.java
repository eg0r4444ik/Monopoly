package ru.vsu.csf.monopoly.game;

import ru.vsu.csf.monopoly.graphics.DrawUtils;
import ru.vsu.csf.monopoly.graphics.GraphicCell;
import ru.vsu.csf.monopoly.graphics.GraphicPlayer;
import ru.vsu.csf.monopoly.graphics.GraphicPlayingField;
import ru.vsu.csf.monopoly.objects.Dice;
import ru.vsu.csf.monopoly.objects.Player;
import ru.vsu.csf.monopoly.objects.cells.Casino;
import ru.vsu.csf.monopoly.objects.cells.Cell;
import ru.vsu.csf.monopoly.objects.cells.Company;
import ru.vsu.csf.monopoly.objects.cells.Rialto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphicGame extends JPanel implements Runnable {

    private List<JButton> buttons;
    private List<Dice> dices;
    private int[] dice;
    private int currentPlayerIndex = 1;
    private GraphicPlayingField field;
    Steps step;
    private Random random = new Random();
    private final JButton rollDice, buildCompany, refuseToBuild, buyCompany, refuseToBuy, okButton, playInCasino, refuseToPlay;
    private final JButton firstCasinoChoice, secondCasinoChoice, payForExit, waitForExit, onlyPayForExit;
    private String currentString;
    private BufferedWriter writer;
    private GraphicPlayer player1, player2, currentPlayer;

    @Override
    public void render(int[] dices, Steps step, String str, int[] playersPos, List<Integer> cells,
                       int currentPlayerIndex, int player1Cash, int player2Cash) {
        if(str != null) {
            this.currentString = str;
        }
        if (dices != null && dices[0] != 0 && dices[1] != 0) {
            this.dice = dices;
        }
        if(playersPos != null && cells != null){
            player1.setCurrentPosition(playersPos[0]);
            player2.setCurrentPosition(playersPos[1]);
            List<GraphicCell> cell = field.getCells();
            for(GraphicCell c : cell){
                c.emptyCell();
            }

            GraphicCell cell1 = cell.get(playersPos[0]);
            GraphicCell cell2 = cell.get(playersPos[1]);
            cell1.addPlayer(player1);
            cell2.addPlayer(player2);
            cell.set(playersPos[0], cell1);
            cell.set(playersPos[1], cell2);

            for(int i = 0; i < cells.size(); i++){
                if(cells.get(i) == 1){
                    List<Company> companies = player1.getMyCompanies();
                    if(cell.get(i) instanceof Company) {
                        Company company = (Company) cell.get(i);
                        companies.add(company);
                        player1.setMyCompanies(companies);
                        company.setBought(true);
                        company.setColor(player1.getColor());
                    }
                }
                if(cells.get(i) == 2){
                    List<Company> companies = player2.getMyCompanies();
                    if(cell.get(i) instanceof Company) {
                        Company company = (Company) cell.get(i);
                        companies.add(company);
                        player2.setMyCompanies(companies);
                        company.setBought(true);
                        company.setColor(player2.getColor());
                    }
                }
            }

            field.setCells(cell);
        }
        this.currentPlayerIndex = currentPlayerIndex;
        player1.setCash(player1Cash);
        player2.setCash(player2Cash);
        currentPlayer = currentPlayerIndex == 1 ? player1 : player2;
        this.step = step;
        defineStep();
        repaint();
    }

    public GraphicGame(BufferedWriter writer) throws IOException {
        this.writer = writer;
        field = new GraphicPlayingField();
        field.generateField();
        player1 = new GraphicPlayer(0, 0, 200, Color.RED, 0, 15000, graphicFieldToField(field), new ArrayList<>(), 0);
        player2 = new GraphicPlayer(0, 200, 200, Color.BLUE, 0, 15000, graphicFieldToField(field), new ArrayList<>(), 0);
        field.getCells().get(0).addPlayer(player1);
        field.getCells().get(0).addPlayer(player2);
        currentPlayer = player1;

        currentString = "";
        dice = new int[2];

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


        rollDice.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.clear();
                try {
                    writer.write("make move");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                repaint();
            }
        });

        buildCompany.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    writer.write("build company");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                buttons.clear();
                render(new int[]{0,0}, Steps.CHOOSE_COMMAND, "", new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                        getThisCells(), currentPlayerIndex, player1.getCash(), player2.getCash());
                repaint();
            }
        });
        refuseToBuild.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                buttons.clear();
                render(new int[]{0,0}, Steps.CHOOSE_COMMAND, "", new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                        getThisCells(), currentPlayerIndex, player1.getCash(), player2.getCash());
                repaint();
            }
        });
        buyCompany.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    writer.write("buy company");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                dices.clear();
                buttons.clear();
                repaint();
                try {
                    writer.write("next");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        refuseToBuy.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                buttons.clear();
                dices.clear();
                repaint();
                try {
                    writer.write("next");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
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
                try {
                    writer.write("next");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        firstCasinoChoice.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                try {
                    writer.write("play in casino 1");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                repaint();
            }
        });
        secondCasinoChoice.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                try {
                    writer.write("play in casino 2");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                repaint();
            }
        });
        payForExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                try {
                    writer.write("pay for exit");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                repaint();
                chooseCommand();
            }
        });
        waitForExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                try {
                    writer.write("wait for exit");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                repaint();
            }
        });
        onlyPayForExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dices.clear();
                buttons.clear();
                try {
                    writer.write("pay for exit");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                repaint();
                chooseCommand();
            }
        });

        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.clear();
                dices.clear();
                currentString = "";
                repaint();
                try {
                    writer.write("next");
                    writer.newLine();
                    writer.flush();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isCompanyClicked(e)){
                    Company company = whatCompanyClicked(e);
                    if(currentPlayer.getMyCompanies().contains(company) && company.getCountOfBuildings() <= 2){
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
            chooseCommand();
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

    private void chooseCommand() {
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

    private boolean isCompanyClicked(MouseEvent e){
        for(GraphicCell cell : field.getCells()){
            if(cell instanceof Company && e.getX() < cell.getX() + cell.getSizeX() / 2 && e.getX() > cell.getX() - cell.getSizeX() / 2 && e.getY() < cell.getY() + cell.getSizeY() / 2 && e.getY() > cell.getY() - cell.getSizeY() / 2){
                return true;
            }
        }
        return false;
    }

    private Company whatCompanyClicked(MouseEvent e){
        for(GraphicCell cell : field.getCells()){
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

        for (GraphicCell c : field.getCells()) {
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

        if(currentPlayerIndex == 1){
            player1.drawActive(g2d, 1);
            player2.draw(g2d, 2);
        }else if(currentPlayerIndex == 2){
            player1.draw(g2d, 1);
            player2.drawActive(g2d, 2);
        }

        if (currentString != null && !currentString.equals("")) {
            DrawUtils.drawStr(g2d, currentString);
        }


//        for (int i = 0; i < game.getPlayers().size(); i++) {
//            Player player = game.getPlayers().get(i);
//            if(player.getCash() < 0){
//                for(Company company : player.getMyCompanies()){
//                    company.setBought(false);
//                    if(company.getCountOfBuildings() != 0) {
//                        company.setSupplyPrice(company.getSupplyPrice() / (4 * company.getCountOfBuildings()));
//                        company.setCountOfBuildings(0);
//                    }
//                    company.setColor(Color.WHITE);
//                }
//                game.getPlayers().remove(i);
//            }
//            if(game.getPlayers().size() == 1){
//                g2d.setColor(new Color(51, 49, 49));
//                g2d.fillRect(0, 0, getWidth(), getHeight());
//                game.getPlayers().get(0).setColor(new Color(189, 171, 61, 240));
//                game.getPlayers().get(0).drawActive(g2d, 1);
//                buttons.clear();
//                g2d.setColor(Color.WHITE);
//                DrawUtils.drawCenteredString(g, "Победа!", new Rectangle(0, 0, getWidth(), getHeight()), new Font("TimesRoman", Font.PLAIN, getHeight()/8));
//            }
//        }
    }

    public List<Integer> getThisCells(){
        List<Integer> cells = new ArrayList<>();
        for(Cell cell : field.getCells()){
            if(cell instanceof Company && ((Company) cell).isBought()){
                if(player1.getMyCompanies().contains(cell)){
                    cells.add(1);
                }else{
                    cells.add(2);
                }
            }else{
                cells.add(0);
            }
        }
        return cells;
    }

    private PlayingField graphicFieldToField(GraphicPlayingField field){
        PlayingField playingField = new PlayingField();
        List<Cell> cells = new ArrayList<>();
        for(GraphicCell c : field.getCells()){
            cells.add(c);
        }
        playingField.setCells(cells);
        return playingField;
    }
}
