package PieceFactory;

import java.util.ArrayList;

public class PieceFactory {

    public ArrayList<Piece> createArrayListOfPieces(String color){
        ArrayList<Piece> pieceArrayList = new ArrayList<>();
        pieceArrayList.add(createPiece(PieceType.ROOK,color));
        pieceArrayList.add(createPiece(PieceType.KNIGHT,color));
        pieceArrayList.add(createPiece(PieceType.BISHOP,color));
        pieceArrayList.add(createPiece(PieceType.KING,color));
        pieceArrayList.add(createPiece(PieceType.QUEEN,color));
        pieceArrayList.add(createPiece(PieceType.BISHOP,color));
        pieceArrayList.add(createPiece(PieceType.KNIGHT,color));
        pieceArrayList.add(createPiece(PieceType.ROOK,color));
        for (int i = 0; i < 8; i++) {
            pieceArrayList.add(createPiece(PieceType.PAWN,color));
        }
        return pieceArrayList;
    }

    private Piece createPiece(PieceType pieceType, String pieceColor){
        if (pieceType == null ||pieceColor == null ) {
            return null;
        }
        switch (pieceType){
            case KING -> {
                return new King(pieceColor);
            }
            case BISHOP -> {
                return new Bishop(pieceColor);
            }
            case KNIGHT -> {
                return new Knight(pieceColor);
            }
            case PAWN -> {
                return new Pawn(pieceColor);
            }
            case QUEEN -> {
                return new Queen(pieceColor);
            }
            case ROOK -> {
                return new Rook(pieceColor);
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
