package ru.vsu.csf.monopoly.game;

import ru.vsu.csf.monopoly.clients.CurrentGameState;
import ru.vsu.csf.monopoly.graphics.GraphicPlayer;
import ru.vsu.csf.monopoly.objects.Player;
import ru.vsu.csf.monopoly.objects.cells.*;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;import ru.vsu.csf.monopoly.game.Runnable;

public class Game {

    private Player player1, player2;
    private PlayingField field;
    private BufferedWriter activeWriter, passWriter, writer1, writer2;
    private int currentPlayerIndex = 1;
    private int[] dice = new int[2];


    public Game(BufferedWriter writer1, BufferedWriter writer2) {
        this.writer1 = writer1;
        this.writer2 = writer2;
        activeWriter = writer1;
        passWriter = writer2;
        this.field = new PlayingField();
        field.generateField();
        player1 = new Player( Color.RED, 0, 15000, field, new ArrayList<>(), 0);
        player2 = new Player( Color.BLUE, 0, 15000, field, new ArrayList<>(), 0);

        Cell start = field.getCells().get(0);
        ArrayList<Player> p = new ArrayList<>();
        p.add(player1);
        p.add(player2);
        start.setPlayers(p);
    }


    public void makeMove() throws IOException {
        Player player;
        if(currentPlayerIndex == 1){
            player = player1;
        }else{
            player = player2;
        }
        if(!player.isPrisonForVisit()){
            Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            if(currentCell instanceof Rialto){
                Rialto rialto = (Rialto) currentCell;
                rialto.makeMove(player, this);
            } else{
                player.setPrisonForVisit(true);
            }
        } else {
            dice = player.rollDice();
            CurrentGameState curr = new CurrentGameState(currentPlayerIndex, player1.getCash(), player2.getCash(), getCells()
                    , dice, new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                    "", Steps.DRAW_DICE);
            activeWriter.write(curr.toString());
            activeWriter.newLine();
            activeWriter.flush();
            CurrentGameState state = new CurrentGameState(currentPlayerIndex, player1.getCash(), player2.getCash(), getCells()
                    , dice, new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                    "", Steps.NOTHING);
            passWriter.write(state.toString());
            passWriter.newLine();
            passWriter.flush();

            Cell currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            List<Player> p = currentCell.getPlayers();
            p.remove(player);
            currentCell.setPlayers(p);
            player.go(dice);

            currentCell = player.getPlayingField().getCells().get(player.getCurrentPosition());
            p = currentCell.getPlayers();
            p.add(player);
            currentCell.setPlayers(p);
            currentCell.makeMove(player, this);
        }
    }

    public void buyCompany(){
        Player player;
        if(currentPlayerIndex == 1){
            player = player1;
        }else{
            player = player2;
        }
        player.buyCompany();
    }

    public void playInCasino(int choice) throws IOException {
        Player player;
        if(currentPlayerIndex == 1){
            player = player1;
        }else{
            player = player2;
        }
        if (field.getCells().get(player.getCurrentPosition()) instanceof Casino) {
            Casino casino = (Casino) field.getCells().get(player.getCurrentPosition());
            casino.play(1000, choice, player, this);
        }
    }

    public void payForExit(){
        Player player;
        if(currentPlayerIndex == 1){
            player = player1;
        }else{
            player = player2;
        }
        if (field.getCells().get(player.getCurrentPosition()) instanceof Rialto) {
            Rialto rialto = (Rialto) field.getCells().get(player.getCurrentPosition());
            rialto.payToExit(player);
            player.setCountOfThrowsInPrison(0);
        }
    }

    public void waitForExit() throws IOException {
        Player player;
        if(currentPlayerIndex == 1){
            player = player1;
        }else{
            player = player2;
        }
        if (field.getCells().get(player.getCurrentPosition()) instanceof Rialto) {
            Rialto rialto = (Rialto) field.getCells().get(player.getCurrentPosition());
            rialto.rollDice(player, this);
        }
    }

    public void next() throws IOException {
        if(dice[0] != dice[1]){
            currentPlayerIndex++;
            if(currentPlayerIndex > 2) {
                currentPlayerIndex = 1;
            }
            if(currentPlayerIndex == 1){
                activeWriter = writer1;
                passWriter = writer2;
            }
            if(currentPlayerIndex == 2){
                activeWriter = writer2;
                passWriter = writer1;
            }
            CurrentGameState curr = new CurrentGameState(currentPlayerIndex, player1.getCash(), player2.getCash(), getCells()
                    , new int[]{0,0}, new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                    "", Steps.CHOOSE_COMMAND);
            activeWriter.write(curr.toString());
            activeWriter.newLine();
            activeWriter.flush();
            CurrentGameState state = new CurrentGameState(currentPlayerIndex, player1.getCash(), player2.getCash(), getCells()
                    , new int[]{0,0}, new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                    "", Steps.NOTHING);
            passWriter.write(state.toString());
            passWriter.newLine();
            passWriter.flush();
        }else{
            makeMove();
        }
    }

    public void buildCompany(Company company) throws IOException {
        Player player;
        if(currentPlayerIndex == 1){
            player = player1;
        }else{
            player = player2;
        }
        if(player.getCompaniesToBuild().size() == 0){
            CurrentGameState curr = new CurrentGameState(currentPlayerIndex, player1.getCash(), player2.getCash(), getCells()
                    , new int[]{0,0}, new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                    "У вас недостаточно компаний, чтобы построить филиал", Steps.DRAW_STRING);
            activeWriter.write(curr.toString());
            activeWriter.newLine();
            activeWriter.flush();
            CurrentGameState state = new CurrentGameState(currentPlayerIndex, player1.getCash(), player2.getCash(), getCells()
                    , new int[]{0,0}, new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                    "", Steps.NOTHING);
            passWriter.write(state.toString());
            passWriter.newLine();
            passWriter.flush();
        } else {
            if(player.getCash() >= 1500) {
                player.build(company);
            } else{
                CurrentGameState curr = new CurrentGameState(currentPlayerIndex, player1.getCash(), player2.getCash(), getCells()
                        , new int[]{0,0}, new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                        "У вас недостаточно средств для постройки", Steps.DRAW_STRING);
                activeWriter.write(curr.toString());
                activeWriter.newLine();
                activeWriter.flush();
                CurrentGameState state = new CurrentGameState(currentPlayerIndex, player1.getCash(), player2.getCash(), getCells()
                        , new int[]{0,0}, new int[]{player1.getCurrentPosition(), player2.getCurrentPosition()},
                        "", Steps.NOTHING);
                passWriter.write(state.toString());
                passWriter.newLine();
                passWriter.flush();
            }
        }
    }

    public PlayingField getField() {
        return field;
    }

    public void setField(PlayingField field) {
        this.field = field;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public List<Integer> getCells(){
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

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public BufferedWriter getActiveWriter() {
        return activeWriter;
    }

    public BufferedWriter getPassWriter() {
        return passWriter;
    }
}
