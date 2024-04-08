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
        outPlayer2.println("ready");
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
                System.out.println("Round nr " + round + " player 1's turn");
                outPlayer1.println("active");
                outPlayer2.println("waiting");
                try {
                    String playerMove = inPlayer1.readLine();
                    System.out.println(playerMove);
                    outPlayer2.println("player move");
                    outPlayer2.println(playerMove);
                    round++;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                System.out.println("Round nr " + round+ " player 2's turn");
                outPlayer2.println("active");
                outPlayer1.println("waiting");
                try {
                    String playerMove = inPlayer2.readLine();
                    System.out.println(playerMove);
                    outPlayer1.println("player move");
                    outPlayer1.println(playerMove);
                    round++;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

