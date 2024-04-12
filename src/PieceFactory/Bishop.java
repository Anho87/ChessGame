package PieceFactory;

import javax.swing.*;
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
    public ArrayList<int[]> move(JButton[][] buttons, int rowPosition, int colPosition) {
        ArrayList<int[]> availableMoves = new ArrayList<>();
        int[][] directions = { {1, 1}, {1, -1}, {-1, 1}, {-1, -1} };

        for (int[] direction : directions) {
            int rowDir = direction[0];
            int colDir = direction[1];

            for (int i = 1; i < 8; i++) {
                int newRow = rowPosition + (i * rowDir);
                int newCol = colPosition + (i * colDir);
                if (isValidPosition(newRow, newCol)) {
                    Piece piece = (Piece) buttons[newRow][newCol].getClientProperty("piece");
                    if (piece == null) {
                        availableMoves.add(new int[]{newRow, newCol});
                    } else {
                        if (isOpponentPiece(piece)) {
                            availableMoves.add(new int[]{newRow, newCol});
                        }
                        break;
                    }
                } else {
                    break;
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
