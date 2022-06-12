package tetris.gui;

import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import javax.swing.*;

public class GuiSolo extends Gui{

    public GuiSolo() {
        super();
        topBar = new ImageIcon(Assets.Menu.TOP_SOLO);
        bottomBar = new ImageIcon(Assets.Menu.BOTTOM_SOLO);

        ImageIcon back_button = new ImageIcon(Assets.Button.BACK_BUTTON);
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));

            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_BACK);
            instance.getSFXPlayer().playMusic();
        }, AnimationType.LEFT));

        ImageIcon fourty_lines_button = new ImageIcon(Assets.Button.FOURTY_LINES_BUTTON);
        buttonList.add(new Button(400, 120, fourty_lines_button, (click)->{
        instance.displayGui(new GuiMenuTransition(this, new GuiFourty()));
            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_2);
            instance.getSFXPlayer().playMusic();

        }, AnimationType.RIGHT));


    }
}
