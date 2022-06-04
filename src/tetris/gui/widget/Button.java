/** Brian Yan, Aaron Zhang
*
* Wrapper class for Buttons
*/
package tetris.gui.widget;

import java.awt.*;

import tetris.wrapper.GraphicsWrapper;
import tetris.controls.MouseInput;

import javax.swing.*;

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

	protected ImageIcon imageIcon;

	protected Button.IPressable onPress;
	public Button (int xPos, int yPos, ImageIcon imageIcon, Button.IPressable onPress){
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = imageIcon.getIconWidth();
		this.height = imageIcon.getIconHeight();
		this.isMouseOver = false;
		this.isClicked = false;

		this.imageIcon = imageIcon;

		this.onPress = onPress;
	}

	public void draw(GraphicsWrapper g){
		checkHover();
		g.drawImage(imageIcon.getImage(), xPosition, yPosition, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		if(isClicked) {
			
		} else if (isMouseOver) {
			g.setColor(new Color(0, 0, 0, 35));
			g.fillRect(xPosition, yPosition, width, height);
		} else{
			g.setColor(new Color(0, 0, 0, 50));
			g.fillRect(xPosition, yPosition, width, height);

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
