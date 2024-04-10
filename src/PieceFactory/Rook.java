package PieceFactory;

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
    public ArrayList<int[]> move(int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();
        for (int col = 0; col < 8; col++) {
            if (col != colPosition) { // Exclude the current column
                availableMoves.add(new int[]{rowPosition, col}); // Add horizontal move
            }
        }
        for (int row = 0; row < 8; row++) {
            if (row != rowPosition) { // Exclude the current row
                availableMoves.add(new int[]{row, colPosition}); // Add vertical move
            }
        }
        return availableMoves;
    }
}
