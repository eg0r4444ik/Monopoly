package ru.vsu.csf.monopoly.player;

import ru.vsu.csf.monopoly.PlayingField;
import ru.vsu.csf.monopoly.cells.Cell;
import ru.vsu.csf.monopoly.cells.Company;
import ru.vsu.csf.monopoly.cells.Type;

import java.util.List;
import java.util.Random;
public class Player {

    private Random rnd;
    private int currentPosition;
    private int cash;
    private PlayingField playingField;
    private List<Company> myCompanies;
    private int countOfDouble;



    public int[] rollDice(){
        rnd = new Random();
        int[] dice = new int[2];
        dice[0] = rnd.nextInt(5) + 1;
        dice[1] = rnd.nextInt(5) + 1;

        if(dice[0] == dice[1]){
            countOfDouble++;
        }

        return dice;
    }

    public void go(){
        if(playingField.getCells().get(currentPosition).getType() == Type.START){
            cash+=2000;
        }
        if(currentPosition < playingField.getCells().size() - 1){
            currentPosition++;
        }
        if(currentPosition == playingField.getCells().size()-1){
            currentPosition = 0;
        }
    }

    public void go(int[] dice){
        int sum = dice[0] + dice[1];
        while(sum != 0){
            go();
            sum--;
        }
    }

    public void buyCompany(){

        Cell currentCell = playingField.getCells().get(currentPosition);

        if (currentCell instanceof Company) {
            Company c = (Company) currentCell;
            myCompanies.add(c);
            cash -= c.getPurchasePrice();
        }

    }


}