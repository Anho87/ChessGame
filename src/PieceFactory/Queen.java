package PieceFactory;

public class Queen extends Piece{

    private PieceType pieceType = PieceType.QUEEN;
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
