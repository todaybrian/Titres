package tetris.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tetris extends Rectangle {

    //Game width and height of the gameboard only
    private static int GAME_WIDTH = 100;
    private static int GAME_HEIGHT = 100;

    public Tetris(){

    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        return image;
    }
}
