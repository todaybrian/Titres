/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * GamePanel class which holds the game's logic
 */
package tetris;

import javax.swing.*;
import java.awt.*;

import tetris.controls.KeyboardInput;
import tetris.controls.MouseInput;
import tetris.gui.GameBackground;
import tetris.gui.Gui;
import tetris.gui.GuiWelcome;
import tetris.music.MusicPlayer;
import tetris.util.Assets;
import tetris.util.Util;


public class GamePanel extends JPanel implements Runnable {
    //Store GamePanel instance
    //This is a technique used in games like Minecraft
    private static GamePanel instance;

    //internal height and width used for rendering
    //We pretend to draw stuff on a 1920 by 1080 screen. This then gets scaled.
    public static final int INTERNAL_WIDTH = 1920;
    public static final int INTERNAL_HEIGHT = 1080;

    //Width and height of the game on the current screen
    public int gameWidth;
    public int gameHeight;

    //The scale that the game will be rendered at compared by a regular 1080p screen
    private double scale;

    //Vertical/Horizontal padding of the game, used in case monitor is not 16:9
    public int verticalPadding;
    public int horizontalPadding;

    public Thread gameThread;
    public Image image;
    public Graphics2D g2d;

    //# of nanoseconds between each render/physics update frame
    private double renderNS;
    private double physicsNS;

    //Displayed gui on the screen. This technique allows us to easily separate menus/guis.
    private Gui gui;

    //Keyboard Input class
    public KeyboardInput keyboardInput;

    //Handles the game backgrounds in the Gui
    private GameBackground gameBackground;

    //The music player
    private MusicPlayer musicPlayer;
    //The sound/sfx player
    private MusicPlayer sfxPlayer;

    // The setting render FPS
    // This is called maximum because if the computer is too slow, the render FPS will be lower than the user setting
    private int maxRenderFPS;

    public GamePanel(int width, int height, double scale, int horizontalPadding, int verticalPadding) {
        GamePanel.instance = this; //Set the GamePanel instance

        //Store the width and height of the game that is being rendered on the user's monitor
        this.gameWidth = width;
        this.gameHeight = height;

        //Store the scale (user's monitor compared to a 1080p screen)
        this.scale = scale;

        //Store the horizontal and vertical padding
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;

        this.setFocusable(true); //make everything in this class appear on the screen

        keyboardInput = new KeyboardInput();

        this.addKeyListener(keyboardInput); //start listening for keyboard input
        this.requestFocus(); //Make window the active window

        this.setPreferredSize(new Dimension(gameWidth, gameHeight));

        //Add a mouse listener to the game panel
        this.addMouseListener(new MouseInput(scale, horizontalPadding, verticalPadding));

        //Load game background images
        gameBackground = new GameBackground();

        musicPlayer = new MusicPlayer(); //Music player
        sfxPlayer = new MusicPlayer(); //Sound Effect/sfx player

        //Play the music, set it to loop, and set volume to 0.9
        musicPlayer.play(Assets.Music.NIGHT_SNOW.get());
        musicPlayer.setLoop(true);
        musicPlayer.changeVolume(0.9);

        //Display Main Menu
        displayGui(new GuiWelcome());

        //make this class run at the same time as other classes (without this each class would "pause" while another class runs). By using threading we can remove lag, and also allows us to do features like display timers in real time!
        gameThread = new Thread(this);
        gameThread.start();

    }
    //run() method is what makes the game continue running without end. It calls other methods to run physics and update the screen
    //Unlike the template run method, this one separates the physics and render updates
    @Override
    public void run() {
        //the CPU runs our game code too quickly - we need to slow it down! The following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen.
        long lastTime = System.nanoTime();

        double deltaRender = 0;
        double deltaPhysics = 0;
        long now;

        while (true) { //this is the infinite game loop
            now = System.nanoTime();
            deltaRender = deltaRender + (now - lastTime) / renderNS;
            deltaPhysics = deltaPhysics + (now - lastTime) / physicsNS;
            lastTime = now;

            //only move objects around if enough time has passed
            if (deltaPhysics >= 1) {
                update();
                deltaPhysics--;
            }
            //only update the screen if enough time has passed
            if(deltaRender >= 1) {
                repaint();
                deltaRender--;
            }
        }
    }

    //This method is called every physics tick. It updates the game's logic and keeps the game consistent regardless of render FPS
    public void update(){
        this.gui.update();
    }

    //Method to set the physics framerate
    //Calculates the number of nanoseconds between each physics update
    public void setPhysicsFPS(int fps){
        physicsNS = 1e9 / fps; //1e9 nanoseconds per fps frames
    }

    //Method to set the render framerate
    //Calculates the number of nanoseconds between each render update
    public void setRenderFPS(int fps){
        this.maxRenderFPS = fps; //Set the maximum render FPS
        renderNS = 1e9 / fps;  //1e9 nanoseconds per fps frames
    }

    //paint is a method in java.awt library that we are overriding. It is a special method - it is called automatically in the background in order to update what appears in the window. You NEVER call paint() yourself
    public void paint(Graphics g){
        //we are using "double buffering" here - if we draw images directly onto the screen, it takes time and the human eye can actually notice flashes of lag as each pixel on the screen is drawn one at a time. Instead, we are going to draw images OFF the screen (outside dimensions of the frame), then simply move the image on screen as needed.
        image = createImage(INTERNAL_WIDTH, INTERNAL_HEIGHT); //draw off-screen

        g2d = (Graphics2D) image.getGraphics();
        Util.setGraphicsFlags(g2d); //Make the game look better on different monitors

        draw(g2d);//update the positions of everything on the screen

        //Using information calculated previously, we draw the game (1920 width and 1080 height) on the screen with horizontal and vertical padding
        g.drawImage(image, horizontalPadding, verticalPadding, gameWidth-horizontalPadding, gameHeight-verticalPadding, 0, 0, 1920, 1080, this);
    }

    //Draw the current Gui object on the screen
    public void draw(Graphics2D g){
        this.gui.draw(g);
    }

    //Method to display a new Gui object on the screen
    public void displayGui(Gui menu){
        this.gui = menu;
    }

    //Method to get the current Gui object
    public Gui getGui(){
    	return this.gui;
    }

    //Getter for the GameBackground class. This will keep the game background in between menus.
    public GameBackground getGameBackground(){
        return this.gameBackground;
    }

    //Getter for Game Panel instance
    public static GamePanel getGamePanel(){
        return GamePanel.instance;
    }

    //Get the FPS that the game is set to be capped at
    public int getMaxRenderFPS(){
        return maxRenderFPS;
    }

    //Getter for the music player
    public MusicPlayer getMusicPlayer(){
    	return musicPlayer;
    }

    // Getter for the sound/sfx player
    public MusicPlayer getSFXPlayer(){
    	return sfxPlayer;
    }

    //Stop the game
    public void exitGame(){
        System.exit(0);
    }
}
