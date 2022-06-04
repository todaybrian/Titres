package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.Button;
import tetris.util.Assets;
import tetris.wrapper.GraphicsWrapper;
import tetris.settings.GameSettings;

import javax.swing.*;

public class GuiSettings extends Gui {
    protected GameSettings gameSettings;

    private ImageIcon top_settings;

    public GuiSettings(Gui parentScreen) {
        super(parentScreen);
        load_assets();
        gameSettings = GamePanel.getGamePanel().getSettings();
        ImageIcon exit_button = new ImageIcon(Assets.Button.EXIT_BUTTON);
        buttonList.add(new Button(-170,880, exit_button, (click)->{
            GamePanel.getGamePanel().displayGui(new GuiMainMenu(this));
        }, Button.AnimationType.RIGHT));


    }

    public void draw(GraphicsWrapper g){
        super.draw(g);
        g.drawString("FART! HAHA", 500, 500);
        g.drawImage(top_settings.getImage(), 0, 0, top_settings.getIconWidth(), top_settings.getIconHeight());

    }

    private void load_assets() {
        top_settings = new ImageIcon(Assets.TOP_SETTINGS_FILE);
    }
}
