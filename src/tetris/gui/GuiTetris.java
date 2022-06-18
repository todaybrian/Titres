package tetris.gui;

/* GuiTetris
Authors: Brian, Aaron
Date: 17 June 2022
Description: Handles displaying the tetris game board and connecting Tetris and GamePanel (i.e. controls).
 */

import tetris.controls.KeyboardInput;
import tetris.game.GameMode;
import tetris.game.Tetris;
import tetris.util.Assets;
import tetris.util.FrameTimer;
import tetris.util.Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GuiTetris extends Gui {
    private FrameTimer blackInTimer;

    private FrameTimer titleTimer;
    private FrameTimer countdownTimer;
    private FrameTimer goTimer;
    private FrameTimer resignTimer;

    Tetris tetris;

    private int xOffset = 0;
    private int yOffset = 0;
    private int xVelocity = 0;
    private int yVelocity = 0;

    private FrameTimer diedTimer;
    private GameMode gameMode;

    private boolean hasPlayedCountdownThree = false;
    private boolean hasPlayedCountdownTwo = false;
    private boolean hasPlayedCountdownOne = false;
    private FrameTimer downTimer = new FrameTimer(0.1);


    private boolean held_hardDrop = false;
    private boolean held_rotateCW = false;
    private boolean held_rotateCCW = false;
    private boolean held_holdPiece = false;

    private FrameTimer moveLeftTimerDAS = new FrameTimer(0.167);
    private FrameTimer moveLeftTimer = new FrameTimer(0.033);

    private FrameTimer hardDropAnimationTimer = new FrameTimer(0.1);

    private FrameTimer moveRightTimerDAS = new FrameTimer(0.167);
    private FrameTimer moveRightTimer = new FrameTimer(0.033);
    
    private KeyboardInput keyboardInput;
    
    public GuiTetris(GameMode gameMode) {
        super();
        this.keyboardInput = instance.keyboardInput;
        
        this.gameMode = gameMode;

        // this object handles all game logic; only tetris.drawImage() and tetris.update() will cause objects inside game board to change.
        tetris = new Tetris(gameMode);

        instance.getGameBackground().randomBackground();

        this.backgroundOpacity = 0.5f;

        blackInTimer = new FrameTimer(1);

        titleTimer = new FrameTimer(5);
        titleTimer.disable();

        diedTimer = new FrameTimer(1);
        diedTimer.disable();

        countdownTimer = new FrameTimer(3.3);
        countdownTimer.disable();

        goTimer = new FrameTimer(1);
        goTimer.disable();

        resignTimer = new FrameTimer(2.5);
        resignTimer.disable();
    }
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if(tetris.isDied()){ // This takes precedence over anything else since we need to transition to another screen if player is dead
            // diedTimer is used to hold things in place while dying animation plays
            if(diedTimer.isDisabled()){
                diedTimer.reset();
            }

            BufferedImage board = (BufferedImage) tetris.drawImage();

            g.rotate(Math.toRadians(20) *diedTimer.getProgress());

            g.drawImage(board, 1920 / 2 - Tetris.GAME_WIDTH / 2  + (int)(330 * diedTimer.getProgress()), 1080 / 2 - Tetris.GAME_HEIGHT / 2 + (int)(600 * diedTimer.getProgress()),null);
            if(diedTimer.isDone()){ // when diedTimer is done, transition to death screen
                instance.displayGui(new GuiMenuTransition(this, new GuiDied(gameMode)));
            }
        } else {
            if (!resignTimer.isDisabled()) {
                g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
                g.setColor(Color.WHITE);
                g.drawString("Keep holding ESC to resign", 750, 1000);
                g.drawImage(tetris.drawImage(), 1920 / 2 - Tetris.GAME_WIDTH / 2 + xOffset, 1080 / 2 - Tetris.GAME_HEIGHT / 2 + yOffset - (int) (1400 * (1 - blackInTimer.getProgress())), (int) (Tetris.GAME_WIDTH*(1-resignTimer.getProgress())), (int) (Tetris.GAME_HEIGHT*(1-resignTimer.getProgress())), null);
            } else {
                // Only draw the tetris board normally if player is not dead and is not resigning
                g.drawImage(tetris.drawImage(), 1920 / 2 - Tetris.GAME_WIDTH / 2 + xOffset, 1080 / 2 - Tetris.GAME_HEIGHT / 2 + yOffset - (int) (1400 * (1 - blackInTimer.getProgress())), Tetris.GAME_WIDTH, Tetris.GAME_HEIGHT, null);
            }
            if (!blackInTimer.isDone()) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (1 - blackInTimer.getProgress())));
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, instance.getWidth(), instance.getHeight());
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            } else if(!titleTimer.isDone()){
                float opacityProgress;
                if(titleTimer.getProgress() > 0.9){
                    //easeOutQuint
                    opacityProgress = (float) -(1-Math.pow(1-(titleTimer.getProgress()-0.9)/0.1,5));
                } else{
                    //easeOutQuint
                    opacityProgress = (float) (1-Math.pow(1-titleTimer.getProgress()/0.9,5));
                }

                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Util.clamp(0.5f + 0.5f * opacityProgress, 0, 1)));

                Image banner = gameMode.getBanner();
                g.drawImage(banner, 1920 / 2 - banner.getWidth(null) / 2, 1080 / 2 - banner.getHeight(null) / 2, null);

                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            } else if(!countdownTimer.isDone() && !countdownTimer.isDisabled()){
                long bufferTime = (long) ((1e9)*(countdownTimer.getLength() - 3.0f));

                g.setColor(Color.WHITE);

                Image countDown = null;
                long timeElapsedFromSecond= 0;

                if(countdownTimer.timeElapsed() - bufferTime > 2e9){
                    timeElapsedFromSecond = countdownTimer.timeElapsed() - bufferTime - (long)2e9;
                    countDown = Assets.Game.COUNTDOWN_1.get();
                    if(!hasPlayedCountdownOne){
                        instance.getSFXPlayer().play(Assets.SFX.COUNTDOWN_1.get());
                        hasPlayedCountdownOne = true;
                    }
                } else if(countdownTimer.timeElapsed() - bufferTime > 1e9){
                    timeElapsedFromSecond = countdownTimer.timeElapsed() - bufferTime - (long)1e9;
                    countDown = Assets.Game.COUNTDOWN_2.get();
                    if(!hasPlayedCountdownTwo){
                        instance.getSFXPlayer().play(Assets.SFX.COUNTDOWN_2.get());
                        hasPlayedCountdownTwo = true;
                    }
                } else if (countdownTimer.timeElapsed() - bufferTime > 0){
                    timeElapsedFromSecond = countdownTimer.timeElapsed() - bufferTime;
                    countDown =Assets.Game.COUNTDOWN_3.get();
                    if(!hasPlayedCountdownThree){
                        instance.getSFXPlayer().play(Assets.SFX.COUNTDOWN_3.get());
                        hasPlayedCountdownThree = true;
                    }
                }
                if(countDown != null){
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Util.clamp(1 - 1 * (float)timeElapsedFromSecond / (float)1e9, 0, 1)));
                    g.drawImage(countDown, 1920 / 2 - countDown.getWidth(null) / 2, 1080 / 2 - countDown.getHeight(null) / 2, null);
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
            }

            if(!goTimer.isDisabled() && !goTimer.isDone()) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Util.clamp((float) (1 - 1 * goTimer.getProgress()), 0, 1)));
                g.drawImage(Assets.Game.GO.get(), 1920 / 2 - Assets.Game.GO.get().getWidth(null) / 2, 1080 / 2 - Assets.Game.GO.get().getHeight(null) / 2, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
        }
    }

    @Override
    public void update(){ // This is called every time game physics needs to update
        super.update();
        if (tetris.isObjectiveCompleted()) { // If game completion requirements are fulfilled, immediately move to results.
            instance.displayGui(new GuiMenuTransition(this, new GuiResults(gameMode,tetris.getFinalScore())));
        }
/*        The following if/else structures work as follows:
            When a timer is not yet done, prevent anything below from updating.
            When that timer is done, start the next one.
            Only when all timers are done will the game physics update at tetris.update().
            At this point, all timers will be done and not disabled, preventing the pre-game timers from resetting.*/
        if(!blackInTimer.isDone()) {
            return;
        } else if(titleTimer.isDisabled()){ // Timer for game banner.
            titleTimer.reset();
            instance.getMusicPlayer().stopMusic();
            instance.getSFXPlayer().play(Assets.SFX.START_SOLO_GAME.get());
        }

        if(!titleTimer.isDone()){
            return;
        } else if(countdownTimer.isDisabled()){ // Timer for 3-2-1 countdown before game begins
            countdownTimer.reset();
        }

        if(!countdownTimer.isDone()){
            return;
        } else if(goTimer.isDisabled()){ // Grace period to account for the "1" animation to finish
            goTimer.reset();
            instance.getSFXPlayer().play(Assets.SFX.GO.get());
            if (tetris.getGameMode() != GameMode.BLITZ) { // Change the BGM to fit the game mode. "VIRTUAL_LIGHT" fits the stress of Blitz mode
                instance.getMusicPlayer().play(Assets.Music.VREMYA.get());
                instance.getMusicPlayer().setLoop(true);
            }  else {
                instance.getMusicPlayer().play(Assets.Music.VIRTUAL_LIGHT.get());
                instance.getMusicPlayer().setLoop(true);
            }
        }

        tetris.update(); // This updates the tetris game physics.

        // This timer only stores how long escape was pressed, resetting when it is pressed and disabling when it is released.
        // The game will only accept the resignation if it is pressed continuously for some time.
        // This ensures that an errant press of the escape key does not cause an accidental resign.
        if (keyboardInput.isKeyPressed(KeyEvent.VK_ESCAPE) && resignTimer.isDisabled()) {
            resignTimer.reset();
        } else if (!keyboardInput.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            resignTimer.disable();
        } else if (resignTimer.isDone()) { // Resignation takes the player back to the main menu.
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));
        }

        // "soft dropping" is rate limited to prevent a short press from bringing the piece all the way down
        if (downTimer.isDone() && keyboardInput.isKeyPressed(KeyEvent.VK_DOWN)) {
            downTimer.reset();
            tetris.dropPiece();
        }
        // "hard dropping" is disabled if the space bar is held to prevent multiple pieces from being hard dropped
        if (keyboardInput.isKeyPressed(KeyEvent.VK_SPACE) && !held_hardDrop) {
            tetris.hardDrop();
            hardDropAnimationTimer.reset();
        }
        held_hardDrop = keyboardInput.isKeyPressed(KeyEvent.VK_SPACE);

        // moving left and right is rate limited to prevent certain exploits
        if (keyboardInput.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (moveLeftTimerDAS.isDisabled()) {
                tetris.moveLeft();
                moveLeftTimerDAS.reset();
                moveLeftTimer.reset();
            } else if (moveLeftTimerDAS.isDone() && moveLeftTimer.isDone()) {
                tetris.moveLeft();
                moveLeftTimer.reset();
            }
        } else {
            moveLeftTimerDAS.disable();
        }

        if (keyboardInput.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (moveRightTimerDAS.isDisabled()) {
                tetris.moveRight();
                moveRightTimerDAS.reset();
                moveRightTimer.reset();
            } else if (moveRightTimerDAS.isDone() && moveRightTimer.isDone()) {
                tetris.moveRight();
                moveRightTimer.reset();
            }
        } else {
            moveRightTimerDAS.disable();
        }

        // same logic as hard drop disabling on hold
        if (keyboardInput.isKeyPressed(KeyEvent.VK_UP) && !held_rotateCW) {
            tetris.rotateCW();
        } else if (keyboardInput.isKeyPressed(KeyEvent.VK_CONTROL) && !held_rotateCCW) {
            tetris.rotateCCW();
        }
        held_rotateCW = keyboardInput.isKeyPressed(KeyEvent.VK_UP);
        held_rotateCCW = keyboardInput.isKeyPressed(KeyEvent.VK_CONTROL);

        if (keyboardInput.isKeyPressed(KeyEvent.VK_C) && !held_holdPiece) {
            tetris.holdPiece();
        }
        held_holdPiece = keyboardInput.isKeyPressed(KeyEvent.VK_C);

        // makes game board animate based on current velocity, prevent it from going too far

        xOffset += xVelocity;
        yOffset += yVelocity;

        if (yOffset > 4) {
            yVelocity = -1;
            yOffset = 4;
        } else if(yOffset < 0){
            yVelocity = 0;
            yOffset = 0;
        }

        if(xOffset > 4) {
            xVelocity = -1;
            xOffset = 4;
        } else if(xOffset < 0){
            xVelocity = 0;
            xOffset = 0;
        }

        // prevent animation from happening too often
        if(!hardDropAnimationTimer.isDone() && !hardDropAnimationTimer.isDisabled()) {
            yVelocity = 1;
        } else{
            hardDropAnimationTimer.disable();
        }

    }

}
