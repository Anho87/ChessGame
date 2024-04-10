import PieceFactory.Piece;
import PieceFactory.PieceFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BoardPanel {
    JPanel bp;
    int rowLength = 8;
    int columLength = 8;
    JButton[][] buttons;

    Color[][] originalButtonColors = new Color[8][8];
    Color lightBrown = new Color(102, 51, 0);
    Color darkBrown = new Color(153, 102, 0);

    private static final int DESIRED_WIDTH = 50;
    private static final int DESIRED_HEIGHT = 50;

    PieceFactory pieceFactory = new PieceFactory();
    ArrayList<Piece> whitePieces;
    ArrayList<Piece> blackPieces;

    public void createButtons() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton tempButton = new JButton();
                if ((row + col) % 2 == 0) {
                    tempButton.setBackground(darkBrown);
                    originalButtonColors[row][col] = darkBrown;
                } else {
                    tempButton.setBackground(lightBrown);
                    originalButtonColors[row][col] = lightBrown;
                }
                buttons[row][col] = tempButton;
            }
        }
    }

    public void resetButtonColors() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                buttons[row][col].setBackground(originalButtonColors[row][col]);
                buttons[row][col].setBorder(null);
            }
        }
    }


    public void addButtonsToPanel() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                JButton button = buttons[row][col];
                button.setPreferredSize(new Dimension(80, 80));
                bp.add(button);
            }
        }
    }

    BoardPanel(JPanel boardJPanel, JButton[][] btn) {
        bp = boardJPanel;
        buttons = btn;
        whitePieces = pieceFactory.createArrayListOfPieces("white");
        blackPieces = pieceFactory.createArrayListOfPieces("black");
        bp.setLayout(new GridLayout(rowLength, columLength));
        createButtons();
        addButtonsToPanel();
        setStartingPositions();
    }


    public void movePiece(String movePieces) {
        String[] parts = movePieces.split(" ");
        if (parts.length >= 2) {
            String targetPart = parts[0];
            String currentPart = parts[1];

            String[] targetCoords = targetPart.split(",");
            String[] currentCoords = currentPart.split(",");

            if (targetCoords.length == 2 && currentCoords.length == 2) {
                int targetRow = Integer.parseInt(targetCoords[0]);
                int targetCol = Integer.parseInt(targetCoords[1]);
                int currentRow = Integer.parseInt(currentCoords[0]);
                int currentCol = Integer.parseInt(currentCoords[1]);

                Piece sourceObject = (Piece) buttons[currentRow][currentCol].getClientProperty("piece");
                Object pieceTypeColorProperty = buttons[currentRow][currentCol].getClientProperty("pieceType");
                Object colorClientProperty = buttons[currentRow][currentCol].getClientProperty("color");

                Icon icon = buttons[currentRow][currentCol].getIcon();
                buttons[targetRow][targetCol].setIcon(icon);
                buttons[currentRow][currentCol].setIcon(null);

                buttons[targetRow][targetCol].putClientProperty("color", colorClientProperty);
                buttons[currentRow][currentCol].putClientProperty("color", null);

                buttons[targetRow][targetCol].putClientProperty("pieceType", pieceTypeColorProperty);
                buttons[currentRow][currentCol].putClientProperty("pieceType", null);

                buttons[targetRow][targetCol].putClientProperty("piece", sourceObject);
                buttons[currentRow][currentCol].putClientProperty("piece", null);
                
            }
        }
    }
    

    public void setStartingPositions() {
        try {
            for (int i = 0; i < 8; i++) {
                buttons[0][i].putClientProperty("piece",whitePieces.get(i));
                Piece storedWhitePiece =  (Piece) buttons[0][i].getClientProperty("piece");
                ImageIcon whiteIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(storedWhitePiece.getimgPath()))));
                buttons[0][i].setIcon(whiteIcon);
                buttons[0][i].putClientProperty("color", storedWhitePiece.getColor());
                buttons[0][i].putClientProperty("pieceType", storedWhitePiece.getPieceType().pieceType);

                buttons[7][i].putClientProperty("piece",blackPieces.get(i));
                Piece storedBlackPiece = (Piece) buttons[7][i].getClientProperty("piece");
                ImageIcon blackIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(storedBlackPiece.getimgPath()))));
                buttons[7][i].setIcon(blackIcon);
                buttons[7][i].putClientProperty("color", storedBlackPiece.getColor());
                buttons[7][i].putClientProperty("pieceType", storedBlackPiece.getPieceType().pieceType);
                
//                ImageIcon whiteIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(whitePieces.get(i).getimgPath()))));
//                ImageIcon blackIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(blackPieces.get(i).getimgPath()))));
//
//                buttons[0][i].setIcon(whiteIcon);
//                buttons[0][i].putClientProperty("color", whitePieces.get(i).getColor());
//                buttons[0][i].putClientProperty("pieceType", whitePieces.get(i).getPieceType().pieceType);
//
//                buttons[7][i].setIcon(blackIcon);
//                buttons[7][i].putClientProperty("color", blackPieces.get(i).getColor());
//                buttons[7][i].putClientProperty("pieceType", blackPieces.get(i).getPieceType().pieceType);
            }

            for (int i = 0; i < 8; i++) {
                buttons[1][i].putClientProperty("piece",whitePieces.get(8));
                Piece storedWhitePiece =  (Piece) buttons[1][i].getClientProperty("piece");
                ImageIcon whiteIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(storedWhitePiece.getimgPath()))));
                buttons[1][i].setIcon(whiteIcon);
                buttons[1][i].putClientProperty("color", storedWhitePiece.getColor());
                buttons[1][i].putClientProperty("pieceType", storedWhitePiece.getPieceType().pieceType);


                buttons[6][i].putClientProperty("piece",blackPieces.get(8));
                Piece storedBlackPiece = (Piece) buttons[6][i].getClientProperty("piece");
                ImageIcon blackIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(storedBlackPiece.getimgPath()))));
                buttons[6][i].setIcon(blackIcon);
                buttons[6][i].putClientProperty("color", storedBlackPiece.getColor());
                buttons[6][i].putClientProperty("pieceType", storedBlackPiece.getPieceType().pieceType);
                
                
//                ImageIcon whitePawnIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(whitePieces.get(8).getimgPath()))));
//                ImageIcon blackPawnIcon = scaleImageIcon(new ImageIcon(ImageIO.read(new File(blackPieces.get(8).getimgPath()))));
//
//                buttons[1][i].setIcon(whitePawnIcon);
//                buttons[1][i].putClientProperty("color", whitePieces.get(8).getColor());
//                buttons[1][i].putClientProperty("pieceType", whitePieces.get(8).getPieceType().pieceType);
//
//                buttons[6][i].setIcon(blackPawnIcon);
//                buttons[6][i].putClientProperty("color", blackPieces.get(8).getColor());
//                buttons[6][i].putClientProperty("pieceType", blackPieces.get(8).getPieceType().pieceType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageIcon scaleImageIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(DESIRED_WIDTH, DESIRED_HEIGHT, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}

