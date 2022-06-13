package tetris.game;

import tetris.game.randomizer.Randomizer;
import tetris.game.randomizer.RandomizerSevenBag;
import tetris.util.Assets;
import tetris.util.FrameTimer;
import tetris.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Tetris extends Rectangle {

    //Game width and height of the gameboard only
    public static int GAME_WIDTH = 732;
    public static int GAME_HEIGHT = 1080;

    private final Image TETRIS_GRID;
    private Randomizer randomizer;


    public PieceType[][] grid;
    public PieceType hold;
    public Piece current;

    public int linesCleared;
    private long timeStarted;

    public FrameTimer dropTimer;
    public FrameTimer lockTimer = new FrameTimer(0.5);

    private boolean died;

    private long currentUpdateFrame;

    public Tetris(){
        this.TETRIS_GRID =  Assets.Game.TETRIS_GRID.get();
        this.randomizer = new RandomizerSevenBag();

        this.grid = new PieceType[30][10];
        for (PieceType[] pieceTypes : grid) {
            Arrays.fill(pieceTypes, PieceType.NULL);
        }

        this.current = new Piece(randomizer.getNextPiece());

        this.linesCleared = 0;
        this.timeStarted = -1;
        this.died = false;

        setLevel(1);
    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        Util.setGraphicsFlags(g);

        g.drawImage(TETRIS_GRID, 0, 1080/2 - TETRIS_GRID.getHeight(null)/2, TETRIS_GRID.getWidth(null), TETRIS_GRID.getHeight(null), null);

        g.setColor(Color.BLUE);

        drawGrid(g);

        if(!this.died) {
            Piece ghost = current.clone();
            ghost.centerY = findDropHeight();
            drawPiece(g, ghost, true, false);

            drawPiece(g, current, false, onGround());
        }
        drawSidebar(g);
        return image;
    }


    public void update(){
        //Get current time, used for oscillating animation of piece when it is on the ground but not locked
        this.currentUpdateFrame++; //Get current time
        if(died){
            return;
        }
        if(timeStarted == -1){
            timeStarted = System.currentTimeMillis();
        }
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
                drawSquare(g, grid[row][column], row, column, false);
            }
        }
    }

    private void drawPiece(Graphics2D g, Piece piece, boolean isGhost, boolean onGround){
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
                drawSquare(g, type, piece.centerY - 1 + i, piece.centerX - 1 + j, onGround);
            }
        }
    }

    private void drawSidebar(Graphics2D g){
        g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.PLAIN, 23));
        g.setColor(Color.WHITE);

        g.drawString("TIME", 120, 840);


        g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 23));
        int minutes = (int)((System.currentTimeMillis() - timeStarted)/1000/60);
        int seconds = ((int)(System.currentTimeMillis() - timeStarted) / 1000)%60;
        int millis = (int)(System.currentTimeMillis() - timeStarted) % 1000;
        if(timeStarted == -1) {
           minutes = seconds = millis = 0;
        }
        FontMetrics fm = g.getFontMetrics();

        //Milliseconds
        String millisString = String.format(".%03d", millis);
        g.drawString(millisString, 123, 880);

        g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 40));
        String minutesSeconds = String.format("%d:%02d", minutes, seconds);

        g.drawString(minutesSeconds, 80 - fm.stringWidth(minutesSeconds), 880);

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

    public void setLevel(int level){
        double secondsPerRow = 1.72 * Math.exp(-0.499* level);
        dropTimer = new FrameTimer(1.0/secondsPerRow);
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
                this.linesCleared++;
            }
        }
        grid = temp;
        return lines;
    }

    public void die(){
        died = true;
    }

    public boolean isDied(){
        return died;
    }

    public void objectiveCompleted(){

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

    private void drawSquare(Graphics2D g, PieceType piece, int row, int column, boolean onGround){
        if(piece.getId() == -1){
            return;
        }
        g.drawImage(Assets.Game.PIECES.get(), 179 + 35*(column), -160 + 35*row, 179 + 35*(column)+34,-160 + 35*row+34, 35*piece.getId(), 0, 35*piece.getId()+34, 34, null);
        if(onGround) {
            int opacity = Math.abs(((int) (Math.sin(currentUpdateFrame/30.0)*100))+50);
            g.setColor(new Color(255, 255, 255, opacity));
            g.fillRect(179 + 35 * (column), -160 + 35 * row, 34, 34);
        }
    }
}
