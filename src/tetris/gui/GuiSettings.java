package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.Button;
//import tetris.gui.widget.Slider;
import tetris.gui.widget.Slider;
import tetris.util.Assets;
import tetris.wrapper.GraphicsWrapper;
import tetris.settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class GuiSettings extends Gui {
    protected GameSettings gameSettings;

    private ImageIcon top_settings;
    private ImageIcon bottomBar;


    public GuiSettings(Gui parentScreen) {
        super(parentScreen);
        load_assets();
        gameSettings = GamePanel.getGamePanel().getSettings();
        ImageIcon exit_button = new ImageIcon(Assets.Button.EXIT_BUTTON);
        buttonList.add(new Button(-170,880, exit_button, (click)->{
            GamePanel.getGamePanel().exitGame();
        }, Button.AnimationType.RIGHT));

        ImageIcon back_button = new ImageIcon(Assets.Button.BACK_BUTTON);
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            GamePanel.getGamePanel().displayGui(parentScreen);
        }, Button.AnimationType.RIGHT));

        ImageIcon slider = new ImageIcon(Assets.Button.SLIDER);
        buttonList.add(new Slider(550,500, slider, 700));

    }

    public void draw(GraphicsWrapper g){
        super.drawBottom(g);
        g.drawString("FART! HAHA", 500, 500);

        //Settings box overlay
        g.setColor(new Color(0x8540a0));
        g.fillRect(300, 200, 1700, 800);
        g.drawImage(top_settings.getImage(), 0, 0, top_settings.getIconWidth(), top_settings.getIconHeight());
        g.setFont(Assets.KDAM_FONT.deriveFont(Font.BOLD, 50));
        g.setColor(Color.WHITE);

        g.drawImage(bottomBar.getImage(), 0, GamePanel.INTERNAL_HEIGHT - bottomBar.getIconHeight(), bottomBar.getIconWidth(), bottomBar.getIconHeight());

        super.drawTop(g);
    }

    private void load_assets() {
        top_settings = new ImageIcon(Assets.TOP_SETTINGS_FILE);
        bottomBar = new ImageIcon(Assets.BOTTOM_SETTINGS_FILE);

    }
}
