package PieceFactory;

public enum PieceType {
    KING("King"),
    QUEEN("Queen"),
    ROOK("Rook"),
    BISHOP("Bishop"),
    KNIGHT("Knight"),
    PAWN("Pawn");
    
    public final String pieceType;
    
    PieceType(String pieceType){
        this.pieceType = pieceType;
    }
}
