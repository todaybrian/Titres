package tetris.gui;

import tetris.game.GameMode;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.util.Assets;

import java.awt.*;

public class GuiResults extends Gui {

    public GuiResults(GameMode gameMode, long finalScore) {
        super();
        topBar = Assets.Gui.TOP_RESULTS.get();
        bottomBar = Assets.Gui.BOTTOM_RESULTS.get();

        // Back button to go back to the main menu (top left)
        Image back_button = Assets.Button.BACK_BUTTON.get();
        buttonList.add(new Button(-170, 120, back_button, (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu())); //Display the main menu
            instance.getSFXPlayer().play(Assets.SFX.CLICK_BACK.get()); //Play the click back sound

        }, AnimationType.LEFT));

        AnimatedRectangle results = new AnimatedRectangle((g, x)->{
            g.setColor(new Color(32, 30, 54));
            g.fillRect((int) (x + 300), 160, 1400, 280);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(new Color(115, 101, 151));
            g.drawString("RESULTS", (int) (x + 320), 230);

            g.setColor(new Color(28, 26, 47));
            g.fillRect((int) (x+330), 240, 1340, 200);

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(Color.WHITE);

            String score = "";
            switch(gameMode){
                case BLITZ:
                    score = String.valueOf(finalScore);
                    break;
                case FOURTY_LINES:
                    int min = (int) (finalScore / 1000 / 60);
                    int sec = (int) (finalScore/1000 % 60);
                    int ms = (int) (finalScore%1000);
                    score = String.format("%d:%2d.%2d", min, sec, ms);
            }
        }, AnimationType.RIGHT);

        componentList.add(results);

        instance.getMusicPlayer().stopMusic();

        buttonList.add(new tetris.gui.widget.Button( 400, 520,Assets.Button.RETRY_BUTTON.get(), (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiTetris(gameMode), 0.5, true));
        }, AnimationType.RIGHT));

        buttonList.add(new Button(400, 660, Assets.Button.BACK_TO_TITLE_BUTTON.get(), (click)->{
            instance.getMusicPlayer().play(Assets.Music.NIGHT_SNOW.get());
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));
        }, AnimationType.RIGHT));
    }

}
