package PiecesFactory;

public class Knight extends Piece{

    private PieceType pieceType = PieceType.KNIGHT;
    @Override
    public int verticalMovement() {
        return 0;
    }

    @Override
    public int horizontalMovement() {
        return 0;
    }
    @Override
    PieceType pieceType() {
        return pieceType;
    }
}