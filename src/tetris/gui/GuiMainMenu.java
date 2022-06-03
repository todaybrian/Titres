package tetris.gui;

import tetris.GamePanel;
import tetris.util.Assets;
import tetris.wrapper.GraphicsWrapper;
import tetris.controls.MouseInput;
import tetris.gui.widget.Button;

import javax.swing.*;
import java.awt.*;

public class GuiMainMenu extends Gui {

    private ImageIcon topBar;

    public GuiMainMenu(Gui parentScreen) {
        super(parentScreen);

        load_assets();

        ImageIcon exit_button = new ImageIcon(Assets.Button.EXIT_BUTTON);
        buttonList.add(new Button(-25,900, exit_button, (click)->{
            GamePanel.getGamePanel().exitGame();
        }));
    }

    private void load_assets(){
        topBar = new ImageIcon(Assets.TOP_MAIN_MENU_FILE);
    }

    public void draw(GraphicsWrapper g){
        super.draw(g);

        int heightOfBar = 70;

        g.setColor(Color.BLACK);
        g.drawImage(topBar.getImage(), 0, 0, topBar.getIconWidth(), topBar.getIconHeight());
        g.fillRect(0, GamePanel.INTERNAL_HEIGHT -heightOfBar, GamePanel.INTERNAL_WIDTH, heightOfBar);

        //Draw logo


        g.drawString(String.valueOf(MouseInput.getLocation().getX()), 500, 500);
        g.drawString(String.valueOf(MouseInput.getLocation().getY()), 550, 500);
        g.drawString(String.valueOf(((MouseInput.getLocation().getX() > 600 && MouseInput.getLocation().getX() < 700) && (MouseInput.getLocation().getY() > 600 && MouseInput.getLocation().getY() < 700))), 150, 150);
    }
}
