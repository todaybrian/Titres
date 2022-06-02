package tetris;

import javax.swing.*;
import java.awt.*;

import tetris.controls.MouseInput;
import tetris.gui.Gui;
import tetris.gui.GuiMainMenu;
import tetris.settings.GameSettings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GamePanel extends JPanel implements KeyListener, Runnable {
    //Store GamePanel instance
    //This is a technique used in games like Minecraft
    private static GamePanel instance;

    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;

    public int gameWidth;
    public int gameHeight;

    public int renderWidth;
    public int renderHeight;

    public int verticalPadding;
    public int horizontalPadding;

    public Thread gameThread;
    public Image image;

    //# of nanoseconds between each render/physics update frame
    public double renderNS;
    public double physicsNS;

    //Is the program running?
    public boolean isGameRunning;

    //Displayed menu
    private Gui gui;

    private GameSettings gameSettings;

    public GamePanel(int width, int height, int renderWidth, int renderHeight, int verticalPadding, int horizontalPadding) {
        GamePanel.instance = this;

        gameWidth = width;
        gameHeight = height;

        this.renderHeight = renderHeight;
        this.renderWidth = renderWidth;

        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;

        this.setFocusable(true); //make everything in this class appear on the screen
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));

        isGameRunning = true;

        //Display Main Menu
        displayGui(new GuiMainMenu(null));
        MouseInput.setScale((double)gameHeight/1080);

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void run() {
        //the CPU runs our game code too quickly - we need to slow it down! The following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen.
        long lastTime = System.nanoTime();

        double deltaRender = 0;
        double deltaPhysics = 0;
        long now;

        while (isGameRunning) { //this is the infinite game loop
            now = System.nanoTime();
            deltaRender = deltaRender + (now - lastTime) / renderNS;
            deltaPhysics = deltaPhysics + (now - lastTime) / physicsNS;
            lastTime = now;

            //only move objects around  if enough time has passed
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

    }

    public void setPhysicsFPS(int fps){
        physicsNS = 1e9 / fps;
    }

    public void setRenderFPS(int fps){
        renderNS = 1e9 / fps;
    }

    public void paint(Graphics g){
        image = createImage(renderWidth, renderHeight); //draw off screen
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        GraphicsWrapper gw = new GraphicsWrapper(g2d, (double)gameHeight/1080);

        draw(gw);//update the positions of everything on the screen

        g.drawImage(image, horizontalPadding, verticalPadding, gameWidth-horizontalPadding, gameHeight-verticalPadding, 0, 0, renderWidth, renderHeight, this);
    }

    public void draw(GraphicsWrapper g){
        if(this.gui != null){
            this.gui.draw(g);
        }
    }

    public void displayGui(Gui menu){
        this.gui = menu;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {}

    //Getter for Game Panel instance
    public static GamePanel getGamePanel(){
        return GamePanel.instance;
    }

    public GameSettings getSettings(){
        return gameSettings;
    }

    //Stop the game
    public void exitGame(){
        this.isGameRunning = false;
    }
}
