import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class BoardPanel {
    JPanel bp;
    int rowLength = 8;
    int columLength = 8;
    ArrayList<JButton> buttonList;
    Color lightBrown = new Color(102, 51, 0);
    Color darkBrown = new Color(153, 102, 0);
    MulticastSocket so;

    String whiteKing = "<html><font size='10'>\u2654</font></html>";
    String whiteQueen = "<html><font size='10'>\u2655</font></html>";
    String whiteRook = "<html><font size='10'>\u2656</font></html>";
    String whiteBishop = "<html><font size='10'>\u2657</font></html>";
    String whiteKnight = "<html><font size='10'>\u2658</font></html>";
    String whitePawn = "<html><font size='10'>\u2659</font></html>";

    String blackKing = "<html><font size='10'>\u265a</font></html>";
    String blackQueen = "<html><font size='10'>\u265b</font></html>";
    String blackRook = "<html><font size='10'>\u265c</font></html>";
    String blackBishop = "<html><font size='10'>\u265d</font></html>";
    String blackKnight = "<html><font size='10'>\u265e</font></html>";
    String blackPawn = "<html><font size='10'>\u265f</font></html>";


    public void createButtonList() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    JButton tempButton = new JButton();
                    tempButton.setBackground(darkBrown);
                    buttonList.add(tempButton);
                } else {
                    JButton tempButton = new JButton();
                    tempButton.setBackground(lightBrown);
                    buttonList.add(tempButton);
                }
            }
        }
    }


    public void addButtonsToPanel() {
        for (JButton b : buttonList) {
            b.setPreferredSize(new Dimension(80, 80));
            bp.add(b);
        }
    }

    BoardPanel(JPanel boardJPanel, ArrayList<JButton> buttList) {
        bp = boardJPanel;
        buttonList = buttList;
        bp.setLayout(new GridLayout(rowLength, columLength));
        createButtonList();
        addButtonsToPanel();
        setStartingPositions();
    }


    public void movePiece(String movePieces) {
        String[] parts = movePieces.split(" ");
        if (parts.length >= 2) {
            String firstPart = parts[0];
            String secondPart = parts[1];
            int firstButtonIndex = Integer.parseInt(firstPart);
            int secondButtonIndex = Integer.parseInt(secondPart);
            buttonList.get(firstButtonIndex).setText(buttonList.get(secondButtonIndex).getText());
            buttonList.get(secondButtonIndex).setText("");
        } else if (movePieces.equalsIgnoreCase("NewGame")) {
            setStartingPositions();
        }
    }
    public void setStartingPositions() {
        String[] blackPieces = {blackRook, blackKnight, blackBishop, blackKing, blackQueen, blackBishop, blackKnight, blackRook};
        String[] whitePieces = {whiteRook, whiteKnight, whiteBishop, whiteKing, whiteQueen, whiteBishop, whiteKnight, whiteRook};
        String[] pawns = {blackPawn, whitePawn};

        for (int i = 0; i < 8; i++) {
            buttonList.get(i).setText(blackPieces[i]);
            buttonList.get(i + 56).setText(whitePieces[i]);
        }

        for (int i = 8; i < 16; i++) {
            buttonList.get(i).setText(pawns[0]);
            buttonList.get(i + 40).setText(pawns[1]);
        }
    }
}

