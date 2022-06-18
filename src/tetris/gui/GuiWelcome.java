/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * This is the Gui that is shown when the game opens.
 * To display an animation, a gif file is displayed on the screen.
 */
package tetris.gui;

import tetris.GamePanel;
import tetris.util.Assets;
import tetris.util.FrameTimer;

import java.awt.*;

public class GuiWelcome extends Gui {
    //Number of seconds that this will be displayed on the screen
    private static final double TIME_ON_SCREEN = 4;

    // FrameTimer to keep track of the time passed
    private final FrameTimer timer;

    // Store the image of the welcome screen
    private final Image welcomeScreen;

    public GuiWelcome() {
        super(); //Call the super constructor
        this.timer = new FrameTimer(TIME_ON_SCREEN); //Initialize the timer
        this.welcomeScreen = Assets.Gui.WELCOME_SCREEN.get(); //Initialize the welcome screen image
    }

    public void draw(Graphics2D g){
        //Draw the welcome screen on the full width of the screen
        g.drawImage(welcomeScreen, 0, 0, GamePanel.INTERNAL_WIDTH, GamePanel.INTERNAL_HEIGHT, null);

        //If time to display is up, display the main menu
        if(timer.isDone()){
            instance.displayGui(new GuiMainMenu());
        }
    }
}
