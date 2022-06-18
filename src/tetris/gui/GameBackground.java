/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * This class is used to handle the game's background.
 */
package tetris.gui;

import tetris.GamePanel;
import tetris.util.Assets;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBackground {
    // ArrayList of all the background images
    private ArrayList<ImageIcon> images;

    // The current background image
    private ImageIcon currentBackground;

    // The current background image opacity
    private float opacity;

    public GameBackground(){
        images = new ArrayList<>();
        this.opacity = 0.25f; //Default opacity
        loadAssets(); //Load all images
        randomBackground(); //Set a random background
    }

    //Draw the background
    public void draw(Graphics2D g){
        //Set the game backdrop to be black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.INTERNAL_WIDTH, GamePanel.INTERNAL_HEIGHT);

        //Draw the current background with the opacity
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)); //Set the opacity
        g.drawImage(currentBackground.getImage(), 0, 0, currentBackground.getIconWidth(), currentBackground.getIconHeight(), null); //Draw the background
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); //Reset the opacity
    }

    //Chooses a random background image
    public void randomBackground(){
        int randomIdx = (int)(Math.random() * images.size()); //Generate a random index
        currentBackground = images.get(randomIdx); //Set the current background to the image at the random index
    }

    //Sets the opacity of the background in the GUI
    //Used in the different GUI classes. For example, the background has a lower opacity in the menu than the game.
    public void setOpacity(float newOpacity){
        this.opacity = newOpacity;
    }

    //Loads all the background images from the assets folder
    //All background images are named in the form of #.jpg, where # is the index of the image
    private void loadAssets(){
        try{
            for(int imageId=0;; imageId++){ //Loops up the image IDs
                ImageIcon image = new ImageIcon(Assets.BACKGROUND_PREFIX + imageId + ".jpg"); //Loads the image

                if(image.getIconWidth() == -1) break; //If the image is not found, break

                images.add(image); //Adds the image to the list of images
            }
        } catch(Exception e){ //Used to avoid any errors that may happen when loading the images
            e.printStackTrace();
        }
    }
}
