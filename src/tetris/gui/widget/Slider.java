/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * The slider holds the slider button used in Settings.
 * This was implemented as it allowed an easier combination with Graphics2D and how our game is implemented and rendered.
 *
 * Implementation details:
 * - The value of the slider is decoupled from the exact value of the slider as it is dictated from the position of the slider button relative to the slider.
 * This is done to avoid rounding issues.
 */
package tetris.gui.widget;

import tetris.controls.MouseInput;

import java.awt.*;

public class Slider extends Button{

    protected int xPos; // Left of the slider

    protected double xSlider; // Position of the button

    // Minimum and maximum value of the slider
    protected int minValue, maxValue;

    protected Image icon; // Appearance of the slider
    protected IPressable onChange; // Communicates the change of slider value

    //The value held by the slider
    //This is to avoid rounding errors
    private double value;

    /*
    Instantiates a slider.
    Specifies position, width, the image, min and max values, and the initial value of the slider.
     */
    public Slider(int xPos, int yPos, int width, Image image, IPressable onChange, int minValue, int maxValue, int initValue) {
        super(xPos, yPos, image, (click)->{});

        //Store arguments
        this.xPos = xPos;
        this.width = width;
        this.icon = image;
        this.minValue = minValue;
        this.maxValue = maxValue;

        //The current value should be the initial value set by the user
        this.value = initValue;

        this.xSlider = (((initValue-minValue+0.0)/(maxValue-minValue))*(width-image.getWidth(null)))+xPos;

        //Store interface to be called when the slider is changed
        this.onChange = onChange;
    }

    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        checkHover(); //Update state of mouse hover
        if (isClicked()) {
            if (MouseInput.getLocation().getX()+icon.getWidth(null)/2 >= width+xPos) { // if mouse goes to the right of the allowed slider space
                xSlider = width+xPos -icon.getWidth(null); // set slider position to the farthest right
            } else if (MouseInput.getLocation().getX()-icon.getWidth(null)/2 <= xPos ) { // if cursor goes to the left of the allowed slider space
                xSlider = xPos; // set slider to the furthest left
            } else { // else: when the cursor is in the allowed slider space
                xSlider = (int) MouseInput.getLocation().getX() - icon.getWidth(null)/2; // keep center of slider at cursor position
            }
            //Change the value held by the slider to the value dictated by the position of the button on the slider
            this.value = this.getRealValue();

            //Call slider interface to state that a change has occurred
            this.onChange.onChange(this);
        }
        g.setColor(new Color(0, 0, 0, 200));
        int offsetY = 20;
        // Draws slider and the rectangle indicating the allowed slider space
        g.fillRect(xPos, (int) (y+offsetY), width, height-2*offsetY);
        g.drawImage(icon, (int) xSlider, (int) y, icon.getWidth(null), icon.getHeight(null), null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1)); //Reset the opacity
    }

    // Get the value held by the slider
    public double getValue(){
        return value;
    }

    // Get the value of the slider based on the position of the button on the slider
    private double getRealValue() {
        return ((xSlider-xPos) / (width-icon.getWidth(null))) * (maxValue-minValue) + minValue;
    }

    // Interface to handle changing other values based on slider value
    public interface IPressable {
        void onChange(Slider onSlide);
    }

}
