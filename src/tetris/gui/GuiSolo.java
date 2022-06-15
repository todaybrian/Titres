package tetris.gui;

import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import java.awt.*;

public class GuiSolo extends Gui{

    public GuiSolo() {
        super();
        topBar = Assets.Gui.TOP_SOLO.get();
        bottomBar = Assets.Gui.BOTTOM_SOLO.get();

        Image back_button = Assets.Button.BACK_BUTTON.get();
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));

            instance.getSFXPlayer().play(Assets.SFX.CLICK_BACK.get());
        }, AnimationType.LEFT));

        Image fourty_lines_button = Assets.Button.FOURTY_LINES_BUTTON.get();
        buttonList.add(new Button(400, 120, fourty_lines_button, (click)->{
        instance.displayGui(new GuiMenuTransition(this, new GuiFourty()));
            instance.getSFXPlayer().play(Assets.SFX.CLICK_2.get());

        }, AnimationType.RIGHT));

        Image blitz_button = Assets.Button.BLITZ_BUTTON.get();
        buttonList.add(new Button(400, 320, blitz_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiBlitz()));
            instance.getSFXPlayer().play(Assets.SFX.CLICK_2.get());

        }, AnimationType.RIGHT));



    }
}
