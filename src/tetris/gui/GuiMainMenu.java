/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * GUI class for the main menu.
 */
package tetris.gui;

import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.util.Assets;
import tetris.gui.widget.Button;

import java.awt.*;
import java.net.URL;

public class GuiMainMenu extends Gui {
    //Constructor for the main menu
    //Sets top bar, bottom bar, adds buttons, and adds components
    public GuiMainMenu() {
        super();
        topBar = Assets.Gui.TOP_MAIN_MENU.get();
        bottomBar = Assets.Gui.BOTTOM_MAIN_MENU.get();
        Image logo = Assets.Gui.LOGO.get();

        // Exit button (bottom left)
        buttonList.add(new Button(-170, 880, Assets.Button.EXIT_BUTTON.get(), (click) -> {

            instance.exitGame(); //Exits the game

        }, AnimationType.LEFT));

        // Solo button (on the right, take most of the screen above setting button)
        buttonList.add(new Button(400, 400, Assets.Button.SOLO_BUTTON.get(), (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiSolo())); //Displays the solo menu
            instance.getSFXPlayer().play(Assets.SFX.CLICK_1.get()); //Plays the click sound

        }, AnimationType.RIGHT));


        // Settings button (on the right, take most of the screen below solo button)
        buttonList.add(new Button(400, 600, Assets.Button.SETTINGS_BUTTON.get(), (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiSettings())); //Displays the settings menu
            instance.getSFXPlayer().play(Assets.SFX.CLICK_1.get()); //Plays the click sound

        }, AnimationType.RIGHT));

        // Github button (opens github page) (button on bottom right on top of bottom bar)
        buttonList.add(new Button(1800, 990, Assets.Button.GITHUB_BUTTON.get(), (click) -> {
            //Opens the github page
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/todaybrian/ics4u-assignment").toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, AnimationType.DOWN));

        // Logo component (Displays logo on top left below the top bar)
        componentList.add(new AnimatedRectangle((g, xOffset) -> {
            //Draws the logo at 1.25x scale
            g.drawImage(Assets.Gui.LOGO.get(), 20 + xOffset, 100, (int) (1.25 * logo.getWidth(null)), (int) (1.25 * logo.getHeight(null)), null);
        }, AnimationType.LEFT));

    }
}
