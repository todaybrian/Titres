package tetris.gui;

import tetris.GraphicsWrapper;

public class Tetris extends Gui {
    public Tetris() {
        super(null);
    }

    public void draw(GraphicsWrapper g) {
        g.drawString("Tetris", 100, 100);
    }
}
