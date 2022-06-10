package tetris.game;

import tetris.game.randomizer.Randomizer;
import tetris.game.randomizer.RandomizerSevenBag;
import tetris.util.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class Tetris extends Rectangle {

    //Game width and height of the gameboard only
    public static int GAME_WIDTH = 732;
    public static int GAME_HEIGHT = 1080;

    private final ImageIcon TETRIS_GRID;
    private Randomizer randomizer;


    public PieceType[][] grid;
    public PieceType hold;
    public Piece current;

    public Tetris(){
        TETRIS_GRID =  new ImageIcon(Assets.Game.TETRIS_GRID);
        randomizer = new RandomizerSevenBag();

        grid = new PieceType[31][12];
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], PieceType.NULL);
            grid[i][0] = PieceType.BORDER;
            grid[i][11] = PieceType.BORDER;
        }

        current = new Piece(randomizer.getNextPiece());
    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        g.drawImage(TETRIS_GRID.getImage(), 0, 1080/2 - TETRIS_GRID.getIconHeight()/2, TETRIS_GRID.getIconWidth(), TETRIS_GRID.getIconHeight(), null);

        g.setColor(Color.BLUE);

        drawGrid(g);
        drawPiece(g, current);
        return image;
    }

    public long lastDropTimer = 144;

    public void update(){
        lastDropTimer--;
        if(lastDropTimer==0){
            current.centerY++;
            lastDropTimer = 144;
        }
    }

    private void drawGrid(Graphics2D g){
        for (int row = 1; row < grid.length; row++) {
            for (int column = 1; column <= 10; column++) {
                drawSquare(g, grid[row][column], row, column);
            }
        }
    }

    private void drawPiece(Graphics2D g, Piece piece){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                drawSquare(g, piece.currentPieceGrid[i][j], piece.centerY-2+i, piece.centerX-2+j);
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

    public void die(){

    }

    private void drawSquare(Graphics2D g, PieceType piece, int row, int column){
        switch(piece){
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
            case GHOST:
                g.setColor(Color.GRAY);
                break;
            default:
                return;
        }
        g.fillRect(179 + 35*(column-1), -160 + 35*row, 34, 34);
    }
}
