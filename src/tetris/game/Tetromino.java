package tetris.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tetromino {
    public static final int SQUARE_SIDE = 20;
    public int x;
    public int y;
    public Color color;
    public Tetromino(int x) {
        this.x = x;

    }
    public Image drawImage() {
        BufferedImage image = new BufferedImage(SQUARE_SIDE,SQUARE_SIDE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.fillRect(x, y,SQUARE_SIDE,SQUARE_SIDE);
        return image;
    }
    private Color pickColor() {

    }
}
