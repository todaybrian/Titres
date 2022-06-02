package tetris.gui;

import tetris.GraphicsWrapper;

import java.awt.*;

public class GuiMainMenu extends Gui {
    public GuiMainMenu(Gui parentScreen) {
        super(parentScreen);

    }

    public void draw(GraphicsWrapper g){
        super.draw(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1920, 50);
    }
}
