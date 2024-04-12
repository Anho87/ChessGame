package PieceFactory;

import javax.swing.*;
import java.util.ArrayList;

public class Knight extends Piece {

    private PieceType pieceType = PieceType.KNIGHT;

    public Knight(String color) {
        super(color, "src/images/" + color + "_knight.png");
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public ArrayList<int[]> move(JButton[][] buttons, int rowPosition, int colPosition) {
        int[][] knightMoves = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        ArrayList<int[]> availableMoves = new ArrayList<>();

        for (int[] move : knightMoves) {
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
