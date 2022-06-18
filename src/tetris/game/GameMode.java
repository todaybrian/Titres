/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * This class stores all the game modes.
 * Also used to handle game specific assets.
 */
package tetris.game;

import tetris.util.Assets;

import java.awt.*;

public enum GameMode {
    FOURTY_LINES, BLITZ; //The game modes

    //Returns the banner that is displayed when the game starts in the given game mode
    public Image getBanner(){
        switch (this){
            case FOURTY_LINES:
                return Assets.Game.FORTY_BANNER.get();
            case BLITZ:
                return Assets.Game.BLITZ_BANNER.get();
            default:
                return null;
        }
    }
}
