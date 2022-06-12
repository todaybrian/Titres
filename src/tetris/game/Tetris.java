package tetris.game;

import tetris.game.randomizer.Randomizer;
import tetris.game.randomizer.RandomizerSevenBag;
import tetris.util.Assets;
import tetris.util.FrameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class Tetris extends Rectangle {

    //Game width and height of the gameboard only
    public static int GAME_WIDTH = 732;
    public static int GAME_HEIGHT = 1080;

    private final Image TETRIS_GRID;
    private Randomizer randomizer;


    public PieceType[][] grid;
    public PieceType hold;
    public Piece current;

    public FrameTimer dropTimer = new FrameTimer(1);
    public FrameTimer lockTimer = new FrameTimer(0.5);

    public Tetris(){
        TETRIS_GRID =  Assets.Game.TETRIS_GRID.get();
        randomizer = new RandomizerSevenBag();

        grid = new PieceType[31][12];
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], PieceType.NULL);
            grid[i][0] = PieceType.BORDER;
            grid[i][11] = PieceType.BORDER;
        }
        Arrays.fill(grid[30], PieceType.BORDER);

        current = new Piece(randomizer.getNextPiece());
    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        g.drawImage(TETRIS_GRID, 0, 1080/2 - TETRIS_GRID.getHeight(null)/2, TETRIS_GRID.getWidth(null), TETRIS_GRID.getHeight(null), null);

        g.setColor(Color.BLUE);

        drawGrid(g);
        drawPiece(g, current);

        drawSquare(g, PieceType.I, 10, 1);
        drawSquare(g, PieceType.I, 29, 10);

        return image;
    }


    public void update(){
        if(dropTimer.isDone()){
            dropPiece();
            dropTimer.reset();
        }
        if (!onGround()) {
            lockTimer.disable();
        }
        if(onGround()){
            if(lockTimer.isDone()){
                setPiece();
                lockTimer.disable();
            } else if(lockTimer.isDisabled()){
                lockTimer.reset();
            }
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
        int length = 3;
        if(current.type == PieceType.I){
            length = 4;
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                drawSquare(g, piece.currentPieceGrid[i][j], piece.centerY - 1 + i, piece.centerX - 1 + j);
            }
        }
    }

    public void moveRight(){
        Piece temp = current.clone();
        temp.centerX++;
        if(checkLegal(temp)){
            current.centerX++;
        }
    }

    public void moveLeft(){
        Piece temp = current.clone();
        temp.centerX--;
        if(checkLegal(temp)){
            current.centerX--;
        }
    }

    public boolean dropPiece(){
        Piece temp = current.clone();
        temp.centerY++;
        if(checkLegal(temp)){
            current.centerY++;
            return true;
        } else return false;
    }

    public void setPiece(){
        if(!checkLegal(current)) return;
        int length = 3;
        if(current.type == PieceType.I){
            length = 4;
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (current.currentPieceGrid[i][j] != PieceType.NULL) {
                    grid[current.centerY + i - 1][current.centerX + j - 1] = current.currentPieceGrid[i][j];
                }
            }
        }

        spawnPiece();
    }

    public void spawnPiece(){
        current = new Piece(randomizer.getNextPiece());
        if(!checkLegal(current)){
            die();
        }
    }

    public void hardDrop(){
        while(dropPiece());
        setPiece();
    }

    public void rotateCW(){
        if(current.type == PieceType.O){ //The O piece doesn't rotate or follow any wall kicks so we don't need to check
            return;
        }
        Piece temp = current.clone();
        temp.rotateCW();
        if(checkLegal(temp)){
            current.rotateCW();
        }
    }

    public boolean onGround(){
        Piece temp = current.clone();
        temp.centerY++;
        return !checkLegal(temp);
    }

    public void die(){

    }

    public boolean checkLegal(Piece piece){
        int length = 3;
        if(current.type == PieceType.I){
            length = 4;
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if(piece.currentPieceGrid[i][j] != PieceType.NULL){
                    if(grid[piece.centerY-1+i][piece.centerX-1+j] != PieceType.NULL){
                        return false;
                    }
                }
            }
        }

        return true;
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
