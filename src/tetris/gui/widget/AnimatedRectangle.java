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

    public double x;
    public double y;

    private final int originalX;
    private final int originalY;

    //Was the left button of mouse pressed (but not released) on this button?
    protected boolean isClicked;

    //Is the cursor over the button?
    protected boolean isMouseOver;

    //How will the button change when it is animated?
    private int xOffsetGoal;
    private int yOffsetGoal;
    private float opacityGoal;
    private double xOffsetCurrent;
    private double yOffsetCurrent;

    //How much will the animation move per tick?
    private double xOffsetStep;
    private double yOffsetStep;
    private double opacityStep;

    public float opacity;

    private long lastSystemTime;

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

    public boolean isMouseOver() {
        return isMouseOver;
    }

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

    public void initAnimate(int xOffsetGoal, int yOffsetGoal, float opacityGoal, double animationLength){
        this.xOffsetGoal = xOffsetGoal;
        this.yOffsetGoal = yOffsetGoal;
        this.opacityGoal = opacityGoal;

        this.xOffsetStep = (xOffsetGoal - xOffsetCurrent+0.0) / (animationLength * 1000000000);
        this.opacityStep = (this.opacityGoal - opacity+0.0) / (animationLength* 1000000000);
        this.yOffsetStep = (yOffsetGoal - yOffsetCurrent+0.0) / (animationLength* 1000000000);
    }

    public void reset(){
        x = originalX;
        y = originalY;
        opacity = 1;
        xOffsetCurrent = 0;
        yOffsetCurrent = 0;
        opacityStep = 0;
        xOffsetStep = 0;
        yOffsetStep = 0;
        inTransition = false;
        lastSystemTime = System.nanoTime();
    }

    public void setOffsets(int xOffset, int yOffset, float opacity){
        this.xOffsetCurrent = xOffset;
        this.yOffsetCurrent = yOffset;
        this.opacity = opacity;
    }

    public void setInTransition(boolean inTransition){
        this.inTransition = inTransition;
    }

    public interface IDrawable {
        void draw(Graphics2D g, int x);
    }

}
