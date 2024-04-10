import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{

    Socket player1Socket;
    Socket player2Socket;
    PrintWriter outPlayer1;
    PrintWriter outPlayer2;
    BufferedReader inPlayer1;
    BufferedReader inPlayer2;

    int round = 0;

    boolean gameActive = false;

    public Server(Socket player1, Socket player2) throws IOException {
        player1Socket = player1;
        player2Socket = player2;
        try {
            outPlayer1 = new PrintWriter(player1Socket.getOutputStream(), true);
            outPlayer2 = new PrintWriter(player2Socket.getOutputStream(), true);
            inPlayer1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
            inPlayer2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        ArrayList<String> readyPlayerList = new ArrayList<>();
        outPlayer1.println("ready");
        outPlayer1.println("White Player");
        outPlayer2.println("ready");
        outPlayer2.println("Black Player");
        try {
            readyPlayerList.add(inPlayer1.readLine());
            readyPlayerList.add(inPlayer2.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (readyPlayerList.size() == 2) {
            gameActive = true;
        }

        while (gameActive) {
            if (round % 2 == 0){
                System.out.println("Round nr " + round + " white players turn");
                turn(outPlayer1,inPlayer1,outPlayer2,inPlayer2);
            }else{
                System.out.println("Round nr " + round+ " black players turn");
                turn(outPlayer2,inPlayer2,outPlayer1,inPlayer1);
            }
        }
    }
    
    public void turn(PrintWriter activePlayerWriter, BufferedReader activePlayerReader, PrintWriter waitingPlayerWriter, BufferedReader waitingPlayerReader){
        activePlayerWriter.println("active");
        waitingPlayerWriter.println("waiting");
        try {
            String playerMove = activePlayerReader.readLine();
            System.out.println(playerMove);
            waitingPlayerWriter.println("player move");
            waitingPlayerWriter.println(playerMove);
            round++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

