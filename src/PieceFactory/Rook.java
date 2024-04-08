package PieceFactory;

public class Rook extends Piece{

    private PieceType pieceType = PieceType.ROOK;
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
