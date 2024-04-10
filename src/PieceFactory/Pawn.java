package PieceFactory;

import java.util.ArrayList;

public class Pawn extends Piece{

    private PieceType pieceType = PieceType.PAWN;

    public Pawn(String color) {
        super(color, "src/images/" + color + "_pawn.png");
    }
   
    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public ArrayList<int[]> move(int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();

// Define the possible directions for a pawn's move
        int[][] pawnMoves;
        String color = getColor();
        if (color.equalsIgnoreCase("white")) {
            // For white pawn, it moves forward by one square
            pawnMoves = new int[][]{{1, 0}};
        } else {
            // For black pawn, it moves forward by one square
            pawnMoves = new int[][]{{-1, 0}};
        }

// Iterate over each possible move direction
        for (int[] move : pawnMoves) {
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
