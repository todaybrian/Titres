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

    private Slider musicSlider;
    private Slider sfxSlider;
    private Slider fpsSlider;

    public GuiSettings() {
        super();
        topBar = new ImageIcon(Assets.TOP_SETTINGS_FILE);
        bottomBar = new ImageIcon(Assets.BOTTOM_SETTINGS_FILE);

        gameSettings = GamePanel.getGamePanel().getSettings();
        ImageIcon exit_button = new ImageIcon(Assets.Button.EXIT_BUTTON);
//        buttonList.add(new Button(-170,880, exit_button, (click)->{
//            GamePanel.getGamePanel().exitGame();
//        }, AnimationType.LEFT));

        ImageIcon back_button = new ImageIcon(Assets.Button.BACK_BUTTON);
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));

            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_BACK);
            instance.getSFXPlayer().playMusic();
        }, AnimationType.LEFT));

        ImageIcon slider = new ImageIcon(Assets.Button.SLIDER);
        musicSlider = new Slider(200,500, 700, slider,  (onChange)->{
            instance.getMusicPlayer().changeVolume((onChange.getValue()/100.0));
        },0, 100, (instance.getMusicPlayer().getVolume()));
        buttonList.add(musicSlider);

        sfxSlider = new Slider(200,200, 700, slider, (onChange)->{
            instance.getSFXPlayer().changeVolume((onChange.getValue()/100.0));
        },0, 100, (instance.getSFXPlayer().getVolume()));

        buttonList.add(sfxSlider);
        fpsSlider = new Slider(320,800, 670, slider,  (onChange)->{
            instance.setRenderFPS((int)onChange.getValue());
        },10, 260, instance.getMaxRenderFPS());
        buttonList.add(fpsSlider);

        AnimatedRectangle settings = new AnimatedRectangle((g, x)->{
            g.setColor(new Color(0x8540a0));
            g.fillRect((int) (300 + x), 200, 1700, 800);
            g.setFont(Assets.KDAM_FONT.deriveFont(Font.BOLD, 50));
            g.setColor(Color.WHITE);
            g.drawString("Music: " + (int)(musicSlider.getValue()),1000, 550);
            g.drawString("SFX: " + (int)(sfxSlider.getValue()),1000,250);
            g.drawString("FPS: " + (int)(Math.round(fpsSlider.getValue())),1000,850);
            updateSettings();
        }, AnimationType.RIGHT);

        componentList.add(settings);
    }

    public void updateSettings() {
//        instance.getSettings().musicVolume = (volumeSlider.getValue());
//        instance.getSettings().updateGameToSettings();
    }
}
