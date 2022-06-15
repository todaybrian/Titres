package tetris.gui;

import tetris.game.GameMode;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

public class GuiDied extends Gui{
    public GuiDied(GameMode gameMode){
        instance.getMusicPlayer().stopMusic();
        buttonList.add(new tetris.gui.widget.Button( 400, 120,Assets.Button.RETRY_BUTTON.get(), (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiTetris(gameMode), 0.5, true));
        }, AnimationType.RIGHT));

        buttonList.add(new Button(400, 260, Assets.Button.BACK_TO_TITLE_BUTTON.get(), (click)->{
            instance.getMusicPlayer().play(Assets.Music.NIGHT_SNOW.get());
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));
        }, AnimationType.RIGHT));}
}
