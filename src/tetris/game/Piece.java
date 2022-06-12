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
        this.centerX = 4;
        this.centerY = 8;

        this.currentPieceGrid = PieceType.getPieceGrid(type);
    }

    public Piece clone(){
        Piece clone = new Piece(this.type);
        clone.rotationIndex = this.rotationIndex;
        clone.centerX = this.centerX;
        clone.centerY = this.centerY;
        clone.currentPieceGrid = this.currentPieceGrid;
        return clone;
    }

    public void rotateCW(){
        this.rotationIndex = (this.rotationIndex+1)%4;
        this.currentPieceGrid = PieceType.getPieceGridFromRot(this.type, this.rotationIndex);
    }

    public void rotateCCW(){
        this.rotationIndex = (this.rotationIndex+3)%4;
        this.currentPieceGrid = PieceType.getPieceGridFromRot(this.type, this.rotationIndex);
    }
}
