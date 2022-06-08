package tetris.gui.widget;

import tetris.GamePanel;
import tetris.controls.MouseInput;

import javax.swing.*;
import java.awt.*;

public class Slider extends Button{

    protected int xPos;
    protected int minValue, maxValue;
    protected ImageIcon icon;
    protected IPressable onChange;

    public Slider(int xPos, int yPos, int width, ImageIcon imageIcon, IPressable onChange, int minValue, int maxValue, int initValue) {
        super((int)(((initValue-minValue+0.0)/(maxValue-minValue))*(width-imageIcon.getIconWidth()))+xPos, yPos, imageIcon, (click)->{});

        this.xPos = xPos;
        this.width = width;
        this.icon = imageIcon;
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.onChange = onChange;
    }

    public Slider(int xPos, int yPos, int width, ImageIcon imageIcon, IPressable onChange, int minValue, int maxValue) {
        this(xPos, yPos, width, imageIcon, onChange, minValue, maxValue, 0);
    }

    public void draw(Graphics2D g) {
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
            this.onChange.onChange(this);
        }
        g.setColor(new Color(0, 0, 0, 200));
        int offsetY = 20;
        g.fillRect(xPos, (int) (y+offsetY), width, height-2*offsetY);
        g.drawImage(icon.getImage(), (int) x, (int) y, icon.getIconWidth(), icon.getIconHeight(), null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

    }

    public double getValue() {
        return ((x-xPos) / (width-icon.getIconWidth())) * (maxValue-minValue) + minValue;
    }
    public interface IPressable {
        void onChange(Slider onSlide);
    }

}
