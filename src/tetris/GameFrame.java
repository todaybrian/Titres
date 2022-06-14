package tetris;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private GamePanel panel;
    private GraphicsEnvironment graphicsEnvironment;
    private GraphicsDevice graphicsDevice;

    private DisplayMode displayMode;
    private int displayWidth, displayHeight;
    private int renderWidth, renderHeight;
    private int horizontalPadding, verticalPadding;

    public GameFrame(){
        //The game was designed on to run on a 1920 by 1080 screen, so we need to scale it to the current screen
        //The following code is used to collect information about the monitor which will be used to figure out the game's resolution
        //If necessary, padding will be used on non 16:9 monitors

        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        displayMode = graphicsDevice.getDisplayMode();

        //Get the current screen width and height
        displayWidth = displayMode.getWidth();
        displayHeight = displayMode.getHeight();

        //The aspect ratio of the game will be 16/9.
        double scale = 16.0 / 9.0;
        if (displayHeight * scale > displayWidth) {
            renderWidth = displayWidth;
            renderHeight = (int)Math.round(displayWidth / scale);
            horizontalPadding = 0;
            verticalPadding = (displayHeight - renderHeight)/2;
        } else {
            renderWidth = (int)Math.round(displayHeight * scale);
            renderHeight = displayHeight;
            horizontalPadding = (displayWidth - renderWidth)/2;
            verticalPadding = 0;
        }

        panel = new GamePanel(displayWidth, displayHeight, renderHeight, horizontalPadding, verticalPadding); //Run GamePanel constructor

        this.add(panel);
        panel.setPhysicsFPS(144); //Set physics update rate to 144 FPS
        panel.setRenderFPS(Math.min(60, displayMode.getRefreshRate()));  //Set the render FPS to 60 or the monitor's refresh rate, whichever is lower

        this.setTitle("Titres!"); //set title for frame
        this.setUndecorated(true); //remove window border

        this.setResizable(false); //frame can't change size

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
        this.pack();//makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //maximize window

        this.setVisible(true); //makes window visible to user
    }
}
