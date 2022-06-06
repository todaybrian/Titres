package tetris.gui;

import tetris.game.Tetris;
import tetris.wrapper.GraphicsWrapper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GuiTetris extends Gui {
    Tetris tetris = new Tetris();
    public GuiTetris() {
        super(null);
    }

    public void draw(GraphicsWrapper g) {
        super.draw(g);
        g.drawImage(tetris.drawImage(), 1920/2-Tetris.GAME_WIDTH/2, 1080/2-Tetris.GAME_HEIGHT/2, (double)Tetris.GAME_WIDTH, (double)Tetris.GAME_HEIGHT);
        g.drawString("Tetris", 100, 100);
    }
}
