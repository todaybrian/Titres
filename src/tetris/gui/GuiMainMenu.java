package tetris.gui;

import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.util.Assets;
import tetris.gui.widget.Button;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GuiMainMenu extends Gui {

    public GuiMainMenu() {
        super();
        topBar = Assets.Gui.TOP_MAIN_MENU.get();
        bottomBar = Assets.Gui.BOTTOM_MAIN_MENU.get();

        Image exit_button = Assets.Button.EXIT_BUTTON.get();
        buttonList.add(new Button(-170,880, exit_button, (click)->{
            instance.exitGame();
        }, AnimationType.LEFT));

        Image solo_button = Assets.Button.SOLO_BUTTON.get();
        buttonList.add(new Button(400, 400, solo_button, (click)->{
            instance.displayGui(new GuiMenuTransition( this, new GuiSolo()));
            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_1);
            instance.getSFXPlayer().playMusic();
        }, AnimationType.RIGHT));

        Image settings_button = Assets.Button.SETTINGS_BUTTON.get();
        buttonList.add(new Button(400, 600, settings_button, (click)->{
            instance.displayGui(new GuiMenuTransition( this, new GuiSettings()));
            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_1);
            instance.getSFXPlayer().playMusic();
        }, AnimationType.RIGHT));

        Image github_button = Assets.Button.GITHUB_BUTTON.get();
        buttonList.add(new Button(1800, 990, github_button, (click)->{
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/todaybrian/ics4u-assignment").toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, AnimationType.DOWN));

        Image logo = Assets.Gui.LOGO.get();
        componentList.add(new AnimatedRectangle((g, x)->{
            g.drawImage(logo, (int)(20+x), 100, (int)(1.25*logo.getWidth(null)), (int)(1.25*logo.getHeight(null)), null);
        }, AnimationType.LEFT));

    }
}
