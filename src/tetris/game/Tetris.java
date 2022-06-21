/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Class to handle tetris game logic. Includes game board graphic, game board grid data,
 * and timers.
 */

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
    public Piece hold;
    public boolean canSwitchHold;
    public Piece current;
    public ArrayList<PieceType> next;
    public int linesCleared;
    public int lineGoal;
    private long timeStarted;

    public FrameTimer dropTimer;
    public FrameTimer lockTimer = new FrameTimer(0.5);

    private boolean died;
    private boolean objectiveCompleted;
    private long finalScore;

    private long currentUpdateFrame;

    private long lastSoftDrop;

    private GameMode gameMode;

    private int level;

    public Tetris(GameMode gameMode) {
        this.TETRIS_GRID =  Assets.Game.TETRIS_GRID.get();
        this.randomizer = new RandomizerSevenBag();

        this.grid = new PieceType[30][10];
        for (PieceType[] pieceTypes : grid) {
            Arrays.fill(pieceTypes, PieceType.NULL);
        }

        this.current = new Piece(randomizer.popNextPiece());

        this.linesCleared = 0;
        this.timeStarted = -1;
        this.died = false;
        this.gameMode = gameMode;
        this.next = randomizer.getNextPieces(5);
        this.canSwitchHold = true;
        increaseLevel();

        if(gameMode == GameMode.FORTY_LINES){
            lineGoal = 40;
        } else if (gameMode == GameMode.BLITZ) {
            lineGoal = 3;
        }
    }

    public Image drawImage(){
        BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        Util.setGraphicsFlags(g); //Make the game look better on different monitors

        g.drawImage(TETRIS_GRID, 0, 1080/2 - TETRIS_GRID.getHeight(null)/2, TETRIS_GRID.getWidth(null), TETRIS_GRID.getHeight(null), null);

        g.setColor(Color.BLUE);

        drawGrid(g);

        if(!this.died) {
            Piece ghost = current.clone();
            ghost.centerY = findDropHeight();
            drawPiece(g, ghost, true, false);
            drawPiece(g, current, false, onGround());
        }
        if (hold != null) {
            drawHold(g);
        }
        drawNext(g);
        drawSidebar(g);
        return image;
    }


    public void update(){
        //Get current time, used for oscillating animation of piece when it is on the ground but not locked
        this.currentUpdateFrame++; //Get current time
        if(died || objectiveCompleted){
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
                lastSoftDrop = System.currentTimeMillis();
                lockTimer.disable();
            } else if(lockTimer.isDisabled()){
                lockTimer.reset();
            }
        }
        checkObjectives();
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

        FontMetrics fm = g.getFontMetrics(); //Get font metrics for the font

        int edgeOfLeftSidebar = 168;

        g.drawString("TIME", edgeOfLeftSidebar - fm.stringWidth("TIME"), 840);
        g.drawString("LINES", edgeOfLeftSidebar - fm.stringWidth("LINES"), 760);


        g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 23));
        int minutes,seconds,millis;
        if (gameMode != GameMode.BLITZ) {
            minutes = (int) ((System.currentTimeMillis() - timeStarted) / 1000 / 60);
            seconds = ((int) (System.currentTimeMillis() - timeStarted) / 1000) % 60;
            millis = (int) (System.currentTimeMillis() - timeStarted) % 1000;
            if (timeStarted == -1) {
                minutes = seconds = millis = 0;
            }
        } else { // makes timer for blitz count down
            minutes = 1-(int) ((System.currentTimeMillis() - timeStarted) / 1000 / 60);
            seconds = 59-((int) (System.currentTimeMillis() - timeStarted) / 1000) % 60;
            millis = 1000-(int) (System.currentTimeMillis() - timeStarted) % 1000;
            if (timeStarted == -1) {
                minutes = 2;
                seconds = millis = 0;
            }
        }


        //Milliseconds
        String millisString = String.format(".%03d", millis);
        g.drawString(millisString, 123, 880);

        //Line Objective
        String objective = String.format(" / %d", lineGoal);
        g.drawString(String.format(" / %d", lineGoal), edgeOfLeftSidebar - fm.stringWidth(objective), 800);

        g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 40));

        fm = g.getFontMetrics(); // Font changed, get new metrics

        String minutesSeconds = String.format("%d:%02d", minutes, seconds);

        g.drawString(minutesSeconds, 122 - fm.stringWidth(minutesSeconds), 880);

        g.drawString(String.valueOf(linesCleared), 205 - fm.stringWidth(objective)-fm.stringWidth(String.valueOf(linesCleared)), 800);
    }


    //The following two methods were separated for expandability reasons (more animations were wanted)

    /**
     * Attempt to move the current Tetris piece to the right.
     * This function is called when the user clicks the key to move the piece to the right
     */
    public void moveRight(){
        Piece temp = current.clone(); //Create a temporary clone of the tetris object
        temp.centerX++; //Move it to the right
        if(checkLegal(temp)){ //Check if movement is legal before moving real piece
            current.centerX++;
        }
    }

    /**
     * Attempt to move the current Tetris piece to the left.
     * This function is called when the user clicks the key to move the piece to the left
     */
    public void moveLeft(){
        Piece temp = current.clone(); //Create a temporary clone of the tetris object
        temp.centerX--; //Move it to the left
        if(checkLegal(temp)){ //Check if movement is legal before moving real piece
            current.centerX--;
        }
    }


    /**
     * Drop the piece by 1 block.
     * Called during soft drop, or gravity.
     */
    public void dropPiece(){
        Piece temp = current.clone(); //Create a temporary clone of the tetris object
        temp.centerY++; //Move it down by 1
        if(checkLegal(temp)){  //Check if movement is legal before moving real piece
            current.centerY++;
        }
    }

    /**
     * Find the lowest height the block can go if it were to continue dropping downwards
     */
    public int findDropHeight(){
        Piece temp = current.clone();
        temp.centerY++;
        while(checkLegal(temp)){ //While it is legal, continue dropping the temporary block.
            temp.centerY++;
        }
        return temp.centerY - 1; // We are 1 block beyond the legal drop height, so subtract 1 to compensate.
    }

    /**
     * Hard drop the piece downwards.
     */
    public void hardDrop(){
        //Prevent accidental hard drops
        //If the last soft drop was 500 milliseconds ago, don't allow a hard drop
        //This can happen if a user wants to hard drop, but the soft drop timer finishes, leading to
        //an accidental hard drop of the next piece.
        if(System.currentTimeMillis() - lastSoftDrop < 500){
            return;
        }
        current.centerY = findDropHeight(); //Set the block to the lowest height possible by gravity
        setPiece();
    }

    /**
     * Sets the piece into the grid
     */
    public void setPiece(){
        if(!checkLegal(current)) return; //Not legal, so piece setting not possible

        //Grid of the tetris array is 3x3 except for the I piece which is 4x4.
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
        if (!canSwitchHold) {
            canSwitchHold = true;
        }
        clearLines();
        spawnPiece();
    }

    public void spawnPiece(){
        spawnPiece(randomizer.popNextPiece());
        next = randomizer.getNextPieces(5);
    }

    public void spawnPiece(PieceType type){
        current = new Piece(type);
        if(!checkLegal(current)){
            die();
        }
    }

    public void increaseLevel(){
        this.level++;
        double secondsPerRow = 1.72 * Math.exp(-0.4* level);
        dropTimer = new FrameTimer(secondsPerRow);
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

    public void clearLines(){
        PieceType[][] temp = new PieceType[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            temp[i] = new PieceType[grid[i].length];
            Arrays.fill(temp[i], PieceType.NULL);
        }
        int lstFilled = grid.length-1;

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
                this.linesCleared++;
            }
        }
        grid = temp;
    }

    public void checkObjectives(){
        if(linesCleared >= lineGoal){
            if(gameMode == GameMode.FORTY_LINES){
                objectiveCompleted(System.currentTimeMillis()-timeStarted);
            } else if(gameMode == GameMode.BLITZ){
                increaseLevel();
                lineGoal = 2*level-1;
            }
        }
        if(System.currentTimeMillis()- timeStarted >= 120*1e3 && gameMode == GameMode.BLITZ){
            objectiveCompleted(linesCleared);
        }
    }

    public void die(){
        died = true;
    }

    public boolean isDied(){
        return died;
    }

    public void objectiveCompleted(long finalScore){
        this.finalScore = finalScore;
        objectiveCompleted = true;
    }

    public long getFinalScore() {
        return this.finalScore;
    }

    public boolean isObjectiveCompleted(){
        return objectiveCompleted;
    }

    private void drawHold(Graphics2D g) {
        int length = 3;
        if(hold.type == PieceType.I){
            length = 4;
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                PieceType type = hold.currentPieceGrid[i][j];
                if(!canSwitchHold && type != PieceType.NULL){
                    type = PieceType.GHOST;
                }
                if (hold.type == PieceType.I) {
                    drawSquare(g, type, 10.85 + i, -4.6 + j, false);
                } else if (hold.type == PieceType.O) {
                    drawSquare(g, type, 11.2 + i, -3.5 + j, false);
                } else {
                    drawSquare(g, type, 11.2 + i, -4 + j, false);
                }
            }
        }
    }

    public void drawNext(Graphics2D g) {
        for (int i = 0; i < next.size(); i++) {
            Piece temp = new Piece(next.get(i));
            int length = 3;
            if(next.get(i) == PieceType.I){
                length = 4;
            }
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < length; k++) {
                    PieceType type = temp.currentPieceGrid[j][k];
                    if (temp.type == PieceType.I) {
                        drawSquare(g, type, 10.85 + i*3 + j, 11.4 + k, false);
                    } else if (temp.type == PieceType.O) {
                        drawSquare(g, type, 11.2 + i*length + j, 12.5 + k, false);
                    } else {
                        drawSquare(g, type, 11.2 + i*length + j, 12 + k, false);
                    }
                }
            }
        }
    }

    public void holdPiece(){
        if (!canSwitchHold) return;
        if(hold == null){
            hold = new Piece(current.type);
            spawnPiece();
        } else {
            Piece temp = new Piece(current.type);
            spawnPiece(hold.type);
            hold = temp.clone();
        }
        canSwitchHold = false;
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

    private void drawSquare(Graphics2D g, PieceType piece, double row, double column, boolean onGround){
        if(piece.getId() == -1){
            return;
        }
        g.drawImage(Assets.Game.PIECES.get(), (int) (179 + 35*(column)), (int) (-160 + 35*row), (int) (179 + 35*(column)+34), (int) (-160 + 35*row+34), 35*piece.getId(), 0, 35*piece.getId()+34, 34, null);
        if(onGround) {
            int opacity = Math.abs(((int) (Math.sin(currentUpdateFrame/30.0)*100))+50);
            g.setColor(new Color(255, 255, 255, opacity));
            g.fillRect((int)(179 + 35 * (column)), (int)(-160 + 35 * row), 34, 34);
        }
    }

    public GameMode getGameMode() { // Allows Gui class to access the current game mode
        return gameMode;
    }
}
