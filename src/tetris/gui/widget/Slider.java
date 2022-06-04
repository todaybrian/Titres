package tetris.gui.widget;

import tetris.GamePanel;
import tetris.controls.MouseInput;
import tetris.wrapper.GraphicsWrapper;

import javax.swing.*;
import java.awt.*;

public class Slider extends Button{

    protected int minX;
    protected ImageIcon icon;

    public Slider(int xPos, int yPos, ImageIcon imageIcon, int posX, int width) {
        super(xPos, yPos, imageIcon, (click)->{});
        minX = posX;
        this.width = width;
        icon = imageIcon;
    }
    public void draw(GraphicsWrapper g) {
        checkHover();
        if (isClicked()) {
            if (MouseInput.getLocation().getX()+icon.getIconWidth() > width+minX) {
                this.xPosition = width+minX -icon.getIconWidth();
            } else if (MouseInput.getLocation().getX() < minX) {
                this.xPosition = minX;
            } else {
                this.xPosition = (int) MouseInput.getLocation().getX();
            }
        }
        g.setColor(new Color(0, 0, 0, 200));
        int offsetY = 20;
        g.fillRect(minX, yPosition+offsetY, width, height-2*offsetY);
        g.drawImage(icon.getImage(), xPosition, yPosition, icon.getIconWidth(), icon.getIconHeight());
    }

    public double getValue() {
        return (double)(xPosition-minX) / (width-icon.getIconWidth());
    }

}
