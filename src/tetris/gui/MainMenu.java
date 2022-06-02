package tetris.gui;

import tetris.GraphicsWrapper;

import java.awt.*;

public class MainMenu extends Gui {
    public MainMenu(Gui parentScreen) {
        super(parentScreen);

    }

    public void draw(GraphicsWrapper g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1920, 50);
    }
}
