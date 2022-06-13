package tetris.game;

import tetris.util.Assets;

import java.awt.*;

public enum GameMode {
    FOURTY_LINES, BLITZ;

    public Image getBanner(){
        switch (this){
            case FOURTY_LINES:
                return Assets.Game.FOURTY_BANNER.get();
            case BLITZ:
                return Assets.Game.BLITZ_BANNER.get();
            default:
                return null;
        }
    }
}
