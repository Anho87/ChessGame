package PieceFactory;

import java.util.ArrayList;

public class Queen extends Piece{

    private PieceType pieceType = PieceType.QUEEN;

    public Queen(String color) {
        super(color, "src/images/" + color + "_queen.png");
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public ArrayList<int[]> move(int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();

// Define the possible directions for a queen's move
        int[][] queenMoves = {
                // Horizontal and vertical moves (rook-like movements)
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                // Diagonal moves (bishop-like movements)
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

// Iterate over each possible move direction
        for (int[] move : queenMoves) {
            int newRow = rowPosition + move[0];
            int newCol = colPosition + move[1];

            // Continue in the same direction until reaching the board's edge
            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                // Add the move to the availableMoves list
                availableMoves.add(new int[]{newRow, newCol});
                newRow += move[0];
                newCol += move[1];
            }
        }


        return availableMoves;

    }
}
