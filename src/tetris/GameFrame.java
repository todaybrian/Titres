package tetris;

import tetris.util.Assets;

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

    //The aspect ratio of the game will be 16/9.
    private static final double aspectRatio = 16.0 / 9.0;

    public GameFrame(){
        //The game was designed on to run on a 1920 by 1080 screen, so we need to scale it to the current screen
        //The following code is used to collect information about the monitor which will be used to figure out the game's resolution
        //If necessary, padding will be used on non 16:9 monitors

        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        displayMode = graphicsDevice.getDisplayMode();

        //Get the current display screen width and height
        displayWidth = displayMode.getWidth();
        displayHeight = displayMode.getHeight();

        //If the screen is a thin screen
        if (displayHeight * aspectRatio > displayWidth) {
            //We set the monitor width to be the width of our rendered game.
            renderWidth = displayWidth;

            //If renderWidth is the width of the game, we use the ratio to find the rendered height to ensure 16:9 ratio
            renderHeight = (int)Math.round(displayWidth / aspectRatio);

            //No horizontal padding as the monitor width is the width of our rendered game
            horizontalPadding = 0;

            //The vertical padding on one side is the difference of the monitor height and the game's rendered height divided by two to account for the top and the bottom
            verticalPadding = (displayHeight - renderHeight)/2;
        } else { //If the screen is a wide screen
            //We set the monitor's height to be the height of our rendered game
            renderHeight = displayHeight;

            //If the renderHeight is the height of the game, we use ratios to find the rendered width to ensure a 16:9 ratio
            renderWidth = (int)Math.round(displayHeight * aspectRatio);

            //No vertical padding as the monitor height is the height of our rendered game
            verticalPadding = 0;

            //The horizontal padding on one side is the difference of the monitor width and the game's rendered width divided by two to account for the left and right sides
            horizontalPadding = (displayWidth - renderWidth)/2;
        }

        //Run a GamePanel constructor with the required arguments needed to scale the game
        panel = new GamePanel(displayWidth, displayHeight, renderHeight, horizontalPadding, verticalPadding);

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

        this.setIconImage(Assets.Gui.LOGO.get()); //set icon for frame
    }
}
