package tetris.gui.widget;

import java.awt.*;

public class AnimatedRectangle extends Rectangle {
    public double x;
    public double y;

    private int originalX;
    private int originalY;

    private int xOffsetGoal;
    private int yOffsetGoal;
    private float opacityGoal;
    private double xOffsetCurrent;
    private double yOffsetCurrent;
    private double xOffsetStep;
    private double yOffsetStep;
    private double opacityStep;

    public double opacity;

    private double animationLength;
    private long lastSystemTime;

    public AnimatedRectangle(int x, int y, int width, int height){
        super(x, y, width, height);
        this.x = x;
        this.y = y;

        originalX = x;
        originalY = y;

        lastSystemTime = System.nanoTime();
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

    public void initAnimate(int xOffsetGoal, int yOffsetGoal, float opacity, long animationLength){
        this.xOffsetGoal = xOffsetGoal;
        this.yOffsetGoal = yOffsetGoal;
        this.opacityGoal = opacity;
        this.animationLength = animationLength;
        this.xOffsetStep = (xOffsetGoal - xOffsetCurrent+0.0) / (animationLength);
        this.yOffsetStep = (yOffsetGoal - yOffsetCurrent+0.0) / (animationLength);
    }
}
