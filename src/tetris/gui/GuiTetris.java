package tetris.gui;

import tetris.game.GameMode;
import tetris.game.Tetris;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;
import tetris.util.FrameTimer;
import tetris.util.Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GuiTetris extends Gui {
    private static final double BLACK_IN_TIME = 1;
    private FrameTimer blackInTimer;

    private FrameTimer titleTimer;
    private FrameTimer countdownTimer;
    private FrameTimer goTimer;

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

    public GuiTetris(GameMode gameMode) {
        super();
        this.gameMode = gameMode;

        tetris = new Tetris(gameMode);

        instance.getGameBackground().randomBackground();

        this.backgroundOpacity = 0.5f;

        Image back_button = Assets.Button.BACK_BUTTON.get();
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition( this, new GuiSolo()));

            instance.getSFXPlayer().play(Assets.SFX.CLICK_BACK.get());
            instance.getGameBackground().setOpacity(0.25f);

        }, AnimationType.LEFT));

        blackInTimer = new FrameTimer(BLACK_IN_TIME);

        titleTimer = new FrameTimer(5);
        titleTimer.disable();

        diedTimer = new FrameTimer(1);
        diedTimer.disable();

        countdownTimer = new FrameTimer(3.3);
        countdownTimer.disable();

        goTimer = new FrameTimer(1);
        goTimer.disable();
    }


    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if(tetris.isDied()){
            if(diedTimer.isDisabled()){
                diedTimer.reset();
            }

            BufferedImage board = (BufferedImage) tetris.drawImage();

            g.rotate(Math.toRadians(20) *diedTimer.getProgress());

            g.drawImage(board, 1920 / 2 - Tetris.GAME_WIDTH / 2  + (int)(330 * diedTimer.getProgress()), 1080 / 2 - Tetris.GAME_HEIGHT / 2 + (int)(600 * diedTimer.getProgress()),null);
            if(diedTimer.isDone()){
                instance.displayGui(new GuiMenuTransition(this, new GuiDied(gameMode)));
            }
        } else {

            g.drawImage(tetris.drawImage(), 1920 / 2 - Tetris.GAME_WIDTH / 2 + xOffset, 1080 / 2 - Tetris.GAME_HEIGHT / 2 + yOffset - (int) (1400 * (1 - blackInTimer.getProgress())), Tetris.GAME_WIDTH, Tetris.GAME_HEIGHT, null);

            if (!blackInTimer.isDone()) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (1 - blackInTimer.getProgress())));
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, instance.getWidth(), instance.getHeight());
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            } else if(!titleTimer.isDone()){
                float opacityProgress = 0;
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

    @Override
    public void update(){
        super.update();

        if(!blackInTimer.isDone()) {
            return;
        } else if(titleTimer.isDisabled()){
            titleTimer.reset();
            instance.getMusicPlayer().stopMusic();
            instance.getSFXPlayer().play(Assets.SFX.START_SOLO_GAME.get());
        }

        if(!titleTimer.isDone()){
            return;
        } else if(countdownTimer.isDisabled()){
            countdownTimer.reset();
        }

        if(!countdownTimer.isDone()){
            return;
        } else if(goTimer.isDisabled()){
            goTimer.reset();
            instance.getSFXPlayer().play(Assets.SFX.GO.get());
            if (tetris.getGameMode() != GameMode.BLITZ) { // Change the BGM to fit the game mode. "VIRTUAL_LIGHT" is more intense
                instance.getMusicPlayer().play(Assets.Music.VREMYA.get());
            }  else {
                instance.getMusicPlayer().play(Assets.Music.VIRTUAL_LIGHT.get());
            }
        }

        tetris.update();

        if (downTimer.isDone() && instance.keyboardInput.keyPressed[KeyEvent.VK_DOWN]) {
            downTimer.reset();
            tetris.dropPiece();
        }
        if (instance.keyboardInput.keyPressed[KeyEvent.VK_SPACE] && !held_hardDrop) {
            tetris.hardDrop();
            hardDropAnimationTimer.reset();
        }
        held_hardDrop = instance.keyboardInput.keyPressed[KeyEvent.VK_SPACE];

        if (instance.keyboardInput.keyPressed[KeyEvent.VK_LEFT]) {
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

        if (instance.keyboardInput.keyPressed[KeyEvent.VK_RIGHT]) {
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

        if (instance.keyboardInput.keyPressed[KeyEvent.VK_UP] && !held_rotateCW) {
            tetris.rotateCW();
        } else if (instance.keyboardInput.keyPressed[KeyEvent.VK_CONTROL] && !held_rotateCCW) {
            tetris.rotateCCW();
        }
        held_rotateCW = instance.keyboardInput.keyPressed[KeyEvent.VK_UP];
        held_rotateCCW = instance.keyboardInput.keyPressed[KeyEvent.VK_CONTROL];

        if (instance.keyboardInput.keyPressed[KeyEvent.VK_C] && !held_holdPiece) {
            tetris.holdPiece();
        }
        held_holdPiece = instance.keyboardInput.keyPressed[KeyEvent.VK_C];

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

        if(!hardDropAnimationTimer.isDone() && !hardDropAnimationTimer.isDisabled()) {
            yVelocity = 1;
        } else{
            hardDropAnimationTimer.disable();
        }

    }

}
