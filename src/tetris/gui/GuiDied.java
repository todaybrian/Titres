/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * This is the GUI that is displayed when the player has died.
 */
package tetris.gui;

import tetris.game.GameMode;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

public class GuiDied extends Gui{

    public GuiDied(GameMode gameMode){ //Argument holds game mode player was in so that they can retry
        instance.getMusicPlayer().stopMusic(); //Stop the music

        //Retry button
        buttonList.add(new tetris.gui.widget.Button(400, 120, Assets.Button.RETRY_BUTTON.get(), (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiTetris(gameMode), 0.5, true)); //Redisplay the game
        }, AnimationType.RIGHT));

        //Back to main menu button
        buttonList.add(new Button(400, 260, Assets.Button.BACK_TO_TITLE_BUTTON.get(), (click)->{
            instance.getMusicPlayer().play(Assets.Music.NIGHT_SNOW.get()); //Replay Main Menu music
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu())); //Display Main Menu
        }, AnimationType.RIGHT));
    }
}
