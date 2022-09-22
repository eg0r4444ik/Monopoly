package ru.vsu.csf.monopoly.cells;

import ru.vsu.csf.monopoly.cells.util.Coord;

import java.util.ArrayList;
import java.util.Random;

public class Casino extends Cell{

    private Random rnd = new Random();

    public Casino(){
        super(new ArrayList<>(), new Coord(0,0), Type.START, 0);
    }

    public int play(int betSum, int[] bet){
        int number = rnd.nextInt(5)+1;
        for(int i : bet){
            if(i == number){
                return betSum*2;
            }
        }
        return 0;
    }

}
