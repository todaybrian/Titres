package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import javax.swing.*;

public class GuiSolo extends Gui{

    public GuiSolo(Gui parentScreen) {
        super(parentScreen);

        topBar = new ImageIcon(Assets.TOP_SOLO_FILE);
        bottomBar = new ImageIcon(Assets.BOTTOM_SOLO_FILE);

        ImageIcon back_button = new ImageIcon(Assets.Button.BACK_BUTTON);
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu(null)));
            instance.getSFXPlayer().loadMusic(Assets.SFX_CLICK);
            instance.getSFXPlayer().playMusic();

        }, AnimationType.LEFT));

        ImageIcon fourty_lines_button = new ImageIcon(Assets.Button.FOURTY_LINES_BUTTON);
        buttonList.add(new Button(400, 120, fourty_lines_button, (click)->{
        instance.displayGui(new GuiMenuTransition(this,new GuiTetris()));

        }, AnimationType.RIGHT));
    }
}
