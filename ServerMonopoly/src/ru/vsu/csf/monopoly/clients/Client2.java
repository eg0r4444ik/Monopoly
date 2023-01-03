package ru.vsu.csf.monopoly.clients;

import ru.vsu.csf.monopoly.game.GraphicGame;
import ru.vsu.csf.monopoly.game.Parser;
import ru.vsu.csf.monopoly.graphics.MainWindow;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2{

    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 9999);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            MainWindow mw = new MainWindow(writer);
            mw.setVisible(true);
            GraphicGame game = mw.getDp();

            while(true) {
                String str = reader.readLine();
                System.out.println(str);
                CurrentGameState curr = Parser.parseCurrState(str);
                game.render(curr.getDices(), curr.getStep(),curr.getString(),curr.getPlayersPos(),curr.getCells(),curr.getCurrentPlayerIndex(),
                        curr.getPlayer1Cash(),curr.getPlayer2Cash());

            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
