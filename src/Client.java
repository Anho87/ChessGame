import PieceFactory.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class Client extends JFrame implements ActionListener {
    //    JPanel northPanel = new JPanel();
    JPanel boardJPanel = new JPanel();
    //    JButton newGameButton = new JButton("New Game");
//    JButton exitButton = new JButton("Exit");
    JButton[][] buttons = new JButton[8][8];
    private static JButton sourceButton = null;
    BoardPanel bp;
    String playerColor;
    String colorOfPlayerPieces;
    String ip = "127.0.0.1";
    int inPort = 22222;

    Color lightBlue = new Color(4, 190, 246);
    Color green = new Color(4, 246, 28);
    Socket sock = new Socket(ip, inPort);
    PrintWriter outToServer = new PrintWriter(sock.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

    public Client() throws IOException {
        setupGameBoard();
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                JButton button = buttons[row][col];
                button.setEnabled(false);
                button.setDisabledIcon(button.getIcon());
            }
        }
    }

    public void game() throws IOException {
        String fromServer;
        while (true) {
            fromServer = in.readLine();
            if (fromServer.equalsIgnoreCase("waiting")) {
                System.out.println("waiting");
                for (int row = 0; row < buttons.length; row++) {
                    for (int col = 0; col < buttons[row].length; col++) {
                        JButton button = buttons[row][col];
                        button.setEnabled(false);
                        button.setDisabledIcon(button.getIcon());
                    }
                }
            } else if (fromServer.equalsIgnoreCase("active")) {
                System.out.println("active");
                for (int row = 0; row < buttons.length; row++) {
                    for (int col = 0; col < buttons[row].length; col++) {
                        JButton button = buttons[row][col];
                        button.setEnabled(true);
                    }
                }
            } else if (fromServer.equalsIgnoreCase("ready")) {
                System.out.println("player ready");
                playerColor = in.readLine();
                setTitle(playerColor);
                colorOfPlayerPieces = playerColor.equalsIgnoreCase("White Player") ? "white" : "black";
                outToServer.println("Player ready");
            } else if (fromServer.equalsIgnoreCase("player move")) {
                System.out.println("player move");
                bp.movePiece(in.readLine());
            } else if (fromServer.equalsIgnoreCase("kingDead?")) {
                boolean exists = checkIfKingIsDead();
                if (exists){
                    System.out.println("alive");
                    outToServer.println("alive");
                }else{
                    System.out.println("dead");
                    outToServer.println("dead");
                }
            }
        }
    }
    

    public void setupGameBoard() {
        setLayout(new BorderLayout());
//        northPanel.add(newGameButton);
//        northPanel.add(exitButton);

        bp = new BoardPanel(boardJPanel, buttons);

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                JButton button = buttons[row][col];
                button.addActionListener(this);
            }
        }

        add(boardJPanel);
//        exitButton.addActionListener(e -> System.exit(0));
//        newGameButton.addActionListener(e -> outToServer.println("NewGame"));
//        add(northPanel, BorderLayout.NORTH);
        setTitle("Chess");
        setLocationRelativeTo(null);
        pack();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int clickedRow = -1;
        int clickedCol = -1;
        int sourceRow = -1;
        int sourceCol = -1;
        if (sourceButton == null) {
            if (clickedButton.getIcon() != null) {
                sourceButton = clickedButton;
                String buttonColorProperty = (String) sourceButton.getClientProperty("color");
                System.out.println("color property: " + buttonColorProperty);
                String buttonTypeProperty = (String) sourceButton.getClientProperty("pieceType");
                System.out.println("pieceType property: " + buttonTypeProperty);
                for (int row = 0; row < buttons.length; row++) {
                    for (int col = 0; col < buttons[row].length; col++) {
                        if (buttons[row][col] == sourceButton) {
                            clickedRow = row;
                            clickedCol = col;
                        }
                    }
                }
                if (checkPieceAndPlayerColor()) {
                    checkPossiblePlay(clickedRow, clickedCol);
                } else {
                    sourceButton = null;
                }
            }
        } else {
            for (int row = 0; row < buttons.length; row++) {
                for (int col = 0; col < buttons[row].length; col++) {
                    if (buttons[row][col] == clickedButton) {
                        clickedRow = row;
                        clickedCol = col;
                    } else if (buttons[row][col] == sourceButton) {
                        sourceRow = row;
                        sourceCol = col;
                    }
                }
            }
            if (checkPieceAndPlayerColor()) {
                if (clickedButton == sourceButton) {
                    bp.resetButtonColors();
                } else {
                    Color checkBackgroundColor = clickedButton.getBackground();
                    if (checkBackgroundColor.equals(lightBlue)) {
                        makeMove(clickedRow, clickedCol, sourceRow, sourceCol);
                        bp.resetButtonColors();
                    } else {
                        bp.resetButtonColors();
                    }
                }
            }
            sourceButton = null;
        }
    }

    private boolean checkIfKingIsDead() {
        boolean kingExists = false;
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                Piece tempPiece = (Piece) buttons[row][col].getClientProperty("piece");
                if (tempPiece != null && tempPiece.getPieceType().pieceType.equalsIgnoreCase("king") && tempPiece.getColor().equalsIgnoreCase(colorOfPlayerPieces)) {
                    kingExists = true;
                    break; // Found the king, no need to continue searching
                }
            }
            if (kingExists) {
                break; // Found the king, no need to continue searching
            }
        }
        return kingExists;
    }


    public void checkPossiblePlay(int rowPosition, int colPosition) {
        Piece sourceObject = (Piece) sourceButton.getClientProperty("piece");
        sourceButton.setBorder(new LineBorder(green, 3));
        if (sourceObject != null) {
            ArrayList<int[]> availableMoves = sourceObject.move(buttons,rowPosition, colPosition);
            paintButtonBackgroundWithAvailableMoves(availableMoves);
        }
    }

    private void paintButtonBackgroundWithAvailableMoves(ArrayList<int[]> availableMoves) {
        for (int[] move : availableMoves) {
            int row = move[0];
            int col = move[1];
            buttons[row][col].setBackground(lightBlue);
        }
    }

    public void makeMove(int clickedRow, int clickedCol, int sourceRow, int sourceCol) {
        System.out.println(clickedRow + "," + clickedCol + " " + sourceRow + "," + sourceCol);
        bp.movePiece(clickedRow + "," + clickedCol + " " + sourceRow + "," + sourceCol);
        outToServer.println(clickedRow + "," + clickedCol + " " + sourceRow + "," + sourceCol);
    }

    public boolean checkPieceAndPlayerColor() {
        String buttonColorProperty = (String) sourceButton.getClientProperty("color");
        if (buttonColorProperty == null) {
            return false;
        }
        return buttonColorProperty.contains(colorOfPlayerPieces);
    }


    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.game();
    }

}

