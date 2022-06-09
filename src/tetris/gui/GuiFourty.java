package tetris.gui;

import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import javax.swing.*;
import java.awt.*;

public class GuiFourty extends Gui{

    public GuiFourty(Gui parentScreen) {
        super(parentScreen);

        topBar = new ImageIcon(Assets.TOP_40_FILE);
        bottomBar = new ImageIcon(Assets.BOTTOM_40_FILE);

        ImageIcon back_button = new ImageIcon(Assets.Button.BACK_BUTTON);
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiSolo(null)));

            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_BACK);
            instance.getSFXPlayer().playMusic();
        }, AnimationType.LEFT));

        componentList.add(new AnimatedRectangle((g, x)->{
            g.setColor(new Color(82, 61, 45));
            g.fillRect((int) (x+300), 160, 1400, 280);

            g.setFont(Assets.KDAM_FONT.deriveFont(Font.BOLD, 50));
            g.setColor(new Color(233, 181, 142));
            g.drawString("40 LINES", (int) (x+320), 230);

            g.setFont(Assets.KDAM_FONT.deriveFont(Font.PLAIN, 25));
            g.drawString("Clear 40 lines in the shortest time possible", (int) (x+320), 270);

            g.setFont(Assets.KDAM_FONT.deriveFont(Font.BOLD, 30));
            g.drawString("PERSONAL BEST: ", (int) (x+320), 340);

            g.setColor(new Color(82, 61, 45));
            g.fillRect((int) (x+300), 460, 1400, 105);
        }, AnimationType.RIGHT));

        ImageIcon start_40_button = new ImageIcon(Assets.Button.START_40_BUTTON);
        buttonList.add(new Button(1700-start_40_button.getIconWidth(), 460, start_40_button, (click)->{
            instance.displayGui(new GuiTetris());
            instance.getSFXPlayer().loadMusic(Assets.SFX.CLICK_2);
            instance.getSFXPlayer().playMusic();
        }, AnimationType.NONE));
    }

}
