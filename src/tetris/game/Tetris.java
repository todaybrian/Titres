package tetris.game;

import tetris.util.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Tetris extends Rectangle {

    //Game width and height of the gameboard only
    public static int GAME_WIDTH = 732;
    public static int GAME_HEIGHT = 1080;

    private final ImageIcon TETRIS_GRID;

    public PieceType[][] grid;
    public PieceType hold;


    public Tetris(){
        TETRIS_GRID =  new ImageIcon(Assets.Game.TETRIS_GRID);

        grid = new PieceType[31][11];
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], PieceType.NULL);
        }
    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        g.drawImage(TETRIS_GRID.getImage(), 0, 1080/2 - TETRIS_GRID.getIconHeight()/2, TETRIS_GRID.getIconWidth(), TETRIS_GRID.getIconHeight(), null);

        g.setColor(Color.BLUE);
//        g.fillRect(319, 505, 34, 34);
//        g.fillRect(179, 190, 34, 34);

        grid[10][1] = PieceType.I;

        drawGrid(g);
        return image;
    }

    public void update(){

    }

    public void drawGrid(Graphics2D g){
        for (int row = 1; row < grid.length; row++) {
            for (int column = 1; column <= 10; column++) {
                drawSquare(g, row, column);
            }
        }
    }

    public void moveRight(){

    }

    public void moveLeft(){

    }

    public boolean dropPiece(){

        return true;
    }

    public void setPiece(){

    }

    public void hardDrop(){

    }

    private void drawSquare(Graphics2D g, int row, int column){
        switch(grid[row][column]){
            case I:
                g.setColor(new Color(48, 213,  200));
                break;
            case J:
                g.setColor(Color.BLUE);
                break;
            case Z:
                g.setColor(Color.RED);
                break;
            case S:
                g.setColor(Color.GREEN);
                break;
            case L:
                g.setColor(Color.ORANGE);
                break;
            case T:
                g.setColor(Color.PINK);
                break;
            case O:
                g.setColor(Color.YELLOW);
                break;
            default:
                return;
        }
        g.fillRect(179 + 35*(column-1), -160 + 35*row, 34, 34);
    }
}
