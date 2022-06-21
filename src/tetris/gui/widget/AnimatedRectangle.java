/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Superclass for buttons and text-displaying rectangles. Can be made to animate on hover, click, etc.
 */

package tetris.gui.widget;

import tetris.controls.MouseInput;

import java.awt.*;

public class AnimatedRectangle extends Rectangle {

    // Rendered location of the rectangle
    public double x;
    public double y;

    //Original location of the rectangle without any animation
    private int originalX;
    private int originalY;

    //Was the left button of mouse pressed (but not released) on this button?
    protected boolean isClicked;

    //Is the cursor over the button?
    protected boolean isMouseOver;

    //How will the rectangle change when it is animated?
    private int xOffsetGoal; //Where will the rectangle go vertically?
    private int yOffsetGoal; //Where will the rectangle go horizontally?
    private float opacityGoal; //What will the opacity be?

    //How much will the animation move per tick?
    private double xOffsetStep; //Horizontal
    private double yOffsetStep; // Vertical
    private double opacityStep; //Opacity percentages


    private double xOffsetCurrent; //Where is the rectangle currently relative to its original position horizontally?
    private double yOffsetCurrent; //Where is the rectangle currently relative to its original position vertically?

    public float opacity; //What is the rectangle's current opacity?

    private long lastSystemTime;

    //Interface which allows for custom editing of the rectangle
    private IDrawable drawable; // Icons, etc.

    public AnimationType animationType; // Which direction does the object need to animate in?

    protected boolean inTransition; // Is the object currently in a transition?

    public AnimatedRectangle(int x, int y, int width, int height, IDrawable drawable, AnimationType animationType) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;

        originalX = x;
        originalY = y;

        this.drawable = drawable;
        this.animationType = animationType;

        this.opacity = 1;
        this.isMouseOver = false;
        this.isClicked = false;

        lastSystemTime = System.nanoTime();
    }

    public AnimatedRectangle(int x, int y, int width, int height, AnimationType animationType) {
        this(x, y, width, height, (click, X)->{}, animationType);
    }

    public AnimatedRectangle(IDrawable drawable, AnimationType animationType) {
        this(0, 0, 0, 0, drawable, animationType);
    }

    public void draw(Graphics2D g){
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        drawable.draw(g, (int)this.x); // Draw the object with x (represents the horizontal animation offset)
        animate();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    /**
     * Updates the mouseover state of the button.
     *
     */
    protected void checkHover() {
        isMouseOver = (MouseInput.getLocation().getX() > x && MouseInput.getLocation().getX() < x +width) && (MouseInput.getLocation().getY() > y && MouseInput.getLocation().getY() < y +height);
    }

    // Returns true if the mouse is over the button
    public boolean isMouseOver() {
        return isMouseOver;
    }

    // Returns true if the left mouse button is pressing the button
    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public void animate(){
        boolean toRight = xOffsetGoal > xOffsetCurrent;
        xOffsetCurrent += xOffsetStep * (System.nanoTime() - lastSystemTime);

        if(xOffsetCurrent >= xOffsetGoal && toRight){
            xOffsetCurrent = xOffsetGoal;
            xOffsetStep = 0;
        } else if(xOffsetCurrent <= xOffsetGoal && !toRight){
            xOffsetCurrent = xOffsetGoal;
            xOffsetStep = 0;
        }
        boolean toUp = yOffsetGoal > yOffsetCurrent;
        yOffsetCurrent += yOffsetStep * (System.nanoTime() - lastSystemTime);
        if(yOffsetCurrent >= yOffsetGoal && toUp){
            yOffsetCurrent = yOffsetGoal;
            yOffsetStep = 0;
        } else if(yOffsetCurrent <= yOffsetGoal && !toUp){
            yOffsetCurrent = yOffsetGoal;
            yOffsetStep = 0;
        }

        boolean toOpacity = opacityGoal > opacity;
        opacity += opacityStep * (System.nanoTime() - lastSystemTime);
        if(opacity >= opacityGoal && toOpacity){
            opacity = opacityGoal;
            opacityStep = 0;
        } else if(opacity <= opacityGoal && !toOpacity){
            opacity = opacityGoal;
            opacityStep = 0;
        }

        x = originalX + xOffsetCurrent;
        y = originalY + yOffsetCurrent;

        lastSystemTime = System.nanoTime();
    }

    /**
     * Starts the animation of the button. It performs the necessary calculations to determine how the rectangle will move per nanosecond.
     *
     * @param xOffsetGoal The horizontal offset the rectangle will move to.
     * @param yOffsetGoal The vertical offset the rectangle will move to.
     * @param opacityGoal The opacity the component will be.
     * @param animationLength The number of seconds the animation will take.
     */
    public void initAnimate(int xOffsetGoal, int yOffsetGoal, float opacityGoal, double animationLength){
        this.xOffsetGoal = xOffsetGoal;
        this.yOffsetGoal = yOffsetGoal;
        this.opacityGoal = opacityGoal;

        //Calculate the amount of pixels (horizontally and vertically) and opacity percent the rectangle will move per nanosecond
        this.xOffsetStep = (xOffsetGoal - xOffsetCurrent) / (animationLength * 1e9);
        this.yOffsetStep = (yOffsetGoal - yOffsetCurrent) / (animationLength* 1e9);
        this.opacityStep = (opacityGoal - opacity) / (animationLength* 1e9);
    }

    /**
     * Resets the rectangle to its original position.
     */
    public void reset(){
        //Reset rendered position to original position
        x = originalX;
        y = originalY;

        //Make rectangle fully visible
        opacity = 1;

        //There are no offsets or steps as it is currently at its original position and is not moving
        xOffsetCurrent = 0;
        yOffsetCurrent = 0;
        opacityStep = 0;
        xOffsetStep = 0;
        yOffsetStep = 0;

        //Not animated
        inTransition = false;

        lastSystemTime = System.nanoTime();
    }

    /**
     * Allow the user to set where the rectangle is initially relative to its original position
     *
     * @param xOffset The horizontal offset the rectangle will be initially relative to its original position.
     * @param yOffset The vertical offset the rectangle will be initially relative to its original position.
     * @param opacity The opacity the rectangle will be initially.
     */
    public void setOffsets(int xOffset, int yOffset, float opacity){
        this.xOffsetCurrent = xOffset;
        this.yOffsetCurrent = yOffset;
        this.opacity = opacity;
    }

    /**
     * Set if the state of the rectangle if it is currently in a transition.
     *
     * @param inTransition True if the rectangle is currently in a transition.
     */
    public void setInTransition(boolean inTransition){
        this.inTransition = inTransition;
    }

    /**
     * Interface that allows for objects to be drawn inside the rectangle.
     */
    public interface IDrawable {
        /**
         * Draws objects inside the rectangle. Meant to be defined by the user
         * @param g The graphics object to draw with.
         * @param offsetX The current horizontal offset of the rectangle, which must be handled by the objects
         *                inside this function for it to animate.
         */
        void draw(Graphics2D g, int offsetX);
    }

}
