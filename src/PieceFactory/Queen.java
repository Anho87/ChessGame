package PieceFactory;

import javax.swing.*;
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
    public ArrayList<int[]> move(JButton[][] buttons, int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();
        int[][] queenMoves = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };


        for (int[] move : queenMoves) {
            int newRow = rowPosition + move[0];
            int newCol = colPosition + move[1];

            while (isValidPosition(newRow, newCol)) {
                Piece piece = (Piece) buttons[newRow][newCol].getClientProperty("piece");
                if (piece == null) {
                    availableMoves.add(new int[]{newRow, newCol});
                } else {
                    if (isOpponentPiece(piece)) {
                        availableMoves.add(new int[]{newRow, newCol});
                    }
                    break;
                }
                newRow += move[0];
                newCol += move[1];
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
