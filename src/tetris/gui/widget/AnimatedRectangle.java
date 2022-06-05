package tetris.gui.widget;

import tetris.wrapper.GraphicsWrapper;

import java.awt.*;

public class AnimatedRectangle extends Rectangle {

    public double x;
    public double y;

    private int originalX;
    private int originalY;

    private int xOffsetGoal;
    private int yOffsetGoal;
    private float opacityGoal;
    public double xOffsetCurrent;
    private double yOffsetCurrent;
    private double xOffsetStep;
    private double yOffsetStep;
    private double opacityStep;

    public float opacity;

    private double animationLength;
    private long lastSystemTime;

    private IDrawable drawable;
    public AnimationType animationType;

    public boolean inTransition;

    public AnimatedRectangle(int x, int y, int width, int height, IDrawable drawable, AnimationType animationType) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;

        originalX = x;
        originalY = y;

        this.drawable = drawable;
        this.animationType = animationType;

        this.opacity = 1;

        lastSystemTime = System.nanoTime();
    }

    public AnimatedRectangle(int x, int y, int width, int height, IDrawable drawable) {
        this(x, y, width, height, drawable, AnimationType.NONE);
    }

    public AnimatedRectangle(int x, int y, int width, int height, AnimationType animationType) {
        this(x, y, width, height, (click, X)->{}, animationType);
    }

    public AnimatedRectangle(IDrawable drawable, AnimationType animationType) {
        this(0, 0, 0, 0, drawable, animationType);
    }

    public void draw(GraphicsWrapper g){
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        drawable.draw(g, this.x);
        animate();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
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

    public void initAnimate(int xOffsetGoal, int yOffsetGoal, float opacityGoal, long animationLength){
        this.xOffsetGoal = xOffsetGoal;
        this.yOffsetGoal = yOffsetGoal;
        this.opacityGoal = opacityGoal;
        this.animationLength = animationLength;
        this.xOffsetStep = (xOffsetGoal - xOffsetCurrent+0.0) / (animationLength);
        this.opacityStep = (this.opacityGoal - opacity+0.0) / (animationLength);
        this.yOffsetStep = (yOffsetGoal - yOffsetCurrent+0.0) / (animationLength);
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

    public interface IDrawable {
        void draw(GraphicsWrapper g, double x);
    }

}
