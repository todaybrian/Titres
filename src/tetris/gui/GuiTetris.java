package tetris.gui;

import tetris.game.GameMode;
import tetris.game.Tetris;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;
import tetris.util.FrameTimer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GuiTetris extends Gui {
    private static final double BLACK_IN_TIME = 1;
    private FrameTimer blackInTimer;

    Tetris tetris;

    private int xOffset = 0;
    private int yOffset = 0;
    private int xVelocity = 0;
    private int yVelocity = 0;

    private FrameTimer diedTimer;
    private GameMode gameMode;

    public GuiTetris(GameMode gameMode) {
        super();
        this.gameMode = gameMode;

        tetris = new Tetris(gameMode);

        instance.getGameBackground().randomBackground();

        this.backgroundOpacity = 0.5f;

        Image back_button = Assets.Button.BACK_BUTTON.get();
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition( this, new GuiSolo()));

            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_BACK.get());
            instance.getSFXPlayer().playMusic();
            instance.getGameBackground().setOpacity(0.25f);

        }, AnimationType.LEFT));

        blackInTimer = new FrameTimer(BLACK_IN_TIME);
        diedTimer = new FrameTimer(1);
        diedTimer.disable();
    }


    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if(tetris.isDied()){
            if(diedTimer.isDisabled()){
                diedTimer.reset();
            }

            BufferedImage board = (BufferedImage) tetris.drawImage();

            g.rotate(Math.toRadians(20)
                    *diedTimer.getProgress());

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
