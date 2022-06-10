package tetris.gui;

import tetris.game.Tetris;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import javax.swing.*;
import java.awt.*;

public class GuiTetris extends Gui {
    private static final double BLACK_IN_TIME = 1;
    private long startTime;

    Tetris tetris = new Tetris();
    public GuiTetris() {
        super(null);
        startTime = System.nanoTime();

        instance.getGameBackground().randomBackground();

        instance.getGameBackground().setOpacity(0.5f);

        ImageIcon back_button = new ImageIcon(Assets.Button.BACK_BUTTON);
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiSolo(null)));

            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_BACK);
            instance.getSFXPlayer().playMusic();
            instance.getGameBackground().setOpacity(0.25f);

            instance.tetrises.clear();
        }, AnimationType.LEFT));

        instance.tetrises.add(tetris);
    }

    public void draw(Graphics2D g) {
        super.draw(g);




        g.drawImage(tetris.drawImage(), 1920/2-Tetris.GAME_WIDTH/2, 1080/2-Tetris.GAME_HEIGHT/2, Tetris.GAME_WIDTH, Tetris.GAME_HEIGHT, null);
        g.drawString("Tetris", 100, 100);

        if(System.nanoTime() - startTime < BLACK_IN_TIME * 1000000000) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-(float) ((System.nanoTime() - startTime) / (BLACK_IN_TIME * 1000000000f))));
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, instance.getWidth(), instance.getHeight());
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}
