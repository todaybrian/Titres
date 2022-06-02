package tetris.gui;

import tetris.GamePanel;
import tetris.GraphicsWrapper;
import tetris.controls.MouseInput;
import tetris.gui.widget.Button;

import java.awt.*;

public class GuiMainMenu extends Gui {
    public GuiMainMenu(Gui parentScreen) {
        super(parentScreen);
        buttonList.add(new Button(600,600,100,100, (click)->{

        }));
        buttonList.add(new Button(900,600,100,100, (click)->{
            GamePanel.getGamePanel().exitGame();
        }));
    }

    public void draw(GraphicsWrapper g){
        super.draw(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.GAME_WIDTH, 50);
        g.fillRect(0, GamePanel.GAME_HEIGHT-50, GamePanel.GAME_WIDTH, 50);
        g.setColor(new Color(343642));
        g.drawRect(0, 0, GamePanel.GAME_WIDTH, 50);
        g.drawRect(0, GamePanel.GAME_HEIGHT-50, GamePanel.GAME_WIDTH, 50);

        g.drawString(String.valueOf(MouseInput.getLocation().getX()), 500, 500);
        g.drawString(String.valueOf(MouseInput.getLocation().getY()), 550, 500);
        g.drawString(String.valueOf(((MouseInput.getLocation().getX() > 600 && MouseInput.getLocation().getX() < 700) && (MouseInput.getLocation().getY() > 600 && MouseInput.getLocation().getY() < 700))), 150, 150);
    }
}
