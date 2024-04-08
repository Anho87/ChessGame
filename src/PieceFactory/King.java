package PieceFactory;

public class King extends Piece{
    
    private PieceType pieceType = PieceType.KING;
    
    
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
