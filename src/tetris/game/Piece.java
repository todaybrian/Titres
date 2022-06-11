package tetris.game;

//Moving piece
public class Piece {
    int rotationIndex;
    int centerX, centerY;
    PieceType type;
    PieceType[][] currentPieceGrid;

    public Piece(PieceType type){
        this.type = type;
        this.rotationIndex = 0;
        this.centerX = 5;
        this.centerY = 8;

        this.currentPieceGrid = PieceType.getPieceGrid(type);
    }

    public Piece clone(){
        Piece clone = new Piece(this.type);
        clone.rotationIndex = this.rotationIndex;
        clone.centerX = this.centerX;
        clone.centerY = this.centerY;
        clone.currentPieceGrid = PieceType.getPieceGrid(this.type);
        return clone;
    }
}
