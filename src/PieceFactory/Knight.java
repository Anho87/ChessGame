package PieceFactory;

import java.util.ArrayList;

public class Knight extends Piece{

    private PieceType pieceType = PieceType.KNIGHT;

    public Knight(String color) {
        super(color, "src/images/" + color + "_knight.png");
    }
   
    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public ArrayList<int[]> move(int rowPosition, int colPosition) {
        int[][] knightMoves = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        ArrayList<int[]> availableMoves = new ArrayList<>();

        for (int[] move : knightMoves) {
            int newRow = rowPosition + move[0];
            int newCol = colPosition + move[1];
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                availableMoves.add(new int[]{newRow, newCol});
            }
        }

        return availableMoves;
    }
}
