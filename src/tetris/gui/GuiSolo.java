/**
 * Author: Brian Yan, Aaron Zhang
 *
 * This is the GUI that is displayed in the Solo main menu.
 */
package tetris.gui;

import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

public class GuiSolo extends Gui {

    //Constructor used to create the GUI for the solo mode
    public GuiSolo() {
        super(); //Call the super constructor
        topBar = Assets.Gui.TOP_SOLO.get(); //Set the top bar
        bottomBar = Assets.Gui.BOTTOM_SOLO.get(); //Set the bottom bar

        //Button to go back to main menu
        buttonList.add(new Button(-170, 120, Assets.Button.BACK_BUTTON.get(), (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu())); //Display Main Menu
            instance.getSFXPlayer().play(Assets.SFX.CLICK_BACK.get()); //Play button click sound

        }, AnimationType.LEFT));

        //Button to 40 lines gamemode
        buttonList.add(new Button(400, 120, Assets.Button.FOURTY_LINES_BUTTON.get(), (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiFourty())); //Display 40 lines game
            instance.getSFXPlayer().play(Assets.SFX.CLICK_2.get()); //Play button click sound

        }, AnimationType.RIGHT));

        //Button to Blitz Gamemode
        buttonList.add(new Button(400, 320, Assets.Button.BLITZ_BUTTON.get(), (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiBlitz())); //Display Blitz game
            instance.getSFXPlayer().play(Assets.SFX.CLICK_2.get()); //Play button click sound

        }, AnimationType.RIGHT));

    }
}
