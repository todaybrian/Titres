package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
//import tetris.gui.widget.Slider;
import tetris.gui.widget.Slider;
import tetris.util.Assets;
import tetris.settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class GuiSettings extends Gui {
    protected GameSettings gameSettings;

    private Slider volumeSlider;
    private Slider sfxSlider;
    private Slider fpsSlider;

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
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu(null)));
        }, AnimationType.LEFT));

        ImageIcon slider = new ImageIcon(Assets.Button.SLIDER);
        volumeSlider = new Slider(200,500, 700, slider,  (onChange)->{

        },0, 100, (int)(GamePanel.getGamePanel().getSettings().musicVolume));
        buttonList.add(volumeSlider);

        sfxSlider = new Slider(200,200, 700, slider, (onChange)->{

        },0, 100, (int)(GamePanel.getGamePanel().getSettings().musicVolume));
        buttonList.add(sfxSlider);

        fpsSlider = new Slider(200,800, 700, slider,  (onChange)->{

        },10, 144, (int)(GamePanel.getGamePanel().getSettings().musicVolume));
        buttonList.add(fpsSlider);

        AnimatedRectangle settings = new AnimatedRectangle((g, x)->{
            g.setColor(new Color(0x8540a0));
            g.fillRect(300 + x, 200, 1700, 800);
            g.setFont(Assets.KDAM_FONT.deriveFont(Font.BOLD, 50));
            g.setColor(Color.WHITE);
            g.drawString("Music: " + (int)(volumeSlider.getValue()),1000, 550);
            g.drawString("SFX: " + (int)(sfxSlider.getValue()),1000,250);
            g.drawString("FPS: " + (int)(fpsSlider.getValue()),1000,850);
            updateSettings();
        }, AnimationType.RIGHT);

        componentList.add(settings);
    }

    public void updateSettings() {
//        instance.getSettings().musicVolume = (volumeSlider.getValue());
//        instance.getSettings().updateGameToSettings();
    }
}
