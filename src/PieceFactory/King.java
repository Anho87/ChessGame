package PieceFactory;

import javax.swing.*;
import java.util.ArrayList;

public class King extends Piece{
    
    private final PieceType pieceType = PieceType.KING;

    public King(String color) {
        super(color, "src/images/" + color + "_king.png");
    }
    
    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public ArrayList<int[]> move(JButton[][] buttons, int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();

        int[][] kingMoves = {
                {1, 0}, {1, 1}, {0, 1}, {-1, 1},
                {-1, 0}, {-1, -1}, {0, -1}, {1, -1}
        };
        
        for (int[] move : kingMoves) {
            int newRow = rowPosition + move[0];
            int newCol = colPosition + move[1];
            if (isValidPosition(newRow, newCol)) {
                Piece piece = (Piece) buttons[newRow][newCol].getClientProperty("piece");
                if (piece == null || isOpponentPiece(piece)) {
                    availableMoves.add(new int[]{newRow, newCol});
                }
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
