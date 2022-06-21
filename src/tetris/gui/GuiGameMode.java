/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Gui for the all the game modes.
 */
package tetris.gui;

import tetris.game.GameMode;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import java.awt.*;

public class GuiGameMode extends Gui {
    // Constructor to create buttons/components for the 40 line/blitz game mode entry screen
    public GuiGameMode(GameMode gameMode) {
        super(); //Call the super constructor

        //Init button images
        Image startButton = null;
        Image back_button = Assets.Button.BACK_BUTTON.get();
        Image controls = Assets.Gui.CONTROLS.get();

        //Different assets for different game modes
        switch (gameMode) {
            case FORTY_LINES:
                topBar = Assets.Gui.TOP_40.get(); //Set the top bar
                bottomBar = Assets.Gui.BOTTOM_40.get(); //Set the bottom bar
                startButton = Assets.Button.START_40_BUTTON.get(); //Set the start button
                break;
            case BLITZ:
                topBar = Assets.Gui.TOP_BLITZ.get(); //Set the top bar
                bottomBar = Assets.Gui.BOTTOM_BLITZ.get(); //Set the bottom bar
                startButton = Assets.Button.START_BLITZ_BUTTON.get(); //Set the start button
                break;
        }


        // Back button to go back to the solo menu (top left)
        buttonList.add(new Button(-170, 120, back_button, (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiSolo())); //Display the main menu
            instance.getSFXPlayer().play(Assets.SFX.CLICK_BACK.get()); //Play the click back sound

        }, AnimationType.LEFT));

        // Component to display the game mode
        componentList.add(new AnimatedRectangle((g, offsetX) -> {
            //offsetX is the horizontal offset variable which is used to create a transition effect for animation
            //It must be added to the x coordinate of relevant components

            //Draw the top rectangle
            g.setColor(gameMode.getBackgroundColor());
            g.fillRect(offsetX + 300, 160, 1400, 280);

            //Draw the Game Mode name
            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(gameMode.getTextColor());
            g.drawString(gameMode.getName(), offsetX + 320, 230);

            //Draw the game mode description
            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.PLAIN, 25));
            g.drawString(gameMode.getDescription(), offsetX + 320, 270);

            //Draw a second rectangle below the first rectangle to store the start button
            g.setColor(gameMode.getBackgroundColor());
            g.fillRect(offsetX + 300, 460, 1400, 105);
        }, AnimationType.RIGHT));

        // Start button to start the game
        buttonList.add(new Button(1700 - startButton.getWidth(null), 460, startButton, (click) -> {
            //Transitions into the game
            //Animates with half a second length and blacks in
            instance.displayGui(new GuiMenuTransition(this, new GuiTetris(GameMode.FORTY_LINES), 0.5, true));
            instance.getSFXPlayer().play(Assets.SFX.CLICK_START.get()); //Play click start sound

        }, AnimationType.NONE));

        // Controls list
        componentList.add(new AnimatedRectangle((g, xOffset) -> {
            //offsetX is the horizontal offset variable which is used to create a transition effect for animation
            //It must be added to the x coordinate of relevant components

            //Draw the controls list
            g.drawImage(controls, 300 + xOffset, 585, controls.getWidth(null),  controls.getHeight(null), null);

        }, AnimationType.RIGHT));

    }
}
