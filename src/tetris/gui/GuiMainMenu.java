package tetris.gui;

import tetris.GamePanel;
import tetris.GraphicsWrapper;
import tetris.controls.MouseInput;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GuiMainMenu extends Gui {
    public GuiMainMenu(Gui parentScreen) {
        super(parentScreen);

    }

    public void draw(GraphicsWrapper g){
        super.draw(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.GAME_WIDTH, 50);
        g.fillRect(0, GamePanel.GAME_HEIGHT-50, GamePanel.GAME_WIDTH, 50);

        g.drawString(String.valueOf(MouseInput.getLocation().getX()), 500, 500);
    }
}
