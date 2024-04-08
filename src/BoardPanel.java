import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
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

    private static final int DESIRED_WIDTH = 50;
    private static final int DESIRED_HEIGHT = 50;
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
            int targetLocation = Integer.parseInt(firstPart);
            int currentLocation = Integer.parseInt(secondPart);
            
            Icon icon = buttonList.get(currentLocation).getIcon();
            buttonList.get(targetLocation).setIcon(icon);
            buttonList.get(currentLocation).setIcon(null);
//            buttonList.get(targetLocation).setIcon(sourceIcon);
//            buttonList.get(currentLocation).setIcon(null);
//            buttonList.get(targetLocation).setText(buttonList.get(currentLocation).getText());
//            buttonList.get(currentLocation).setText("");
        } else if (movePieces.equalsIgnoreCase("NewGame")) {
            setStartingPositions();
        }
    }
    public void setStartingPositions() {
        String[] blackPieces = {"src/images/b_rook.png", "src/images/b_knight.png", "src/images/b_bishop.png", "src/images/b_king.png", "src/images/b_queen.png", "src/images/b_bishop.png", "src/images/b_knight.png", "src/images/b_rook.png"};
        String[] whitePieces = {"src/images/w_rook.png", "src/images/w_knight.png", "src/images/w_bishop.png", "src/images/w_king.png", "src/images/w_queen.png", "src/images/w_bishop.png", "src/images/w_knight.png", "src/images/w_rook.png"};
        String[] pawns = {"src/images/b_pawn.png", "src/images/w_pawn.png"};

        try {
            for (int i = 0; i < 8; i++) {
                ImageIcon blackIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(blackPieces[i]))));
                ImageIcon whiteIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(whitePieces[i]))));
                buttonList.get(i).setIcon(whiteIcon);
                buttonList.get(i + 56).setIcon(blackIcon);
            }

            for (int i = 8; i < 16; i++) {
                ImageIcon blackPawnIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(pawns[0]))));
                ImageIcon whitePawnIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(pawns[1]))));
                buttonList.get(i).setIcon(whitePawnIcon);
                buttonList.get(i + 40).setIcon(blackPawnIcon);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private ImageIcon scaleImageIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(DESIRED_WIDTH, DESIRED_HEIGHT, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}

