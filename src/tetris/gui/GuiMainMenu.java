package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.util.Assets;
import tetris.wrapper.GraphicsWrapper;
import tetris.gui.widget.Button;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GuiMainMenu extends Gui {

    public GuiMainMenu(Gui parentScreen) {
        super(parentScreen);
        topBar = new ImageIcon(Assets.TOP_MAIN_MENU_FILE);
        bottomBar = new ImageIcon(Assets.BOTTOM_MAIN_MENU_FILE);

        ImageIcon exit_button = new ImageIcon(Assets.Button.EXIT_BUTTON);
        buttonList.add(new Button(-170,880, exit_button, (click)->{
            instance.exitGame();
        }, AnimationType.LEFT));

        ImageIcon solo_button = new ImageIcon(Assets.Button.SOLO_BUTTON);
        buttonList.add(new Button(400, 400, solo_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiSolo(this)));
            instance.getSFXPlayer().loadMusic(Assets.SFX_CLICK);
            instance.getSFXPlayer().playMusic();
        }, AnimationType.RIGHT));
        ImageIcon settings_button = new ImageIcon(Assets.Button.SETTINGS_BUTTON);
        buttonList.add(new Button(400, 600, settings_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiSettings(this)));
            instance.getSFXPlayer().loadMusic(Assets.SFX_CLICK);
            instance.getSFXPlayer().playMusic();
        }, AnimationType.RIGHT));

        ImageIcon github_button = new ImageIcon(Assets.Button.GITHUB_BUTTON);
        buttonList.add(new Button(1800, 990, github_button, (click)->{
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/todaybrian/ics4u-assignment").toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, AnimationType.DOWN));

        ImageIcon logo = new ImageIcon(Assets.LOGO_FILE);
        componentList.add(new AnimatedRectangle((g, x)->{
            g.drawImage(logo.getImage(), 20+x, 100, 1.25*logo.getIconWidth(), 1.25*logo.getIconHeight());
        }, AnimationType.LEFT));

        AnimatedRectangle debug = new AnimatedRectangle((g, x)->{

            g.setFont(Assets.KDAM_FONT.deriveFont(Font.BOLD, 50));
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(buttonList.get(0).isMouseOver())+ buttonList.get(1).isMouseOver() + buttonList.get(2).isMouseOver(),500, 350);

        }, AnimationType.RIGHT);

        componentList.add(debug);
    }
}
