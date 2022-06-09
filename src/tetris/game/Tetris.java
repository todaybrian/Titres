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
    private Color[][] occupiedGrid = new Color[10][22];
    Randomizer randomizer = new RandomizerSevenBag();

    private final ImageIcon TETRIS_GRID;

    public Tetris(){
        TETRIS_GRID =  new ImageIcon(Assets.Game.TETRIS_GRID);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 22; j++) {
                occupiedGrid[i][j] = Color.BLACK;
            }
        }
        putTheNextTetrominoOnScreen();
    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
//        drawGrid(g);
        g.drawImage(TETRIS_GRID.getImage(), 0, 1080/2 - TETRIS_GRID.getIconHeight()/2, TETRIS_GRID.getIconWidth(), TETRIS_GRID.getIconHeight(), null);

        return image;
    }

    public void drawGrid(Graphics2D g) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 22; j++) {
                g.setColor(occupiedGrid[i][j]);
                g.fillRect(Tetromino.SQUARE_SIDE*i, Tetromino.SQUARE_SIDE*j, Tetromino.SQUARE_SIDE, Tetromino.SQUARE_SIDE);
            }
        }
    }

    public void putTheNextTetrominoOnScreen() {
        Tetromino newRender = new Tetromino(randomizer.getNext());
        switch(newRender.thisPiece.id) {
            case 1:
                occupiedGrid[6][0] = newRender.color;
                occupiedGrid[4][1] = newRender.color;
                occupiedGrid[5][1] = newRender.color;
                occupiedGrid[6][1] = newRender.color;
            case 2:
                occupiedGrid[4][0] = newRender.color;
                occupiedGrid[4][1] = newRender.color;
                occupiedGrid[5][1] = newRender.color;
                occupiedGrid[6][1] = newRender.color;
            case 3:
                occupiedGrid[5][0] = newRender.color;
                occupiedGrid[4][1] = newRender.color;
                occupiedGrid[5][1] = newRender.color;
                occupiedGrid[6][1] = newRender.color;
            case 4:
                occupiedGrid[4][1] = newRender.color;
                occupiedGrid[5][1] = newRender.color;
                occupiedGrid[6][1] = newRender.color;
                occupiedGrid[7][1] = newRender.color;
            case 5:
                occupiedGrid[5][0] = newRender.color;
                occupiedGrid[6][0] = newRender.color;
                occupiedGrid[5][1] = newRender.color;
                occupiedGrid[6][1] = newRender.color;
            case 6:
                occupiedGrid[4][0] = newRender.color;
                occupiedGrid[5][0] = newRender.color;
                occupiedGrid[5][1] = newRender.color;
                occupiedGrid[6][1] = newRender.color;
            case 7:
                occupiedGrid[5][0] = newRender.color;
                occupiedGrid[6][1] = newRender.color;
                occupiedGrid[4][1] = newRender.color;
                occupiedGrid[5][1] = newRender.color;
        }
    }
}
