/**
 * Author: Brian Yan, Aaron Zhang
 *
 * This is the GUI that is displayed in the Solo main menu.
 */
package tetris.gui;

import tetris.game.GameMode;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

public class GuiSolo extends Gui {

    //Constructor used to create the GUI for the solo mode, initializes the buttons/components
    public GuiSolo() {
        super(); //Call the super constructor
        topBar = Assets.Gui.TOP_SOLO.get(); //Set the top bar
        bottomBar = Assets.Gui.BOTTOM_SOLO.get(); //Set the bottom bar

        //Button to go back to main menu
        buttonList.add(new Button(-170, 120, Assets.Button.BACK_BUTTON.get(), (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu())); //Display Main Menu
            sfxPlayer.play(Assets.SFX.CLICK_BACK.get()); //Play button click back sound

        }, AnimationType.LEFT));

        //Button to 40 lines game mode (right, takes most of the screen, above blitz game mode button)
        buttonList.add(new Button(400, 120, Assets.Button.FOURTY_LINES_BUTTON.get(), (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiGameMode(GameMode.FORTY_LINES))); //Display 40 lines game
            sfxPlayer.play(Assets.SFX.CLICK_2.get()); //Play button click level 2 sound

        }, AnimationType.RIGHT));

        //Button to Blitz game mode (right, takes most of the screen, below 40 line game mode button)
        buttonList.add(new Button(400, 320, Assets.Button.BLITZ_BUTTON.get(), (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiGameMode(GameMode.BLITZ))); //Display Blitz game
            sfxPlayer.play(Assets.SFX.CLICK_2.get()); //Play button click level 2sound

        }, AnimationType.RIGHT));

    }
}
