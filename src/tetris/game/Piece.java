/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Handles the internal logic of individual tetrominoes
 */

package tetris.game;

//Moving piece
public class Piece {
    // Current rotation state (0 = upright, 1 = 90 degrees, 2 = upside down, 3 = 270 degrees)
    int rotationIndex;
    int centerX, centerY; // Center of the pieceGrid relative to the game board

    // Piece type means its shape and by extension its color
    PieceType type;

    // Stores actual position of the piece (i.e. which squares are colored) in an array
    PieceType[][] currentPieceGrid;

    public Piece(PieceType type){
        this.type = type;
        this.rotationIndex = 0;
        this.centerX = 4;
        this.centerY = 8;

        // Each piece type has a default piece grid in its unrotated state, to be used when it spawns
        this.currentPieceGrid = PieceType.getPieceGrid(type);
    }

    // Creates a copy of a piece, transferring all parameters
    public Piece clone(){
        Piece clone = new Piece(this.type);
        clone.rotationIndex = this.rotationIndex;
        clone.centerX = this.centerX;
        clone.centerY = this.centerY;
        clone.currentPieceGrid = this.currentPieceGrid;
        return clone;
    }

    // Rotates clockwise
    public void rotateCW(){
        this.rotationIndex = (this.rotationIndex+1)%4; // Rotating a piece with rotation index 3 clockwise means making it upright again (set rotIndex to 0)
        this.currentPieceGrid = PieceType.getPieceGridFromRot(this.type, this.rotationIndex);
    }

    // Rotates counterclockwise
    public void rotateCCW(){
        this.rotationIndex = (this.rotationIndex+3)%4; // rotating CCW is just rotating CW 3 times
        this.currentPieceGrid = PieceType.getPieceGridFromRot(this.type, this.rotationIndex);
    }
}
