package tetris.gui;

import tetris.game.Tetris;
import tetris.wrapper.GraphicsWrapper;

public class GuiTetris extends Gui {
    public GuiTetris() {
        super(null);
    }

    public void draw(GraphicsWrapper g) {
        super.draw(g);

        g.drawString("Tetris", 100, 100);
    }
}
