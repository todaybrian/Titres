package tetris.gui;

import tetris.game.GameMode;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import java.awt.*;

public class GuiBlitz extends Gui{
    public GuiBlitz() {
        super();
        topBar = Assets.Gui.TOP_BLITZ.get();
        bottomBar = Assets.Gui.BOTTOM_BLITZ.get();

        Image back_button = Assets.Button.BACK_BUTTON.get();
        buttonList.add(new Button(-170, 120, back_button, (click)->{
            instance.displayGui(new GuiMenuTransition( this, new GuiSolo()));

            instance.getSFXPlayer().play(Assets.SFX.CLICK_BACK.get());
        }, AnimationType.LEFT));

        componentList.add(new AnimatedRectangle((g, x)->{
            g.setColor(new Color(62, 36, 36));
            g.fillRect((int) (x+300), 160, 1400, 280);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(new Color(219, 161, 161));
            g.drawString("BLITZ", (int) (x+320), 230);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.PLAIN, 25));
            g.drawString("Clear as much lines as possible in 120 seconds!", (int) (x+320), 270);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 30));
            g.drawString("PERSONAL BEST: ", (int) (x+320), 340);

            g.setColor(new Color(62, 36, 36));
            g.fillRect((int) (x+300), 460, 1400, 105);
        }, AnimationType.RIGHT));

        Image start_40_button = Assets.Button.START_BLITZ_BUTTON.get();
        buttonList.add(new Button(1700-start_40_button.getWidth(null), 460, start_40_button, (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiTetris(GameMode.BLITZ), 0.5, true));
            instance.getSFXPlayer().play(Assets.SFX.CLICK_START.get());
        }, AnimationType.NONE));

        Image controls = Assets.Gui.CONTROLS.get();
        componentList.add(new AnimatedRectangle((g, xOffset) -> g.drawImage(controls, 300, 585, controls.getWidth(null),  controls.getHeight(null), null), AnimationType.LEFT));
    }

}
