package tetris.game;

import tetris.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tetromino {


    public enum Tetrominos{
        L_PIECE(1),
        J_PIECE(2),
        T_PIECE(3),
        I_PIECE(4),
        O_PIECE(5),
        Z_PIECE(6),
        S_PIECE(7);

        public final int id;

        Tetrominos(int i) {
            this.id = i;
        }
    }
    public static final int SQUARE_SIDE = 20;
    public Tetrominos thisPiece;
    public Color color;
    public Tetromino(int id) {
        thisPiece = Tetrominos.values()[id];
        color = this.getColor();
    }
    private Color getColor() {
        switch(thisPiece.id) {
            case 1:
                return Color.ORANGE;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.PINK;
            case 5:
                return Color.YELLOW;
            case 6:
                return Color.RED;
            case 7:
                return Color.GREEN;
            default:
                return Color.black;
        }
    }
}
