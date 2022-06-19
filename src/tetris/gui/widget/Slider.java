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

    protected int xPos;

    protected double xSlider;

    // Minimum and maximum value of the slider
    protected int minValue, maxValue;

    protected Image icon;
    protected IPressable onChange;

    //The value held by the slider
    //This is to avoid rounding errors
    private double value;

    public Slider(int xPos, int yPos, int width, Image image, IPressable onChange, int minValue, int maxValue, int initValue) {
        super(xPos, yPos, image, (click)->{});

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

        checkHover();
        if (isClicked()) {
            if (MouseInput.getLocation().getX()+icon.getWidth(null) > width+xPos) {
                xSlider = width+xPos -icon.getWidth(null);
            } else if (MouseInput.getLocation().getX() < xPos ) {
                xSlider = xPos;
            } else {
                xSlider = (int) MouseInput.getLocation().getX() - icon.getWidth(null)/2;
            }
            //Change the value held by the slider to the value dictated by the position of the button on the slider
            this.value = this.getRealValue();

            //Call slider interface to state that a change has occurred
            this.onChange.onChange(this);
        }
        g.setColor(new Color(0, 0, 0, 200));
        int offsetY = 20;
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

    public interface IPressable {
        void onChange(Slider onSlide);
    }

}
