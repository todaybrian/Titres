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

    public int renderHeight;

    //Vertical/Horizontal padding of the game, used in case monitor is not 16:9
    public int verticalPadding;
    public int horizontalPadding;

    public Thread gameThread;
    public Image image;

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

    public GamePanel(int width, int height, int renderHeight, int horizontalPadding, int verticalPadding) {
        GamePanel.instance = this; //Set the GamePanel instance

        gameWidth = width;
        gameHeight = height;

        this.renderHeight = renderHeight;

        //Store the horizontal and vertical padding
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;

        this.setFocusable(true); //make everything in this class appear on the screen
        keyboardInput = new KeyboardInput();
        this.addKeyListener(keyboardInput);
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));

        this.addMouseListener(new MouseInput());
        MouseInput.setScale((double)renderHeight/1080, horizontalPadding, verticalPadding);

        gameBackground = new GameBackground();

        musicPlayer = new MusicPlayer(); //Music player
        sfxPlayer = new MusicPlayer(); //Sound Effect/sfx player

        musicPlayer.play(Assets.Music.NIGHT_SNOW.get());
        musicPlayer.setLoop(true);
        musicPlayer.changeVolume(0.9);

        sfxPlayer.play(Assets.SFX.SILENCE.get());

        //Display Main Menu
        displayGui(new GuiWelcome());

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

        long previousFPSTime = System.nanoTime();
        int countUpdate = 0, countRender = 0;

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

    public void paint(Graphics g){
        image = createImage(INTERNAL_WIDTH, INTERNAL_HEIGHT); //draw off screen

        Graphics2D g2d = (Graphics2D) image.getGraphics();
        Util.setGraphicsFlags(g2d); //set the graphics flags which make the game look better on different monitors

        draw(g2d);//update the positions of everything on the screen

        //Using information calculated previously, we draw the game (1920 width and 1080 height) on the screen with
        // "horizontalPadding" widths on the left and right
        // "verticalPadding" height on the top and bottom
        //
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
