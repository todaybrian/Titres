/** Brian Yan, Aaron Zhang
*
* Wrapper class for Buttons
*/
package tetris.gui.widget;

import java.awt.Color;

import tetris.GraphicsWrapper;
import tetris.controls.MouseInput;

public class Button {
	//TODO: public bc testing
	public boolean hover;

	// Button width
	protected int width;

	//Button height
	protected int height;

	//The x position of the button
	protected int xPosition;

	// The y position of the button
	protected int yPosition;

	protected Button.IPressable onPress;
	public Button (int xPos, int yPos, int w, int h, Button.IPressable onPress){
		xPosition = xPos;
		yPosition = yPos;
		width = w;
		height = h;
		hover = false;
		this.onPress = onPress;
	}

	public void draw(GraphicsWrapper g){
		checkHover();
		if (!hover) {
			g.setColor(Color.YELLOW);
			g.fillRect(xPosition, yPosition, width, height);
			g.setColor(Color.BLACK);
			g.drawString("I am a button!!!", xPosition, yPosition);
		} else {
			g.setColor(Color.PINK);
			g.fillRect(xPosition -25, yPosition, width, height);
			g.setColor(Color.BLACK);
			g.drawString("I AM BEING HOVERED!!!", xPosition, yPosition);
		}
	}

	public void checkHover() {
		if ((MouseInput.getLocation().getX() > xPosition && MouseInput.getLocation().getX() < xPosition +width) && (MouseInput.getLocation().getY() > yPosition && MouseInput.getLocation().getY() < yPosition +height)) {
			hover = true;
		} else if (hover && ((MouseInput.getLocation().getX() < xPosition -25 || MouseInput.getLocation().getX() > xPosition +width-25) || (MouseInput.getLocation().getY() < 600 || MouseInput.getLocation().getY() > 700))) {
			hover = false;
		}
	}

	public void clicked(){
		onPress.onPress(this);
	}

	public interface IPressable {
		void onPress(Button p_onPress_1_);
	}
}
