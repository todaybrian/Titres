/** Brian Yan, Aaron Zhang
 *
 * Wrapper class for Buttons
 */
package tetris.gui.widget;

import java.awt.Color;

import tetris.GraphicsWrapper;
import tetris.controls.MouseInput;

public class ExitButton extends Button {
    @Override
    public void draw(GraphicsWrapper g) {
        checkHover();
        if (!isMouseOver) {
            g.setColor(Color(255,89,89));
            g.fillRect(xPosition, yPosition, width, height);
            g.setColor(Color.BLACK);
            g.drawString("Exit Game", xPosition, yPosition);
        } else {
            g.setColor(Color(255,121,121));
            g.fillRect(xPosition-25, yPosition, width, height);
        }
    }
}