package ru.vsu.csf.monopoly.server;

import ru.vsu.csf.monopoly.clients.CurrentGameState;
import ru.vsu.csf.monopoly.game.Game;
import ru.vsu.csf.monopoly.game.GraphicGame;
import ru.vsu.csf.monopoly.game.Parser;
import ru.vsu.csf.monopoly.game.Steps;
import ru.vsu.csf.monopoly.graphics.MainWindow;
import ru.vsu.csf.monopoly.objects.cells.Company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {

        try(ServerSocket server = new ServerSocket(9999);
            Socket client1 = server.accept();
            Socket client2 = server.accept();
            BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(client1.getOutputStream()));
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
            BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(client2.getOutputStream()));
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(client2.getInputStream()))
        ){

            System.out.println("Server started!");
            System.out.println();

            Game game = new Game(writer1, writer2);
            CurrentGameState curr = new CurrentGameState(1, 15000, 15000,
                    game.getCells(), new int[]{0,0}, new int[]{0,0}, "Начало игры", Steps.CHOOSE_COMMAND);
            writer1.write(curr.toString());
            writer1.newLine();
            writer1.flush();

            while(true){
                String str;
                if(game.getCurrentPlayerIndex() == 1) {
                    str = reader1.readLine();
                    System.out.print("Player 1: ");
                }else {
                    str = reader2.readLine();
                    System.out.print("Player 2: ");
                }
                System.out.println(str);
                Parser.defineGameStep(str, game);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
