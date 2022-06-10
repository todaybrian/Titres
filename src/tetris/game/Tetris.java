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
    private Tetromino[][] occupiedGrid = new Tetromino[10][25];
    private Tetromino activePiece;
    // The array below is me lazily declaring a bunch of dummy holder tetrominos at once.
    private Tetromino[] tetrominoList = new Tetromino[]{new Tetromino(1), new Tetromino(2), new Tetromino(3), new Tetromino(4), new Tetromino(5), new Tetromino(6), new Tetromino(7)};
    RandomizerSevenBag randomizer = new RandomizerSevenBag();

    private final ImageIcon TETRIS_GRID;

    public Tetris(){
        TETRIS_GRID =  new ImageIcon(Assets.Game.TETRIS_GRID);
        putTheNextTetrominoOnScreen();
        moveActive(0,5);

    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        //g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g.drawImage(TETRIS_GRID.getImage(), 0, 1080/2 - TETRIS_GRID.getIconHeight()/2, TETRIS_GRID.getIconWidth(), TETRIS_GRID.getIconHeight(), null);
        drawGrid(g);
        return image;
    }

    public void drawGrid(Graphics2D g) {
        for (int i = 0; i < 10; i++) { // ik this is o(n*m) time but idk how else to render everything
            for (int j = 0; j < 25; j++) {
                try {
                    g.setColor(occupiedGrid[i][j].color);
                    g.fillRect(180+Tetromino.SQUARE_SIDE*i+i-1, 1080/2 - TETRIS_GRID.getIconHeight()/2+Tetromino.SQUARE_SIDE*(j-4)+(j-4)+1, Tetromino.SQUARE_SIDE, Tetromino.SQUARE_SIDE);
                } catch (NullPointerException e) {

                }
            }
        }
    }

    public void moveActive(int x, int y) { //TODO: Map this to keys and time passed
        for (int[] arr : activePiece.getPosition()) { // If you know how do to this more efficiently, please do it!
            occupiedGrid[arr[0]][arr[1]] = null;
            arr[0] +=x;
            arr[1] +=y;
        }
        for (int[] arr : activePiece.getPosition()) {
            occupiedGrid[arr[0]][arr[1]] = activePiece;
        }
    }

    private void convertActiveToDead(){
        //TODO: take position of active piece in occupiedGrid and add them to relevant dead piece
    }

    public void putTheNextTetrominoOnScreen() {
        activePiece = new Tetromino(randomizer.getNext());
        switch(activePiece.pieceName.id) {
            case 1:
                occupiedGrid[6][2] = activePiece;
                occupiedGrid[4][3] = activePiece;
                occupiedGrid[5][3] = activePiece;
                occupiedGrid[6][3] = activePiece;
                activePiece.setPosition(new int[][]{{6,2},{4,3},{5,3},{6,3}});
                break;
            case 2:
                occupiedGrid[4][2] = activePiece;
                occupiedGrid[4][3] = activePiece;
                occupiedGrid[5][3] = activePiece;
                occupiedGrid[6][3] = activePiece;
                activePiece.setPosition(new int[][]{{4,2},{4,3},{5,3},{6,3}});
                break;
            case 3:
                occupiedGrid[5][2] = activePiece;
                occupiedGrid[6][3] = activePiece;
                occupiedGrid[4][3] = activePiece;
                occupiedGrid[5][3] = activePiece;
                activePiece.setPosition(new int[][]{{5,2},{4,3},{5,3},{6,3}});
                break;
            case 4:
                occupiedGrid[4][3] = activePiece;
                occupiedGrid[5][3] = activePiece;
                occupiedGrid[6][3] = activePiece;
                occupiedGrid[7][3] = activePiece;
                activePiece.setPosition(new int[][]{{7,3},{4,3},{5,3},{6,3}});
                break;
            case 5:
                occupiedGrid[5][2] = activePiece;
                occupiedGrid[6][2] = activePiece;
                occupiedGrid[5][3] = activePiece;
                occupiedGrid[6][3] = activePiece;
                activePiece.setPosition(new int[][]{{5,2},{6,2},{5,3},{6,3}});
                break;
            case 6:
                occupiedGrid[4][2] = activePiece;
                occupiedGrid[5][2] = activePiece;
                occupiedGrid[5][3] = activePiece;
                occupiedGrid[6][3] = activePiece;
                activePiece.setPosition(new int[][]{{4,2},{5,2},{5,3},{6,3}});
                break;
            case 7:
                occupiedGrid[5][2] = activePiece;
                occupiedGrid[6][2] = activePiece;
                occupiedGrid[4][3] = activePiece;
                occupiedGrid[5][3] = activePiece;
                activePiece.setPosition(new int[][]{{6,2},{5,2},{5,3},{4,3}});
                break;
        }
    }
}
