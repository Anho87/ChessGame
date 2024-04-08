package PiecesFactory;

public class Bishop extends Piece{

    private PieceType pieceType = PieceType.BISHOP;
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
