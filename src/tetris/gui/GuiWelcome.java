package tetris.gui;

import tetris.GamePanel;
import tetris.wrapper.GraphicsWrapper;
import tetris.util.Assets;

import javax.swing.*;

public class GuiWelcome extends Gui {
    //Number of nanoseconds that this will be displayed on the screen
    private final long NUMBER_OF_NS = 4 * 1000000000L;

    //Current number of render ticks that have passed, used to determine when to remove this from the screen
    private long currentNS;

    public GuiWelcome(Gui parentScreen) {
        super(parentScreen);
        currentNS = System.nanoTime();
    }

    public void draw(GraphicsWrapper g){
        if(System.nanoTime() - currentNS < NUMBER_OF_NS){
            currentNS++;
            ImageIcon welcomeScreen = new ImageIcon(Assets.WELCOME_SCREEN);
            g.drawImage(welcomeScreen.getImage(), 0, 0, GamePanel.INTERNAL_WIDTH, GamePanel.INTERNAL_HEIGHT);
        } else{
            // Display Main Menu
            instance.displayGui(new GuiMainMenu(this));
        }
    }
}
