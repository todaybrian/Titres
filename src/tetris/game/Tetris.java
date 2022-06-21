/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Class to handle tetris game logic. Includes game board graphic, game board grid data,
 * and timers. It is separated from the menu system because it allows greater flexibility for animations and allows
 * for multiple instances for a future multiplayer feature.
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
    //The board spans the entire height of the screen as tetris pieces fall vertically, and allows for future tetris ghost pieces
    // No pieces are rendered horizontally beyond the grid width
    public final static int BOARD_WIDTH = 732;
    public final static int BOARD_HEIGHT = 1080;

    public final static int SQUARE_LENGTH = 34;

    //Store the Tetris Grid Image
    private final Image TETRIS_GRID;

    //Store the randomizer that will be used to generate the next piece
    private Randomizer randomizer;

    // The grid of all pieces
    public PieceType[][] grid;

    // The currently held piece
    public Piece hold;

    // Can the user switch their held piece?
    public boolean canSwitchHold;

    // Currently actively dropping piece
    public Piece current;

    // The next few pieces to be spawned (for display/strategy purposes)
    public ArrayList<PieceType> next;

    // Number of lines cleared since last line goal was met (or since game started if none met)
    public int linesCleared;

    // Amount of lines required to be cleared before something happens
    public int lineGoal;

    // Start time of the game, used for game timer
    private long timeStarted;

    // Time between each drop
    public FrameTimer dropTimer;

    // Time after piece lands before it is locked and set in place
    public FrameTimer lockTimer = new FrameTimer(0.5);

    // Has the game been lost?
    private boolean died;

    // Has the game been won?
    private boolean objectiveCompleted;

    // Stores the score at the end of the game, to be displayed on results screen
    private long finalScore;

    // The current frame
    private long currentUpdateFrame;

    // The latest time that a soft drop command was received
    private long lastSoftDrop;

    // The current game mode
    private GameMode gameMode;

    // Current level; how fast pieces are currently dropping
    private int level;

    public Tetris(GameMode gameMode) {
        this.TETRIS_GRID =  Assets.Game.TETRIS_GRID.get();
        this.randomizer = new RandomizerSevenBag();

        //Initialize the grid. The grid is 30 x 10 because it creates a buffer on the top of the game board for pieces to be placed
        //It also allows for future tetris ghost pieces (multiplayer) to be rendered
        this.grid = new PieceType[30][10];
        for (PieceType[] pieceTypes : grid) { //Fill each row with empty pieces
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
        //Create a new image to draw on
        BufferedImage image = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        Util.setGraphicsFlags(g); //Make the game look better on different monitors

        g.drawImage(TETRIS_GRID, 0, 1080/2 - TETRIS_GRID.getHeight(null)/2, TETRIS_GRID.getWidth(null), TETRIS_GRID.getHeight(null), null);

        drawGrid(g);

        if(!this.died) {
            Piece ghost = current.clone();
            ghost.centerY = findDropHeight();
            drawPiece(g, ghost, true, false);
            drawPiece(g, current, false, onGround());
        }
        // If there is a hold piece, draw it
        if (hold != null) {
            drawHold(g);
        }

        //Draw the next few pieces in the bag
        drawNext(g);

        //Draw the lines cleared and the time passed/left
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
            if (timeStarted == -1) { // Holds timer at 2 minutes before game starts
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

        //Grid of the tetris array is 3x3 except for the 'I' piece which is 4x4.
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

    /**
     * Checks the grid for any lines that are full and clears them.
     */
    public void clearLines(){
        //Create a temporary grid to store the resultant grid after clearing lines
        PieceType[][] temp = new PieceType[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            temp[i] = new PieceType[grid[i].length];
            Arrays.fill(temp[i], PieceType.NULL);
        }

        //The last filled row is the lowest row.
        int lstFilled = grid.length-1;

        //Iterating from the lowest row to the highest
        for (int i = grid.length-1; i >=1; i--) {
            //Check if the row is full

            boolean full = true;
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == PieceType.NULL){ //Row is not full
                    full = false;
                    temp[lstFilled] = grid[i]; //This row is not full, so copy it to the last filled row of the temp grid
                    lstFilled--; //Move the last filled row upwards by one
                    break;
                }
            }

            //If the row is full, we add it to the number of lines cleared
            if(full){
                this.linesCleared++;
            }
        }
        //Copy the temp grid to the real grid
        grid = temp;
    }

    public void checkObjectives(){
        if(linesCleared >= lineGoal){
            if(gameMode == GameMode.FORTY_LINES){
                objectiveCompleted(System.currentTimeMillis()-timeStarted);
            } else if(gameMode == GameMode.BLITZ){
                increaseLevel();
                lineGoal = 3*level;
            }
        }
        if(System.currentTimeMillis()- timeStarted >= 120*1e3 && gameMode == GameMode.BLITZ){
            objectiveCompleted(linesCleared);
        }
    }

    /**
     * Kills the player.
     */
    public void die(){
        died = true;
    }

    /**
     * @return True if the player is dead, false otherwise
     */
    public boolean isDied(){
        return died;
    }

    /**
     * Set objective completed.
     */
    public void objectiveCompleted(long finalScore){
        this.finalScore = finalScore;
        objectiveCompleted = true;
    }

    /**
     * @return The final score of the player
     */
    public long getFinalScore() {
        return this.finalScore;
    }

    /**
     * @return True if the player has completed the objective, false otherwise
     */
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

    /**
     * Draws a square of the given type at the given coordinates.
     *
     * If the piece is on the ground, it will blink.
     *
     * @param g The graphics object to draw on
     * @param piece The type of piece to draw
     * @param row The row of the grid
     * @param column The column of the grid
     * @param onGround Whether the piece is on the ground or not
     */
    private void drawSquare(Graphics2D g, PieceType piece, double row, double column, boolean onGround){
        if(piece.getId() == -1){ //null piece
            return;
        }
        //Position found through trial and error. 1 is added to account for the grid lines.
        int xPos = (int)(179 + (SQUARE_LENGTH +1)*(column));
        int yPos = (int) (-160 + (SQUARE_LENGTH +1)*row);

        //Get the position of the square in the sprite. 1 is added to account for spacing in the sprite
        int xPosInSprite = (SQUARE_LENGTH +1)*piece.getId();

        //Draw the square
        g.drawImage(Assets.Game.PIECES.get(), xPos, yPos, xPos + SQUARE_LENGTH, yPos + SQUARE_LENGTH, xPosInSprite, 0, xPosInSprite+ SQUARE_LENGTH, SQUARE_LENGTH, null);

        //If the piece is on the ground, we draw a blinking animation to indicate that the piece will be settled
        if(onGround) {
            //The opacity of the blinking animation, based on sin wave equation on the current update frame
            int opacity = Math.abs(((int) (Math.sin(currentUpdateFrame/30.0)*100))+50);

            //Draw the blinking animation above the square
            g.setColor(new Color(255, 255, 255, opacity));
            g.fillRect(xPos, yPos, SQUARE_LENGTH, SQUARE_LENGTH);
        }
    }

    public GameMode getGameMode() { // Allows Gui class to access the current game mode
        return gameMode;
    }
}
