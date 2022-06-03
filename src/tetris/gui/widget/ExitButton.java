/** Brian Yan, Aaron Zhang
 *
 * Wrapper class for Buttons
 */
package tetris.gui.widget;

import java.awt.*;

import tetris.GraphicsWrapper;
import tetris.util.Assets;

public class ExitButton extends Button {
    public ExitButton(int xPos, int yPos, int w, int h, IPressable onPress) {
        super(xPos, yPos, w, h, onPress);
    }

    @Override
    public void draw(GraphicsWrapper g) {
        checkHover();
//        if (!isMouseOver) {
            g.setColor(new Color(255,89,89));
            g.fillRect(xPosition, yPosition, width, height);
            g.setColor(Color.BLACK);
            g.setFont(Assets.SIGNIKA_FONT.deriveFont(Font.PLAIN,25));
            g.drawString("Exit Game", 500, yPosition+height/2);
//        } else {
//            g.setColor(new Color(255,121,121));
//            g.fillRect(xPosition-25, yPosition, width, height);
//        }
    }
}