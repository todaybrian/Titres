package tetris.gui;

import tetris.game.GameMode;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import javax.swing.*;
import java.awt.*;

public class GuiFourty extends Gui{
    public GuiFourty() {
        super();
        topBar = Assets.Gui.TOP_40.get();
        bottomBar = Assets.Gui.BOTTOM_40.get();

        Image back_button = Assets.Button.BACK_BUTTON.get();
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition( this, new GuiSolo()));

            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_BACK.get());
            instance.getSFXPlayer().playMusic();
        }, AnimationType.LEFT));

        componentList.add(new AnimatedRectangle((g, x)->{
            g.setColor(new Color(82, 61, 45));
            g.fillRect((int) (x+300), 160, 1400, 280);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(new Color(233, 181, 142));
            g.drawString("40 LINES", (int) (x+320), 230);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.PLAIN, 25));
            g.drawString("Clear 40 lines in the shortest time possible!", (int) (x+320), 270);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 30));
            g.drawString("PERSONAL BEST: ", (int) (x+320), 340);

            g.setColor(new Color(82, 61, 45));
            g.fillRect((int) (x+300), 460, 1400, 105);
        }, AnimationType.RIGHT));

        Image start_40_button = Assets.Button.START_40_BUTTON.get();
        buttonList.add(new Button(1700-start_40_button.getWidth(null), 460, start_40_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiTetris(GameMode.FOURTY_LINES), 0.5, true));
            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_2.get());
            instance.getSFXPlayer().playMusic();
        }, AnimationType.NONE));
    }

}
