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
    JPanel northPanel = new JPanel();
    JPanel boardJPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    JButton exitButton = new JButton("Exit");
    JButton[][] buttons = new JButton[8][8];
    private static JButton sourceButton = null;
    BoardPanel bp;
    String playerColor;
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
                outToServer.println("Player ready");
            } else if (fromServer.equalsIgnoreCase("player move")) {
                System.out.println("player move");
                bp.movePiece(in.readLine());
            }
        }
    }

    public void setupGameBoard() {
        setLayout(new BorderLayout());
        northPanel.add(newGameButton);
        northPanel.add(exitButton);

        bp = new BoardPanel(boardJPanel, buttons);

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                JButton button = buttons[row][col];
                button.addActionListener(this);
            }
        }

        add(boardJPanel);
        exitButton.addActionListener(e -> System.exit(0));
        newGameButton.addActionListener(e -> outToServer.println("NewGame"));
        add(northPanel, BorderLayout.NORTH);
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
                    if (checkBackgroundColor.equals(lightBlue))
                    makeMove(clickedRow, clickedCol, sourceRow, sourceCol);
                    bp.resetButtonColors(); 
                }
            }
            sourceButton = null;
        }
    }

    public void checkPossiblePlay(int rowPosition, int colPosition) {
        if (rowPosition >= 0 && rowPosition < buttons.length) { 
//            String buttonTypeProperty = (String) sourceButton.getClientProperty("pieceType");
            Piece sourceObject = (Piece) sourceButton.getClientProperty("piece");
            sourceButton.setBorder(new LineBorder(green, 3));
            ArrayList<int[]> availableMoves;
            if (sourceObject != null) {
                availableMoves = sourceObject.move(rowPosition,colPosition);
                paintButtonBackgroundWithAvailableMoves(availableMoves);
//                switch (buttonTypeProperty) {
//                    case "Rook": {
//                        availableMoves = sourceObject.move(rowPosition,colPosition);
//                        paintButtonBackgroundWithAvailableMoves(availableMoves);
////                        highlightRookMoves(rowPosition, colPosition);
//                        break;
//                    }
//                    case "Knight": {
//                        availableMoves = sourceObject.move(rowPosition,colPosition);
//                        paintButtonBackgroundWithAvailableMoves(availableMoves);
////                        highlightKnightMoves(rowPosition, colPosition);
//                        break;
//                    }
//                    case "Bishop": {
//                        highlightBishopMoves(rowPosition, colPosition);
//                        break;
//                    }
//                    case "Queen":{
//                        highlightQueenMoves(rowPosition,colPosition);
//                        break;
//                    }
//                    case "King":{
//                        highlightKingMoves(rowPosition, colPosition);
//                        break;
//                    }
//                    case "Pawn":{
//                        highlightPawnMoves(rowPosition, colPosition);
//                        break;
//                    }
//                }
            }
        } else {
            System.out.println("Invalid rowPosition: " + rowPosition);
        }
    }
    
    private void paintButtonBackgroundWithAvailableMoves(ArrayList<int[]> availableMoves){
        for (int[] move : availableMoves) {
            int row = move[0];
            int col = move[1];

            if (row >= 0 && row < buttons.length && col >= 0 && col < buttons[0].length) {
                buttons[row][col].setBackground(lightBlue); 
            }
        }
    }

//    private void highlightBishopMoves(int rowPosition, int colPosition) {
//        int[][] bishopDirections = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
//
//        for (int[] direction : bishopDirections) {
//            int newRow = rowPosition + direction[0];
//            int newCol = colPosition + direction[1];
//
//            while (newRow >= 0 && newRow < buttons.length && newCol >= 0 && newCol < buttons[0].length) {
//                buttons[newRow][newCol].setBackground(lightBlue);
//                newRow += direction[0];
//                newCol += direction[1];
//            }
//        }
//    }
//    private void highlightQueenMoves(int rowPosition, int colPosition) {
//        highlightRookMoves(rowPosition, colPosition);
//        highlightBishopMoves(rowPosition, colPosition);
//    }
//    private void highlightKingMoves(int rowPosition, int colPosition) {
//        int[][] kingMoves = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
//
//        for (int[] move : kingMoves) {
//            int newRow = rowPosition + move[0];
//            int newCol = colPosition + move[1];
//
//            if (newRow >= 0 && newRow < buttons.length && newCol >= 0 && newCol < buttons[0].length) {
//                buttons[newRow][newCol].setBackground(lightBlue);
//            }
//        }
//    }
//    private void highlightPawnMoves(int rowPosition, int colPosition) {
//        int direction = (playerColor.equalsIgnoreCase("White Player")) ? 1 : -1;
//
//        int newRow = rowPosition + direction;
//        int newCol = colPosition;
//        if (newRow >= 0 && newRow < buttons.length && newCol >= 0 && newCol < buttons[0].length) {
//            buttons[newRow][newCol].setBackground(lightBlue);
//        }
//
//        int[][] pawnDiagonalMoves = {{direction, -1}, {direction, 1}};
//        for (int[] move : pawnDiagonalMoves) {
//            newRow = rowPosition + move[0];
//            newCol = colPosition + move[1];
//            if (newRow >= 0 && newRow < buttons.length && newCol >= 0 && newCol < buttons[0].length) {
//                buttons[newRow][newCol].setBackground(lightBlue);
//            }
//        }
//    }

//    private void highlightKnightMoves(int rowPosition, int colPosition) {
//        int[][] knightMoves = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
//
//        for (int[] move : knightMoves) {
//            int newRow = rowPosition + move[0];
//            int newCol = colPosition + move[1];
//
//            if (newRow >= 0 && newRow < buttons.length && newCol >= 0 && newCol < buttons[0].length) {
//                buttons[newRow][newCol].setBackground(lightBlue);
//            }
//        }
//    }

//    private void highlightRookMoves(int rowPosition, int colPosition) {
//        for (int col = 0; col < buttons[rowPosition].length; col++) {
//            if (col != colPosition) {
//                buttons[rowPosition][col].setBackground(lightBlue);
//            }
//        }
//        for (int row = 0; row < buttons.length; row++) {
//            if (row != rowPosition) {
//                buttons[row][colPosition].setBackground(lightBlue);
//            }
//        }
//    }
    
    public void makeMove(int clickedRow, int clickedCol, int sourceRow, int sourceCol){
        System.out.println(clickedRow + "," + clickedCol + " " + sourceRow + "," + sourceCol);
        bp.movePiece(clickedRow + "," + clickedCol + " " + sourceRow + "," + sourceCol);
        outToServer.println(clickedRow + "," + clickedCol + " " + sourceRow + "," + sourceCol);
    }

    public boolean checkPieceAndPlayerColor() {
        String buttonColorProperty = (String) sourceButton.getClientProperty("color");
        if (buttonColorProperty == null) {
            return false;
        }
        String colorOfPlayerPieces = playerColor.equalsIgnoreCase("White Player") ? "white" : "black";
        return buttonColorProperty.contains(colorOfPlayerPieces);
    }


    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.game();
    }

}

