package tetris.gui.widget;

import tetris.GamePanel;
import tetris.controls.MouseInput;
import tetris.wrapper.GraphicsWrapper;

import javax.swing.*;
import java.awt.*;

public class Slider extends Button{

    protected int xPos;
    protected int minValue, maxValue;
    protected ImageIcon icon;

    public Slider(int xPos, int yPos, int width, ImageIcon imageIcon, int minValue, int maxValue, int initValue) {
        super(((initValue-minValue)/(maxValue-minValue))*(width-imageIcon.getIconWidth())+xPos, yPos, imageIcon, (click)->{});

        this.xPos = xPos;
        this.width = width;
        this.icon = imageIcon;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public Slider(int xPos, int yPos, int width, ImageIcon imageIcon, int minValue, int maxValue) {
        this(xPos, yPos, width, imageIcon, minValue, maxValue, 0);
    }

    public void draw(GraphicsWrapper g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        checkHover();
        if (isClicked()) {
            if (MouseInput.getLocation().getX()+icon.getIconWidth() > width+xPos) {
                x = width+xPos -icon.getIconWidth();
            } else if (MouseInput.getLocation().getX() < xPos) {
                x = xPos;
            } else {
                x = (int) MouseInput.getLocation().getX();
            }
        }
        g.setColor(new Color(0, 0, 0, 200));
        int offsetY = 20;
        g.fillRect(xPos, y+offsetY, width, height-2*offsetY);
        g.drawImage(icon.getImage(), x, y, icon.getIconWidth(), icon.getIconHeight());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

    }

    public double getValue() {
        return ((x-xPos) / (width-icon.getIconWidth())) * (maxValue-minValue) + minValue;
    }

}
