package PieceFactory;

public class Pawn extends Piece{

    private PieceType pieceType = PieceType.PAWN;
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
