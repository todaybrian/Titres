package tetris.game;

import tetris.game.randomizer.Randomizer;
import tetris.game.randomizer.RandomizerSevenBag;
import tetris.util.Assets;
import tetris.util.FrameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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

        grid = new PieceType[30][10];
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], PieceType.NULL);
        }

        current = new Piece(randomizer.getNextPiece());
    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        g.drawImage(TETRIS_GRID, 0, 1080/2 - TETRIS_GRID.getHeight(null)/2, TETRIS_GRID.getWidth(null), TETRIS_GRID.getHeight(null), null);

        g.setColor(Color.BLUE);

        drawGrid(g);

        Piece ghost = current.clone();
        ghost.centerY = findDropHeight();
        drawPiece(g, ghost, true);

        drawPiece(g, current, false);

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
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                drawSquare(g, grid[row][column], row, column);
            }
        }
    }

    private void drawPiece(Graphics2D g, Piece piece, boolean isGhost){
        int length = 3;
        if(current.type == PieceType.I){
            length = 4;
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                PieceType type = piece.currentPieceGrid[i][j];
                if(isGhost && type != PieceType.NULL){
                    type = PieceType.GHOST;
                }
                drawSquare(g, type, piece.centerY - 1 + i, piece.centerX - 1 + j);
            }
        }
    }

    public boolean moveRight(){
        Piece temp = current.clone();
        temp.centerX++;
        if(checkLegal(temp)){
            current.centerX++;
            return true;
        }
        return false;
    }

    public boolean moveLeft(){
        Piece temp = current.clone();
        temp.centerX--;
        if(checkLegal(temp)){
            current.centerX--;
            return true;
        }
        return false;
    }

    public int findDropHeight(){
        Piece temp = current.clone();
        temp.centerY++;
        while(checkLegal(temp)){
            temp.centerY++;
        }
        return temp.centerY - 1;
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

        clearLines();
        spawnPiece();
    }

    public void spawnPiece(){
        spawnPiece(randomizer.getNextPiece());
    }

    public void spawnPiece(PieceType type){
        current = new Piece(type);
        if(!checkLegal(current)){
            die();
        }
    }

    public void hardDrop(){
        current.centerY = findDropHeight();
        setPiece();
    }

    public void rotateCW(){
        if(current.type == PieceType.O){ //The O piece doesn't rotate or follow any wall kicks so we don't need to check
            return;
        }
        Piece temp = current.clone();
        temp.rotateCW();
        int[][][] wallKick;
        if(temp.type == PieceType.I){
            wallKick = PieceType.wallKickDataI;
        } else{
            wallKick = PieceType.wallKickDataJLSTZ;
        }
        for (int i = 0; i < 5; i++) {
            Piece temp2 = temp.clone();
            temp2.centerX += wallKick[current.rotationIndex][i][0];
            temp2.centerY -= wallKick[current.rotationIndex][i][1];
            if(checkLegal(temp2)){
                current = temp2;
                return;
            }
        }
    }

    public void rotateCCW(){
        if(current.type == PieceType.O){ //The O piece doesn't rotate or follow any wall kicks so we don't need to check
            return;
        }
        Piece temp = current.clone();
        temp.rotateCCW();
        int[][][] wallKick;
        if(temp.type == PieceType.I){
            wallKick = PieceType.wallKickDataI;
        } else{
            wallKick = PieceType.wallKickDataJLSTZ;
        }
        for (int i = 0; i < 5; i++) {
            Piece temp2 = temp.clone();
            temp2.centerX -= wallKick[temp2.rotationIndex][i][0];
            temp2.centerY += wallKick[temp2.rotationIndex][i][1];
            if(checkLegal(temp2)){
                current = temp2;
                return;
            }
        }
    }

    public boolean onGround(){
        Piece temp = current.clone();
        temp.centerY++;
        return !checkLegal(temp);
    }

    public ArrayList<Integer> clearLines(){
        PieceType[][] temp = new PieceType[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            temp[i] = new PieceType[grid[i].length];
            Arrays.fill(temp[i], PieceType.NULL);
        }
        int lstFilled = grid.length-1;

        ArrayList<Integer> lines = new ArrayList<>();
        for (int i = grid.length-1; i >=1; i--) {
            boolean full = true;
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == PieceType.NULL){
                    full = false;
                    temp[lstFilled] = grid[i];
                    lstFilled--;
                    break;
                }
            }
            if(full){
                lines.add(i);
            }
        }
        grid = temp;
        return lines;
    }

    public void die(){

    }

    public void holdPiece(){
        if(hold == null){
            hold = current.type;
            spawnPiece();
        } else {
            PieceType temp = current.type;
            spawnPiece(hold);
            hold = temp;
        }
    }

    public boolean checkLegal(Piece piece){
        int length = 3;
        if(current.type == PieceType.I){
            length = 4;
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if(piece.currentPieceGrid[i][j] != PieceType.NULL){
                    if(piece.centerY + i - 1 < 0 || piece.centerY + i - 1 >= grid.length || piece.centerX + j - 1 < 0 || piece.centerX + j - 1 >= grid[0].length){
                        return false;
                    }
                    if(grid[piece.centerY-1+i][piece.centerX-1+j] != PieceType.NULL){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void drawSquare(Graphics2D g, PieceType piece, int row, int column){
        if(piece.getId() == -1){
            return;
        }
        g.drawImage(Assets.Game.PIECES.get(), 179 + 35*(column), -160 + 35*row, 179 + 35*(column)+34,-160 + 35*row+34, 35*piece.getId(), 0, 35*piece.getId()+34, 34, null);
    }
}
