package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.Button;
import tetris.util.Assets;
import tetris.wrapper.GraphicsWrapper;
import tetris.settings.GameSettings;

import javax.swing.*;

public class GuiSettings extends Gui {
    protected GameSettings gameSettings;

    public GuiSettings(Gui parentScreen) {
        super(parentScreen);
        gameSettings = GamePanel.getGamePanel().getSettings();
        ImageIcon exit_button = new ImageIcon(Assets.Button.EXIT_BUTTON);
        buttonList.add(new Button(-25,880, exit_button, (click)->{
            GamePanel.getGamePanel().displayGui(new GuiMainMenu(this));
        }));


    }

    public void draw(GraphicsWrapper g){
        super.draw(g);
        g.drawString("FART! HAHA", 500, 500);

    }
}
