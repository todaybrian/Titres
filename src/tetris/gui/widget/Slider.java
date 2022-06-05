package tetris.gui.widget;

import tetris.GamePanel;
import tetris.controls.MouseInput;
import tetris.wrapper.GraphicsWrapper;

import javax.swing.*;
import java.awt.*;

public class Slider extends Button{

    protected int minX;
    protected ImageIcon icon;

    public Slider(int xPos, int yPos, ImageIcon imageIcon, int width) {
        super(xPos, yPos, imageIcon, (click)->{});

        minX = xPos;
        this.width = width;
        icon = imageIcon;
    }
    public void draw(GraphicsWrapper g) {
        checkHover();
        if (isClicked()) {
            if (MouseInput.getLocation().getX()+icon.getIconWidth() > width+minX) {
                x = width+minX -icon.getIconWidth();
            } else if (MouseInput.getLocation().getX() < minX) {
                x = minX;
            } else {
                x = (int) MouseInput.getLocation().getX();
            }
        }
        g.setColor(new Color(0, 0, 0, 200));
        int offsetY = 20;
        g.fillRect(minX, y+offsetY, width, height-2*offsetY);
        g.drawImage(icon.getImage(), x, y, icon.getIconWidth(), icon.getIconHeight());
    }

    public double getValue() {
        return (double)(x-minX) / (width-icon.getIconWidth());
    }

}
