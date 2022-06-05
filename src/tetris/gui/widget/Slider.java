package tetris.gui.widget;

import tetris.GamePanel;
import tetris.controls.MouseInput;
import tetris.wrapper.GraphicsWrapper;

import javax.swing.*;
import java.awt.*;

public class Slider extends Button{

    protected int minX;
    protected ImageIcon icon;

    public Slider(int xPos, int yPos, ImageIcon imageIcon, int xMin, int width) {
        super(xPos, yPos, imageIcon, (click)->{});

        minX = xMin;
        this.width = width;
        icon = imageIcon;
    }
    public void draw(GraphicsWrapper g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        super.animate();
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
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

    }

    public double getValue() {
        return (x-minX) / (width-icon.getIconWidth());
    }

}
