package PieceFactory;

import javax.swing.*;
import java.util.ArrayList;

public class Rook extends Piece{

    private PieceType pieceType = PieceType.ROOK;

    public Rook(String color) {
        super(color, "src/images/" + color + "_rook.png");
    }
    
    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public ArrayList<int[]> move(JButton[][] buttons, int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();
        
        for (int col = colPosition + 1; col < 8; col++) {
            Piece piece = (Piece) buttons[rowPosition][col].getClientProperty("piece");
            if (piece == null) {
                availableMoves.add(new int[]{rowPosition, col});
            } else if (isOpponentPiece(piece)) {
                availableMoves.add(new int[]{rowPosition, col});
                break;
            } else {
                break; 
            }
        }
        for (int col = colPosition - 1; col >= 0; col--) {
            Piece piece = (Piece) buttons[rowPosition][col].getClientProperty("piece");
            if (piece == null) {
                availableMoves.add(new int[]{rowPosition, col});
            } else if (isOpponentPiece(piece)) {
                availableMoves.add(new int[]{rowPosition, col});
                break;
            } else {
                break;
            }
        }

        for (int row = rowPosition - 1; row >= 0; row--) {
            Piece piece = (Piece) buttons[row][colPosition].getClientProperty("piece");
            if (piece == null) {
                availableMoves.add(new int[]{row, colPosition});
            } else if (isOpponentPiece(piece)) {
                availableMoves.add(new int[]{row, colPosition});
                break;
            } else {
                break; 
            }
        }

        for (int row = rowPosition + 1; row < 8; row++) {
            Piece piece = (Piece) buttons[row][colPosition].getClientProperty("piece");
            if (piece == null) {
                availableMoves.add(new int[]{row, colPosition});
            } else if (isOpponentPiece(piece)) {
                availableMoves.add(new int[]{row, colPosition});
                break;
            } else {
                break; 
            }
        }

        return availableMoves;
    }


    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private boolean isOpponentPiece(Piece piece) {
        String pieceColor = piece.getColor();
        return !pieceColor.equalsIgnoreCase(getColor());
    }
}
