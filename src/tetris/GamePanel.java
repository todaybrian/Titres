package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GamePanel extends JPanel implements KeyListener, Runnable {

    public int gameWidth;
    public int gameHeight;

    public int renderWidth;
    public int renderHeight;

    public int verticalPadding;
    public int horizontalPadding;

    public Thread gameThread;
    public Image image;

    public GamePanel(int width, int height, int renderWidth, int renderHeight, int verticalPadding, int horizontalPadding) {
        gameWidth = width;
        gameHeight = height;

        this.renderHeight = renderHeight;
        this.renderWidth = renderWidth;

        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;

        this.setFocusable(true); //make everything in this class appear on the screen
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void run() {
        //the CPU runs our game code too quickly - we need to slow it down! The following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen.
        long lastTime = System.nanoTime();
        double amountOfTicks = 144;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long now;

        while (true) { //this is the infinite game loop
            now = System.nanoTime();
            delta = delta + (now - lastTime) / ns;
            lastTime = now;
            //only move objects around and update screen if enough time has passed
            if (delta >= 1) {
                //move();
                //checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public void paint(Graphics g){
        image = createImage(renderWidth, renderHeight); //draw off screen
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        GraphicsWrapper gw = new GraphicsWrapper(g2d, (double)Main.instance.displayMode.getHeight()/1000);


        draw(g2d);//update the positions of everything on the screen

        g.drawImage(image, horizontalPadding, verticalPadding, gameWidth-horizontalPadding, gameHeight-verticalPadding, 0, 0, renderWidth, renderHeight, this);
    }

    public void draw(Graphics g){

        g.setColor(Color.BLUE);
        g.fillRect(500, 500, 500, 500);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
