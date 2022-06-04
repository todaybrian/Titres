package tetris.gui.widget;

import tetris.GamePanel;
import tetris.controls.MouseInput;
import tetris.wrapper.GraphicsWrapper;

import javax.swing.*;

public class Slider extends Button{

    protected int minX;
    protected int maxX;
    protected ImageIcon icon;

    public Slider(int xPos, int yPos, ImageIcon imageIcon, int min, int max) {
        super(xPos, yPos, imageIcon, (click)->{});
        minX = min;
        maxX = max;
        icon = imageIcon;
    }
    public void draw(GraphicsWrapper g) {
        checkHover();
        if (isClicked()) {
            if (MouseInput.getLocation().getX() > maxX) {
                this.xPosition = maxX;
            } else if (MouseInput.getLocation().getX() < minX) {
                this.xPosition = minX;
            } else {
                this.xPosition = (int) MouseInput.getLocation().getX();
            }
        }
        g.drawImage(icon.getImage(), xPosition, yPosition, icon.getIconWidth(), icon.getIconHeight());
    }

    public double getValue() {
        return (double)(xPosition-minX) / (maxX-minX);
    }

}
