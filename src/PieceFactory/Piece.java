package PieceFactory;

public abstract class Piece implements Movement{
    
    final private String color;
    
    final private String imgPath;

    public Piece(String color, String imgPath) {
        this.color = color;
        this.imgPath = imgPath;
    }

    public String getColor() {
        return color;
    }

    public String getimgPath() {
        return imgPath;
    }

    public abstract PieceType getPieceType();
}
