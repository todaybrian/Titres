package tetris.game;

import tetris.game.randomizer.Randomizer;
import tetris.game.randomizer.RandomizerSevenBag;
import tetris.util.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tetris extends Rectangle {

    //Game width and height of the gameboard only
    public static int GAME_WIDTH = 732;
    public static int GAME_HEIGHT = 1080;

    private final ImageIcon TETRIS_GRID;

    public Tetris(){
        TETRIS_GRID =  new ImageIcon(Assets.Game.TETRIS_GRID);


    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        g.drawImage(TETRIS_GRID.getImage(), 0, 1080/2 - TETRIS_GRID.getIconHeight()/2, TETRIS_GRID.getIconWidth(), TETRIS_GRID.getIconHeight(), null);

        return image;
    }



}
