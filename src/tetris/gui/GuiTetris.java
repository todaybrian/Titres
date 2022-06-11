package tetris.gui;

import tetris.game.Tetris;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;
import tetris.util.FrameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class GuiTetris extends Gui {
    private static final double BLACK_IN_TIME = 1;
    private FrameTimer blackInTimer;

    Tetris tetris = new Tetris();
    public GuiTetris() {
        super();
        instance.getGameBackground().randomBackground();

        instance.getGameBackground().setOpacity(0.5f);

        ImageIcon back_button = new ImageIcon(Assets.Button.BACK_BUTTON);
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition( this, new GuiSolo()));

            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_BACK);
            instance.getSFXPlayer().playMusic();
            instance.getGameBackground().setOpacity(0.25f);

        }, AnimationType.LEFT));

        blackInTimer = new FrameTimer(BLACK_IN_TIME);
    }

    public void draw(Graphics2D g) {
        super.draw(g);

        g.drawImage(tetris.drawImage(), 1920/2-Tetris.GAME_WIDTH/2, 1080/2-Tetris.GAME_HEIGHT/2, Tetris.GAME_WIDTH, Tetris.GAME_HEIGHT, null);
        g.drawString("Tetris", 100, 100);

        if(!blackInTimer.isDone()) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1-blackInTimer.getProgress())));
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, instance.getWidth(), instance.getHeight());
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    private FrameTimer downTimer = new FrameTimer(0.1);

    @Override
    public void update(){
        super.update();
        if(blackInTimer.isDone()) {
            tetris.update();
        }
        if(downTimer.isDone() && instance.keyboardInput.keyPressed[KeyEvent.VK_DOWN]) {
            downTimer = new FrameTimer(0.1);
            tetris.dropPiece();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            tetris.moveRight();
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            tetris.moveLeft();
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            //tetris.dropPiece();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
