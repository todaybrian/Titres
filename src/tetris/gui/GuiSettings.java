package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
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

    public GuiSettings(Gui parentScreen) {
        super(parentScreen);
        topBar = new ImageIcon(Assets.TOP_SETTINGS_FILE);
        bottomBar = new ImageIcon(Assets.BOTTOM_SETTINGS_FILE);

        gameSettings = GamePanel.getGamePanel().getSettings();
        ImageIcon exit_button = new ImageIcon(Assets.Button.EXIT_BUTTON);
//        buttonList.add(new Button(-170,880, exit_button, (click)->{
//            GamePanel.getGamePanel().exitGame();
//        }, AnimationType.LEFT));

        ImageIcon back_button = new ImageIcon(Assets.Button.BACK_BUTTON);
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            GamePanel.getGamePanel().displayGui(new GuiMenuTransition(this, new GuiMainMenu(null)));
        }, AnimationType.LEFT));

        ImageIcon slider = new ImageIcon(Assets.Button.SLIDER);
        buttonList.add(new Slider(785,500, slider, 200, 700));

        AnimatedRectangle settings = new AnimatedRectangle((g, x)->{
            g.setColor(new Color(0x8540a0));
            g.fillRect(300 + x, 200, 1700, 800);
            g.setFont(Assets.KDAM_FONT.deriveFont(Font.BOLD, 50));
            g.setColor(Color.WHITE);
            g.drawString("Volume: " + (int)(buttonList.get(1).getValue()*100),500, 750);

            updateSettings();
        }, AnimationType.RIGHT);

        componentList.add(settings);
    }

    public void draw(GraphicsWrapper g){
        super.draw(g);
        g.drawString("FART! HAHA", 500, 500);

        //Settings box overlay
    }

    public void loadAssets() {
    }

    public void updateSettings() {
        GamePanel.getGamePanel().getSettings().volume = (buttonList.get(1).getValue());
        GamePanel.getGamePanel().getSettings().updateGameToSettings();
    }
}
