import ru.vsu.csf.monopoly.PlayingField;
import ru.vsu.csf.monopoly.cells.Company;
import ru.vsu.csf.monopoly.player.Player;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        PlayingField field1 = new PlayingField(new ArrayList<>());
        field1.generateField();
        Player player = new Player(0, 15000, field1, new ArrayList<>(), 0);
        int[] dice = player.rollDice();
        System.out.println(dice[0] + " " + dice[1]);
        player.go(dice);
        if(field1.getCells().get(player.getCurrentPosition()) instanceof Company){
            Company c = (Company) field1.getCells().get(player.getCurrentPosition());
            System.out.println(c.getName());
        }
    }
}
