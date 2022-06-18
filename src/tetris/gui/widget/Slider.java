package tetris.gui.widget;

import tetris.GamePanel;
import tetris.controls.MouseInput;

import javax.swing.*;
import java.awt.*;

public class Slider extends Button{

    protected int xPos;

    protected double xSlider;
    protected int minValue, maxValue;
    protected Image icon;
    protected IPressable onChange;

    public Slider(int xPos, int yPos, int width, Image image, IPressable onChange, int minValue, int maxValue, int initValue) {
        super(xPos, yPos, image, (click)->{});

        this.xPos = xPos;
        this.width = width;
        this.icon = image;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.xSlider = (((initValue-minValue+0.0)/(maxValue-minValue))*(width-image.getWidth(null)))+xPos;
        this.onChange = onChange;
    }

    public Slider(int xPos, int yPos, int width, Image image, IPressable onChange, int minValue, int maxValue) {
        this(xPos, yPos, width, image, onChange, minValue, maxValue, 0);
    }

    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        checkHover();
        if (isClicked()) {
            if (MouseInput.getLocation().getX()+icon.getWidth(null) > width+xPos) {
                xSlider = width+xPos -icon.getWidth(null);
            } else if (MouseInput.getLocation().getX() < xPos) {
                xSlider = xPos;
            } else {
                xSlider = (int) MouseInput.getLocation().getX() - icon.getWidth(null)/2;
            }
            this.onChange.onChange(this);
        }
        g.setColor(new Color(0, 0, 0, 200));
        int offsetY = 20;
        g.fillRect(xPos, (int) (y+offsetY), width, height-2*offsetY);
        g.drawImage(icon, (int) xSlider, (int) y, icon.getWidth(null), icon.getHeight(null), null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

    }

    public double getValue() {
        return ((xSlider-xPos) / (width-icon.getWidth(null))) * (maxValue-minValue) + minValue;
    }
    public interface IPressable {
        void onChange(Slider onSlide);
    }


}
