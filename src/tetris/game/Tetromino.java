package tetris.game;

import tetris.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tetromino {


    protected enum Tetrominos{
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
    public double x;
    public double y;
    public Tetrominos thisPiece;
    public Color color;
    public Tetromino(int id) {
        x = 1.5 * Tetris.GAME_WIDTH;
        y = Tetris.GAME_HEIGHT;
        thisPiece = Tetrominos.values()[id];
    }
    public void draw(Graphics g) {
        g.setColor(pickColor());
        g.fillRect((int)x,(int)y,SQUARE_SIDE,SQUARE_SIDE);
    }
    private Color pickColor() {
        switch(thisPiece.id) {
            case 1:
                return Color.ORANGE;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.decode("Light Blue");
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
