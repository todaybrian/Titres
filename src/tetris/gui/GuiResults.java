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
        // Top and bottom menu bars
        topBar = Assets.Gui.TOP_RESULTS.get();
        bottomBar = Assets.Gui.BOTTOM_RESULTS.get();

        // Back button to go back to the main menu (top left)
        buttonList.add(new Button(-170, 120, Assets.Button.BACK_BUTTON.get(), (click) -> {

            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu())); //Display the main menu
            sfxPlayer.play(Assets.SFX.CLICK_BACK.get()); //Play the click back sound

        }, AnimationType.LEFT));

        AnimatedRectangle results = new AnimatedRectangle((g, offsetX)->{
            //offsetX is the horizontal offset variable which is used to create a transition effect for animation
            //It must be added to the x coordinate of relevant components

            FontMetrics fm; //Font metrics for font
            String scoreText = ""; //Text to be displayed in score box

            g.setColor(new Color(32, 30, 54));
            g.fillRect(offsetX + 300, 160, 1400, 280); // Score box

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(new Color(115, 101, 151));
            g.drawString("RESULTS", offsetX + 320, 230); // "RESULTS" text

            g.setColor(new Color(28, 26, 47));
            g.fillRect(offsetX+330, 240, 1340, 180); // Box below score box

            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 60));
            g.setColor(Color.WHITE);

            fm = g.getFontMetrics(); //Get font metrics to center text

            //Figure out the score text that will be shown in results
            switch(gameMode){
                case BLITZ:
                    scoreText = String.format("Lines Cleared: %d", finalScore);
                    break;
                case FORTY_LINES:
                    //Calculate the minutes, seconds, and milliseconds from the final score (in milliseconds)
                    int min = (int) (finalScore / 1000 / 60);
                    int sec = (int) (finalScore/1000 % 60);
                    int ms = (int) (finalScore%1000);
                    scoreText = String.format("Time: %d:%02d.%03d", min, sec, ms);
            }
            g.drawString(scoreText, 1000-fm.stringWidth(scoreText)/2, 355);

        }, AnimationType.RIGHT);

        componentList.add(results);

        instance.getMusicPlayer().stopMusic(); // Stop the game music when the game ends

        // Retry button
        buttonList.add(new tetris.gui.widget.Button( 400, 620,Assets.Button.RETRY_BUTTON.get(), (click)->{
            instance.displayGui(new GuiMenuTransition(this, new GuiTetris(gameMode), 0.5, true));
            sfxPlayer.play(Assets.SFX.CLICK_START.get());
        }, AnimationType.RIGHT));

        // Go back to main menu button
        buttonList.add(new Button(400, 760, Assets.Button.BACK_TO_TITLE_BUTTON.get(), (click)->{
            musicPlayer.play(Assets.Music.NIGHT_SNOW.get());
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));
            sfxPlayer.play(Assets.SFX.CLICK_BACK.get());
        }, AnimationType.RIGHT));
    }

}
