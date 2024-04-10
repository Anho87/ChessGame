package PieceFactory;

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
    public ArrayList<int[]> move(int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();

// Define the possible directions for a king's move
        int[][] kingMoves = {
                {1, 0}, {1, 1}, {0, 1}, {-1, 1},
                {-1, 0}, {-1, -1}, {0, -1}, {1, -1}
        };

// Iterate over each possible move direction
        for (int[] move : kingMoves) {
            int newRow = rowPosition + move[0];
            int newCol = colPosition + move[1];

            // Check if the new position is within the bounds of the board
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                // Add the move to the availableMoves list
                availableMoves.add(new int[]{newRow, newCol});
            }
        }


        return availableMoves;

    }
}
