package tetris.gui;

import tetris.GamePanel;
import tetris.util.Assets;
import tetris.util.FrameTimer;

import javax.swing.*;
import java.awt.*;

public class GuiWelcome extends Gui {
    //Number of seconds that this will be displayed on the screen
    private final double TIME_ON_SCREEN = 4;
    private final FrameTimer timer;

    public GuiWelcome() {
        super();
        timer = new FrameTimer(TIME_ON_SCREEN);
    }

    public void draw(Graphics2D g){
        ImageIcon welcomeScreen = new ImageIcon(Assets.WELCOME_SCREEN);
        g.drawImage(welcomeScreen.getImage(), 0, 0, GamePanel.INTERNAL_WIDTH, GamePanel.INTERNAL_HEIGHT, null);

        if(timer.isDone()){
            //Display Main Menu
            instance.displayGui(new GuiMainMenu());
        }
    }
}
