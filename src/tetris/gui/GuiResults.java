package tetris.gui;

import tetris.GamePanel;
import tetris.game.GameMode;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import java.awt.*;

public class GuiResults extends Gui {
    private GameMode gameMode;

    public GuiResults(GameMode gameMode, long finalScore) {
        super();
        this.gameMode = gameMode;
        AnimatedRectangle results = new AnimatedRectangle((g, x)->{
            g.setColor(new Color(0x8540a0));
            g.fillRect((int) (300 + x), 200, 1700, 800);
            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(Color.WHITE);
            if (gameMode == GameMode.BLITZ) {
                g.drawString("Lines Cleared: " + finalScore, 1000, 550);
            } else if (gameMode == GameMode.FOURTY_LINES) {
                int min = (int) (finalScore / 1000 / 60);
                int sec = (int) (finalScore/1000 % 60);
                int ms = (int) (finalScore%1000);
                g.drawString("Time: "+min+":"+sec+":"+ms, 1000, 550);
            }
        }, AnimationType.RIGHT);
        componentList.add(results);
        instance.getMusicPlayer().stopMusic();
        buttonList.add(new tetris.gui.widget.Button( 400, 120,Assets.Button.RETRY_BUTTON.get(), (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiTetris(gameMode), 0.5, true));
            }, AnimationType.RIGHT));

        buttonList.add(new Button(400, 260, Assets.Button.BACK_TO_TITLE_BUTTON.get(), (click)->{
            instance.getMusicPlayer().play(Assets.Music.NIGHT_SNOW.get());
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));
        }, AnimationType.RIGHT));
    }

}
