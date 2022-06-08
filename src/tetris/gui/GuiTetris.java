package tetris.gui;

import tetris.game.Tetris;
import tetris.game.Tetromino;
import tetris.game.randomizer.Randomizer;
import tetris.game.randomizer.RandomizerSevenBag;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GuiTetris extends Gui {
    Tetris tetris = new Tetris();
    Tetromino test, test2, test3;
    Randomizer randomizer = new RandomizerSevenBag();
    public GuiTetris() {
        super(null);
        test = new Tetromino(randomizer.getNext());
        test2 = new Tetromino(randomizer.getNext());
        test3 = new Tetromino(randomizer.getNext());
        ImageIcon back_button = new ImageIcon(Assets.Button.BACK_BUTTON);
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiSolo(null)));

            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_BACK);
            instance.getSFXPlayer().playMusic();
        }, AnimationType.LEFT));
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        g.drawImage(tetris.drawImage(), 1920/2-Tetris.GAME_WIDTH/2, 1080/2-Tetris.GAME_HEIGHT/2, Tetris.GAME_WIDTH, Tetris.GAME_HEIGHT, null);
        g.drawImage(test.drawImage(), 1920/2-Tetris.GAME_WIDTH/2, 1080/2-Tetris.GAME_HEIGHT/2, Tetris.GAME_WIDTH, Tetris.GAME_HEIGHT, null);
        g.drawString("Tetris", 100, 100);
    }
}
