package tetris.gui;

import tetris.GamePanel;
import tetris.wrapper.GraphicsWrapper;
import tetris.settings.GameSettings;

public class GuiSettings extends Gui {
    protected GameSettings gameSettings;

    public GuiSettings(Gui parentScreen) {
        super(parentScreen);
        gameSettings = GamePanel.getGamePanel().getSettings();
    }

    public void draw(GraphicsWrapper g){
        super.draw(g);

    }
}
