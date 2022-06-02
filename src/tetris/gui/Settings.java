package tetris.gui;

import tetris.GraphicsWrapper;
import tetris.settings.GameSettings;

public class Settings extends Menu{
    protected GameSettings gameSettings;

    public Settings(Menu parentScreen, GameSettings gs) {
        super(parentScreen);
        gameSettings = gs;
    }

    public void draw(GraphicsWrapper g){

    }
}
