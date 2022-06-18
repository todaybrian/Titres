/**
 * Author: Brian Yan, Aaron Zhang
 *
 * This is the abstract GUI class that all other GUIs inherit from.
 */
package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.Button;

import java.awt.*;
import java.util.ArrayList;

public abstract class Gui {
    // The top and bottom bars of the screen
    protected Image topBar;
    protected Image bottomBar;

    // The list of components to be displayed
    protected ArrayList<AnimatedRectangle> componentList;

    // The list of buttons to be displayed
    protected ArrayList<Button> buttonList;

    // The opacity of the game background
    protected float backgroundOpacity;

    // The instance of the game
    protected GamePanel instance;

    public Gui(){
        //Initialize component list, button list, and background opacity
        buttonList = new ArrayList<>();
        componentList = new ArrayList<>();
        this.backgroundOpacity = 0.25f;

        instance = GamePanel.getGamePanel(); //Get the instance of the game panel
    }

    public void draw(Graphics2D g){
        //Draw the game background
        instance.getGameBackground().setOpacity(backgroundOpacity);
        instance.getGameBackground().draw(g);

        //Top bar
        if(topBar != null) {
            g.drawImage(topBar, 0, 0, topBar.getWidth(null), topBar.getHeight(null), null);
        }
        //Bottom bar
        if(bottomBar != null) {
            g.drawImage(bottomBar, 0, GamePanel.INTERNAL_HEIGHT - bottomBar.getHeight(null), bottomBar.getWidth(null), bottomBar.getHeight(null), null);
        }

        //Draw all components
        for(AnimatedRectangle component : componentList){
            component.draw(g);
        }

        //Draw all buttons and gather information about the mouseover state which is used to determine the cursor
        boolean mouseOverButton = false; //Is the mouse over a button?
        for(Button button : buttonList){
            if(button.isMouseOver()){
                mouseOverButton = true; //If the mouse is over a button, set the mouseoverButton to true
            }
            button.draw(g); //Draw the buttons
        }

        // Set cursor to the appropriate cursor
        if(mouseOverButton){
            instance.setCursor(new Cursor(Cursor.HAND_CURSOR)); //Set the cursor to the click cursor
        } else{
            instance.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //Set the cursor to the default cursor
        }
    }

    //Called by MouseInput when a mouse left button is pressed
    //This method then sends the click to the appropriate button
    //Note: Even if a button is clicked, its action will not be performed until the mouse is released
    public void mouseClicked(){
        for(Button button: buttonList){
            if(button.isMouseOver()) // If the mouse is over the button
                button.setClicked(true); // Set the button state to be clicked
        }
    }

    //Called by MouseInput when a mouse left button is released
    //This method then sends the release to the appropriate button
    //Note: Even if a button is clicked, its action will not be performed until the mouse is released
    public void mouseReleased(){
        for(Button button: buttonList){
            if(button.isClicked() && button.isMouseOver()) // If the button is clicked and the mouse was released over the button
                button.clicked(); // Perform the button's action
            button.setClicked(false); // Set the button state to be not clicked
        }
    }

    //Get the list of buttons that need to be displayed
    //This is used for the transition between screens
    public ArrayList<Button> getButtonList(){
        return buttonList;
    }

    //Get the list of components that need to be displayed
    //This is used for the transition between screens
    public ArrayList<AnimatedRectangle> getComponentList(){
        return componentList;
    }

    // update method that is called every physics tick, used to update physics of the game
    // Meant to be overridden by subclasses
    public void update() {}
}
