package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.gui.widget.Slider;
import tetris.util.Assets;
import tetris.settings.GameSettings;

import java.awt.*;

public class GuiSettings extends Gui {
    protected GameSettings gameSettings;

    private Slider musicSlider;
    private Slider sfxSlider;
    private Slider fpsSlider;

    public GuiSettings() {
        super();
        topBar = Assets.Gui.TOP_SETTINGS.get();
        bottomBar = Assets.Gui.BOTTOM_SETTINGS.get();

        gameSettings = GamePanel.getGamePanel().getSettings();

        Image back_button = Assets.Button.BACK_BUTTON.get();
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));

            instance.getSFXPlayer().play(Assets.SFX.CLICK_BACK.get());
        }, AnimationType.LEFT));

        Image slider = Assets.Button.SLIDER.get();
        musicSlider = new Slider(320,500, 670, slider,  (onChange)->{
            instance.getMusicPlayer().changeVolume((Math.round(onChange.getValue())/100.0));
        },0, 100, (instance.getMusicPlayer().getVolume()));
        buttonList.add(musicSlider);

        sfxSlider = new Slider(320,200, 670, slider, (onChange)->{
            instance.getSFXPlayer().changeVolume((Math.round(onChange.getValue())/100.0));
        },0, 100, (instance.getSFXPlayer().getVolume()));

        buttonList.add(sfxSlider);
        fpsSlider = new Slider(320,800, 670, slider,  (onChange)->{
            instance.setRenderFPS((int)Math.round(onChange.getValue()));
        },10, 260, instance.getMaxRenderFPS());
        buttonList.add(fpsSlider);

        AnimatedRectangle settings = new AnimatedRectangle((g, x)->{
            g.setColor(new Color(0x8540a0));
            g.fillRect((int) (300 + x), 200, 1700, 800);
            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(Color.WHITE);

            g.drawString("Music: " + (int)Math.round(musicSlider.getValue()),1050, 570);
            g.drawString("SFX: " + (int)Math.round(sfxSlider.getValue()),1050,270);
            g.drawString("FPS: " + (int)Math.round(fpsSlider.getValue()),1050,870);
            updateSettings();
        }, AnimationType.RIGHT);

        componentList.add(settings);
    }

    public void updateSettings() {
//        instance.getSettings().musicVolume = (volumeSlider.getValue());
//        instance.getSettings().updateGameToSettings();
    }
}
