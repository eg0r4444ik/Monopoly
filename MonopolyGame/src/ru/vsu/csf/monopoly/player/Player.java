package ru.vsu.csf.monopoly.player;

import ru.vsu.csf.monopoly.PlayingField;
import ru.vsu.csf.monopoly.cells.Cell;
import ru.vsu.csf.monopoly.cells.Company;
import ru.vsu.csf.monopoly.cells.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Player {

    private Random rnd;
    private boolean prisonForVisit = true;
    private int currentPosition;
    private int cash;
    private PlayingField playingField;
    private List<Company> myCompanies;
    private int countOfDouble, countOfThrowsInPrison = 0;

    public Player(int currentPosition, int cash, PlayingField playingField, List<Company> myCompanies, int countOfDouble) {
        this.currentPosition = currentPosition;
        this.cash = cash;
        this.playingField = playingField;
        this.myCompanies = myCompanies;
        this.countOfDouble = countOfDouble;
    }

    public int[] rollDice(){
        rnd = new Random();
        int[] dice = new int[2];
        dice[0] = rnd.nextInt(5) + 1;
        dice[1] = rnd.nextInt(5) + 1;

        if(dice[0] == dice[1]){
            countOfDouble++;
        } else{
            countOfDouble = 0;
        }

        return dice;
    }

    private void go(){
        if(currentPosition < playingField.getCells().size() - 1){
            currentPosition++;
        }
        if(currentPosition == playingField.getCells().size()-1){
            currentPosition = 0;
        }
    }

    public void go(int[] dice){
        if(countOfDouble == 3){
            currentPosition = 10;
            countOfDouble = 0;
        } else {
            int sum = dice[0] + dice[1];
            while (sum != 0) {
                go();
                sum--;
            }
        }
    }

    public void buyCompany(){
        Cell currentCell = playingField.getCells().get(currentPosition);

        if (currentCell instanceof Company) {
            Company c = (Company) currentCell;
            if(!c.isBought()){
                myCompanies.add(c);
                cash -= c.getPurchasePrice();
                c.setBought(true);
                if((c.getCompanyType() == Company.CompanyType.AUTO || c.getCompanyType() == Company.CompanyType.GAME) && countTypeCompany(c.getCompanyType()) > 1){
                    for (Company com: myCompanies) {
                        if(com.getCompanyType() == c.getCompanyType()){
                            com.setSupplyPrice(com.getSupplyPrice()*2);
                        }
                    }
                }
            }
        }
    }

    public void payForVisit(){
        Cell currentCell = playingField.getCells().get(currentPosition);
        if(currentCell instanceof Company){
            Company c = (Company) currentCell;
            cash -= c.getSupplyPrice();
        }
    }

    public int countTypeCompany(Company.CompanyType type){
        int count = 0;
        for(Company c : myCompanies){
            if(c.getCompanyType() == type){
                count++;
            }
        }
        return count;
    }

    public boolean haveAllCompanies(Company company){
        int count = 0;
        for (Company c: myCompanies) {
            if(c.getCompanyType() == company.getCompanyType()){
                count++;
            }
        }
        if(count == company.getCountToBuy() && company.getCountOfBuildings() < 5){
            return true;
        }else{
            return false;
        }
    }

    public List<Company> getCompaniesToBuild(){
        List<Company> comp = new ArrayList<>();
        for (Company c: myCompanies) {
            if(haveAllCompanies(c)){
                comp.add(c);
            }
        }
        return comp;
    }

    public boolean build(Company company){
        return company.buildCompany(this);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public List<Company> getMyCompanies() {
        return myCompanies;
    }

    public void setMyCompanies(List<Company> myCompanies) {
        this.myCompanies = myCompanies;
    }

    public int getCountOfDouble() {
        return countOfDouble;
    }

    public void setCountOfDouble(int countOfDouble) {
        this.countOfDouble = countOfDouble;
    }

    public PlayingField getPlayingField() {
        return playingField;
    }

    public void setPlayingField(PlayingField playingField) {
        this.playingField = playingField;
    }

    public boolean isPrisonForVisit() {
        return prisonForVisit;
    }

    public void setPrisonForVisit(boolean prisonForVisit) {
        this.prisonForVisit = prisonForVisit;
    }

    public int getCountOfThrowsInPrison() {
        return countOfThrowsInPrison;
    }

    public void setCountOfThrowsInPrison(int countOfThrowsInPrison) {
        this.countOfThrowsInPrison = countOfThrowsInPrison;
    }
}
