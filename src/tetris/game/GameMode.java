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
    FORTY_LINES, BLITZ; //The game modes

    //Returns name of the game mode
    public String getName(){
        switch(this){
            case FORTY_LINES:
                return "40 LINES";
            case BLITZ:
                return "BLITZ";
            default:
                return "";
        }
    }

    //Returns the description of the game mode
    public String getDescription(){
        switch(this){
            case FORTY_LINES:
                return "Clear 40 lines in the shortest time possible!";
            case BLITZ:
                return "Clear as many lines as possible in 120 seconds!";
            default:
                return "";
        }
    }

    //Returns the banner that is displayed when the game starts in the given game mode
    public Image getBanner(){
        switch (this){
            case FORTY_LINES:
                return Assets.Game.FORTY_BANNER.get();
            case BLITZ:
                return Assets.Game.BLITZ_BANNER.get();
            default:
                return null;
        }
    }

    //Returns the text color
    public Color getTextColor(){
        switch (this){
            case FORTY_LINES:
                return new Color(233, 181, 142);
            case BLITZ:
                return new Color(219, 161, 161);
            default:
                return null;
        }
    }

    //Returns the background color
    public Color getBackgroundColor(){
        switch (this){
            case FORTY_LINES:
                return new Color(82, 61, 45);
            case BLITZ:
                return new Color(62, 36, 36);
            default:
                return null;
        }
    }
}
