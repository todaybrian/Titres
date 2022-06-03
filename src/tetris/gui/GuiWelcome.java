package tetris.gui;

import tetris.GamePanel;
import tetris.GraphicsWrapper;
import tetris.util.Assets;

import javax.swing.*;

public class GuiWelcome extends Gui {
    //Number of render ticks that this will be displayed on the screen
    private final int NUMBER_OF_TICKS = 180;

    //Current number of render ticks that have passed, used to determine when to remove this from the screen
    private int currentTick;

    public GuiWelcome(Gui parentScreen) {
        super(parentScreen);
        currentTick = 0;
    }

    public void draw(GraphicsWrapper g){
        if(currentTick < NUMBER_OF_TICKS){
            currentTick++;
            System.out.println(currentTick);
            ImageIcon welcomeScreen = new ImageIcon(Assets.WELCOME_SCREEN);
            g.drawImage(welcomeScreen.getImage(), 0, 0, GamePanel.INTERNAL_WIDTH, GamePanel.INTERNAL_HEIGHT);
        } else{
            // Display Main Menu
            GamePanel.getGamePanel().displayGui(new GuiMainMenu(this));
        }
    }
}
