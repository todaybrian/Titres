/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Gui for the blitz game mode.
 */

package tetris.gui;

import tetris.game.GameMode;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import java.awt.*;

public class GuiBlitz extends Gui{
    public GuiBlitz() {
        super(); // Calls Gui class constructor
        // Get top and bottom menu bars
        topBar = Assets.Gui.TOP_BLITZ.get();
        bottomBar = Assets.Gui.BOTTOM_BLITZ.get();

        // The button to return to game mode select menu
        Image back_button = Assets.Button.BACK_BUTTON.get();
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition( this, new GuiSolo()));

            instance.getSFXPlayer().play(Assets.SFX.CLICK_BACK.get());
        }, AnimationType.LEFT));

        // Instructions for game mode
        componentList.add(new AnimatedRectangle((g, offsetX)->{
            g.setColor(new Color(62, 36, 36));
            g.fillRect(offsetX+300, 160, 1400, 280);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(new Color(219, 161, 161));
            g.drawString("BLITZ", offsetX+320, 230);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.PLAIN, 25));
            g.drawString("Clear as many lines as possible in 120 seconds!", offsetX+320, 270);

            g.setColor(new Color(62, 36, 36));
            g.fillRect(offsetX+300, 460, 1400, 105);
        }, AnimationType.RIGHT));

        // Button to start game
        Image start_40_button = Assets.Button.START_BLITZ_BUTTON.get();
        buttonList.add(new Button(1700-start_40_button.getWidth(null), 460, start_40_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiTetris(GameMode.BLITZ), 0.5, true));
            instance.getSFXPlayer().play(Assets.SFX.CLICK_START.get());
        }, AnimationType.NONE));

        // Controls list
        Image controls = Assets.Gui.CONTROLS.get();
        componentList.add(new AnimatedRectangle((g, xOffset) -> {
            g.drawImage(controls, 300 + xOffset, 585, controls.getWidth(null),  controls.getHeight(null), null);
        }, AnimationType.RIGHT));
    }

}
