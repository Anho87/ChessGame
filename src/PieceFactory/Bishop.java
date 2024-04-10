package PieceFactory;

import java.util.ArrayList;

public class Bishop extends Piece{

    private PieceType pieceType = PieceType.BISHOP;

    public Bishop(String color) {
        super(color, "src/images/" + color + "_bishop.png");
    }
    
    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public ArrayList<int[]> move(int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();

// Check for moves along the main diagonal (from top-left to bottom-right)
        for (int i = 1; i < 8; i++) {
            int newRow = rowPosition + i;
            int newCol = colPosition + i;
            if (newRow < 8 && newCol < 8) {
                availableMoves.add(new int[]{newRow, newCol});
            } else {
                break; // Stop if out of bounds
            }
        }

// Check for moves along the anti-diagonal (from top-right to bottom-left)
        for (int i = 1; i < 8; i++) {
            int newRow = rowPosition + i;
            int newCol = colPosition - i;
            if (newRow < 8 && newCol >= 0) {
                availableMoves.add(new int[]{newRow, newCol});
            } else {
                break; // Stop if out of bounds
            }
        }

// Check for moves along the anti-diagonal (from bottom-left to top-right)
        for (int i = 1; i < 8; i++) {
            int newRow = rowPosition - i;
            int newCol = colPosition + i;
            if (newRow >= 0 && newCol < 8) {
                availableMoves.add(new int[]{newRow, newCol});
            } else {
                break; // Stop if out of bounds
            }
        }

// Check for moves along the main diagonal (from bottom-right to top-left)
        for (int i = 1; i < 8; i++) {
            int newRow = rowPosition - i;
            int newCol = colPosition - i;
            if (newRow >= 0 && newCol >= 0) {
                availableMoves.add(new int[]{newRow, newCol});
            } else {
                break; // Stop if out of bounds
            }
        }

        return availableMoves;

    }
}
