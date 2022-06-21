/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * This class is used to handle the mouse input, which is used primarily for the buttons/sliders in the menu.
 * Due to the scaling and padding of the game, this class translate the raw mouse coordinates to the scaled coordinates that are used in the game.
 */
package tetris.controls;

import tetris.GamePanel;
import tetris.util.WindowFocus;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    //Instance of the current GamePanel class
    private GamePanel instance;

    //Store variables used to translate the mouse coordinates to the scaled coordinates
    private static double scale;
    private static int horizontalPadding;
    private static int verticalPadding;

    public MouseInput() {
        instance = GamePanel.getGamePanel(); //store instance
    }

    //Gets the scaled coordinates of the mouse
    //Used to determine if the mouse is over a button/slider
    public static Point getLocation(){
        Point ret;
        if(WindowFocus.isShown) { //If the window is shown, then handle mouse coordinates.
            ret = MouseInfo.getPointerInfo().getLocation(); //Get the raw mouse coordinates
            ret.x = scale(ret.x - horizontalPadding); //Subtract the horizontal padding and scale the coordinates
            ret.y = scale(ret.y - verticalPadding); //Subtract the vertical padding and scale the coordinates

            return ret; //Return the scaled coordinates
        } else{ //If it is not shown, then return 0 0 to avoid button mouseover conflicts
            ret = new Point(0, 0);
            return ret;
        }
    }

    //Configures the scale and padding of the game
    public static void setScale(double scale, int horizontalPadding, int verticalPadding) {
        MouseInput.scale = scale;
        MouseInput.horizontalPadding = horizontalPadding;
        MouseInput.verticalPadding = verticalPadding;
    }

    //Given a single coordinate, scale them to the scaled coordinates
    private static int scale(double val) {
        return (int)Math.round(val /scale);
    }

    //If the left mouse button is clicked, send the mouseReleased event to the gui for processing
    @Override
    public void mousePressed(MouseEvent e) {
        //Send left click event
        if(e.getButton() == MouseEvent.BUTTON1){
            instance.getGui().mouseClicked();
        }
    }

    //If the left mouse button is released, send the mouseReleased event to the gui for processing
    @Override
    public void mouseReleased(MouseEvent e) {
        //Send left click release event
        if(e.getButton() == MouseEvent.BUTTON1){
            instance.getGui().mouseReleased();
        }
    }

    //left empty because we don't need it; must be here because it is required to be overridden by the MouseListener interface
    //This function is unreliable, so it is not used.
    @Override
    public void mouseClicked(MouseEvent e) {}

    //left empty because we don't need it; must be here because it is required to be overridden by the MouseListener interface
    @Override
    public void mouseEntered(MouseEvent e){}

    //left empty because we don't need it; must be here because it is required to be overridden by the MouseListener interface
    @Override
    public void mouseExited(MouseEvent e) {}
}
