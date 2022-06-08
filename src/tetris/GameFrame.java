package tetris;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GamePanel panel;
    private GraphicsEnvironment graphicsEnvironment;
    private GraphicsDevice graphicsDevice;
    private GraphicsConfiguration graphicsConfiguration;
    public DisplayMode displayMode;
    private int displayWidth, displayHeight;
    private int renderWidth, renderHeight;
    private int horizontalPadding, verticalPadding;

    public GameFrame(){
        // What does this do?
        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
        displayMode = graphicsDevice.getDisplayMode();

        displayWidth = displayMode.getWidth();
        displayHeight = displayMode.getHeight();

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

        panel = new GamePanel(displayWidth, displayHeight, renderHeight, horizontalPadding, verticalPadding);

        this.add(panel);
        panel.setPhysicsFPS(144);
        panel.setRenderFPS(Math.min(60, displayMode.getRefreshRate()));
        this.setTitle("Tetris"); //set title for frame
        this.setUndecorated(true); //remove window border

        this.setResizable(false); //frame can't change size
        this.setBackground(Color.WHITE); //set background color
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
        this.pack();//makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //maximize window

        this.setVisible(true); //makes window visible to user

        this.setLocationRelativeTo(null);//set window in middle of screen
    }
}
