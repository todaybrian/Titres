package tetris.gui;

import tetris.GraphicsWrapper;
import tetris.settings.GameSettings;

public class Settings extends Gui {
    protected GameSettings gameSettings;

    public Settings(Gui parentScreen, GameSettings gs) {
        super(parentScreen);
        gameSettings = gs;
    }

    public void draw(GraphicsWrapper g){
        super.draw(g);

    }
}
