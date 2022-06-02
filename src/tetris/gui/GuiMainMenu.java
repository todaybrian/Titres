package tetris.gui;

import tetris.GamePanel;
import tetris.GraphicsWrapper;
import tetris.controls.MouseInput;

import java.awt.*;

public class GuiMainMenu extends Gui {
	tetris.gui.widget.Button f;
    public GuiMainMenu(Gui parentScreen) {
        super(parentScreen);
         f = new tetris.gui.widget.Button(1, 600,600,100,100, (click)->{

         });
    }

    public void draw(GraphicsWrapper g){
        f.checkHover();
        super.draw(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.GAME_WIDTH, 50);
        g.fillRect(0, GamePanel.GAME_HEIGHT-50, GamePanel.GAME_WIDTH, 50);
        g.drawString(String.valueOf(MouseInput.getLocation().getX()), 500, 500);
        g.drawString(String.valueOf(MouseInput.getLocation().getY()), 550, 500);
        g.drawString(String.valueOf(((MouseInput.getLocation().getX() > 600 && MouseInput.getLocation().getX() < 700) && (MouseInput.getLocation().getY() > 600 && MouseInput.getLocation().getY() < 700))), 150, 150);
        f.draw(g);
    }
}
