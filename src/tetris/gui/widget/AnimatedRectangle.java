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

    /**
     * Constructor for animated rectangles.
     *
     * @param x The original x-coordinate of the rectangle.
     * @param y The original y-coordinate of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param drawable Custom objects that will be drawn on the rectangle.
     * @param animationType The animation type of the rectangle.
     */
    public AnimatedRectangle(int x, int y, int width, int height, IDrawable drawable, AnimationType animationType) {
        super(x, y, width, height); // Call the superclass constructor for a basic rectangle.

        //Store current location of the rectangle
        this.x = x;
        this.y = y;

        //Keep track of the original location of the rectangle
        originalX = x;
        originalY = y;

        //Store the custom drawable object and animation type
        this.drawable = drawable;
        this.animationType = animationType;

        //Set the opacity to 100% so it is visible
        this.opacity = 1;

        //Currently doesn't have a mouse over or is clicked
        this.isMouseOver = false;
        this.isClicked = false;

        //Not in transition
        this.inTransition = false;

        //Set the last system time to now, used for animations
        lastSystemTime = System.nanoTime();
    }

    /**
     * Creates a rectangle.
     * Mainly used for buttons.
     *
     * @param x The original x-coordinate of the rectangle.
     * @param y The original y-coordinate of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param animationType The animation type of the rectangle.
     */
    public AnimatedRectangle(int x, int y, int width, int height, AnimationType animationType) {
        this(x, y, width, height, (click, X) -> {
        }, animationType);
    }

    /**
     * Creates a rectangle.
     * Mostly used when animation is required on basic rectangles.
     *
     * @param drawable The interface representing custom content of the rectangle.
     * @param animationType The animation type of the rectangle.
     */
    public AnimatedRectangle(IDrawable drawable, AnimationType animationType) {
        this(0, 0, 0, 0, drawable, animationType);
    }

    /**
     * Draws the rectangle.
     *
     * @param g The graphics object to draw on.
     */
    public void draw(Graphics2D g) {
        //Set the opacity of the rectangle
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        drawable.draw(g, (int) this.x); // Draw the object with a horizontal offset of x (represents the horizontal animation offset)
        animate(); // Animate the rectangle

        // Reset the opacity to 100% for other objects
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    /**
     * Updates the mouseover state of the button.
     *
     */
    protected void checkHover() {
        isMouseOver = (MouseInput.getLocation().getX() > x && MouseInput.getLocation().getX() < x + width) && (MouseInput.getLocation().getY() > y && MouseInput.getLocation().getY() < y + height);
    }

    // Returns true if the mouse is over the button
    public boolean isMouseOver() {
        return isMouseOver;
    }

    // Returns true if the left mouse button is pressing the button
    public boolean isClicked() {
        return isClicked;
    }

    // Allows the click state of the button to be changed (i.e. by the mouse input)
    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    /**
     * Animates the rectangle. This is called every render tick, but should stay consistant as it is based on time.
     */
    public void animate() {
        // How many nanoseconds have passed since the last render tick?
        long numberOfNSPassed = System.nanoTime() - lastSystemTime;

        /* ------ Horizontal Animation ------ */
        boolean toRight = xOffsetGoal > xOffsetCurrent; // Is the rectangle moving to the right?

        // Update the current animation offset.
        xOffsetCurrent += xOffsetStep * numberOfNSPassed;

        // If the rectangle is moving to the right and it is now past the goal, set the current offset to the goal and stop the animation.
        if (xOffsetCurrent >= xOffsetGoal && toRight) {
            xOffsetCurrent = xOffsetGoal;
            xOffsetStep = 0;
        } else if (xOffsetCurrent <= xOffsetGoal && !toRight) { // If the rectangle is moving to the left and it is now past the goal, set the current offset to the goal and stop the animation.
            xOffsetCurrent = xOffsetGoal;
            xOffsetStep = 0;
        }

        /* ------ Vertical Animation ------ */

        boolean toUp = yOffsetGoal > yOffsetCurrent; // Is the rectangle moving up?

        // Update the current animation offset.
        yOffsetCurrent += yOffsetStep * numberOfNSPassed;

        if (yOffsetCurrent >= yOffsetGoal && toUp) { // If the rectangle is moving up and it is now past the goal, set the current offset to the goal and stop the animation.
            yOffsetCurrent = yOffsetGoal;
            yOffsetStep = 0;
        } else if (yOffsetCurrent <= yOffsetGoal && !toUp) { // If the rectangle is moving down and it is now past the goal, set the current offset to the goal and stop the animation.
            yOffsetCurrent = yOffsetGoal;
            yOffsetStep = 0;
        }

        /* ------ Opacity Animation ------ */

        boolean toOpacity = opacityGoal > opacity; // Is the rectangle fading in?

        // Update the current animation offset.
        opacity += opacityStep * numberOfNSPassed;

        if (opacity >= opacityGoal && toOpacity) { // If the rectangle is fading in and it is now past the goal, set the current opacity to the goal and stop the animation.
            opacity = opacityGoal;
            opacityStep = 0;
        } else if (opacity <= opacityGoal && !toOpacity) { // If the rectangle is fading out and it is now past the goal, set the current opacity to the goal and stop the animation.
            opacity = opacityGoal;
            opacityStep = 0;
        }

        // Set the rendered position of the rectangle using the original positions and the current animation offsets.
        x = originalX + xOffsetCurrent;
        y = originalY + yOffsetCurrent;

        //set system time for the next render tick
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
    public void initAnimate(int xOffsetGoal, int yOffsetGoal, float opacityGoal, double animationLength) {
        // Set the goal offsets
        this.xOffsetGoal = xOffsetGoal;
        this.yOffsetGoal = yOffsetGoal;
        this.opacityGoal = opacityGoal;

        //Calculate the amount of pixels (horizontally and vertically) and opacity percent the rectangle will move per nanosecond
        this.xOffsetStep = (xOffsetGoal - xOffsetCurrent) / (animationLength * 1e9);
        this.yOffsetStep = (yOffsetGoal - yOffsetCurrent) / (animationLength * 1e9);
        this.opacityStep = (opacityGoal - opacity) / (animationLength * 1e9);
    }

    /**
     * Resets the rectangle to its original position.
     */
    public void reset() {
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
    public void setOffsets(int xOffset, int yOffset, float opacity) {
        this.xOffsetCurrent = xOffset;
        this.yOffsetCurrent = yOffset;
        this.opacity = opacity;
    }

    /**
     * Set if the state of the rectangle if it is currently in a transition.
     *
     * @param inTransition True if the rectangle is currently in a transition.
     */
    public void setInTransition(boolean inTransition) {
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
