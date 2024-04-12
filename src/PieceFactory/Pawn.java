package PieceFactory;

import javax.swing.*;
import java.util.ArrayList;

public class Pawn extends Piece {

    private final PieceType pieceType = PieceType.PAWN;

    public Pawn(String color) {
        super(color, "src/images/" + color + "_pawn.png");
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public ArrayList<int[]> move(JButton[][] buttons, int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();

        int[][] pawnMoves;
        if (getColor().equalsIgnoreCase("white")) {
            pawnMoves = new int[][]{{1, 0}};
        } else {
            pawnMoves = new int[][]{{-1, 0}};
        }

        for (int[] move : pawnMoves) {
            int newRow = rowPosition + move[0];
            int newCol = colPosition + move[1];

            if (isValidPosition(newRow, newCol)) {
                Piece oneTileAheadPiece = (Piece) buttons[newRow][colPosition].getClientProperty("piece");
                Piece twoTilesAheadPiece = (Piece) buttons[newRow][colPosition].getClientProperty("piece");
                Piece oneTileAheadAndOneToTheLeftPiece = (colPosition > 0) ? (Piece) buttons[newRow][colPosition - 1].getClientProperty("piece") : null;
                Piece oneTileAheadAndOneToTheRightPiece = (colPosition < 7) ? (Piece) buttons[newRow][colPosition + 1].getClientProperty("piece") : null;

                if (oneTileAheadPiece == null) {
                    availableMoves.add(new int[]{newRow, colPosition});
                    if ((getColor().equalsIgnoreCase("white") && rowPosition == 1) || (getColor().equalsIgnoreCase("black") && rowPosition == 6)) {
                        if (twoTilesAheadPiece == null && isValidPosition(newRow + move[0], colPosition)) {
                            availableMoves.add(new int[]{newRow + move[0], colPosition});
                        }
                    }
                }
                if (newCol > 0 && oneTileAheadAndOneToTheLeftPiece != null && isOpponentPiece(oneTileAheadAndOneToTheLeftPiece)) {
                    availableMoves.add(new int[]{newRow, colPosition - 1});
                }
                if (newCol < 7 && oneTileAheadAndOneToTheRightPiece != null && isOpponentPiece(oneTileAheadAndOneToTheRightPiece)) {
                    availableMoves.add(new int[]{newRow, colPosition + 1});
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
