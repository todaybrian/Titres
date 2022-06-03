/** Brian Yan, Aaron Zhang
*
* Wrapper class for Buttons
*/
package tetris.gui.widget;

import java.awt.*;

import tetris.GraphicsWrapper;
import tetris.controls.MouseInput;

public class Button extends Rectangle {
	//Is the cursor over the button?
	protected boolean isMouseOver;

	//Was the left button of mouse pressed (but not released) on this button?
	private boolean isClicked;

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
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = w;
		this.height = h;
		this.isMouseOver = false;
		this.isClicked = false;
		this.onPress = onPress;
	}

	public void draw(GraphicsWrapper g){
		checkHover();
		if (!isMouseOver) {
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

	protected void checkHover() {
		isMouseOver = (MouseInput.getLocation().getX() > xPosition && MouseInput.getLocation().getX() < xPosition +width) && (MouseInput.getLocation().getY() > yPosition && MouseInput.getLocation().getY() < yPosition +height);
	}

	public void clicked(){
		onPress.onPress(this);
	}

	public boolean isMouseOver() {
		return isMouseOver;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public interface IPressable {
		void onPress(Button p_onPress_1_);
	}
}
