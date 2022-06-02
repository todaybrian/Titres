/** Brian Yan, Aaron Zhang
*
* Wrapper class for Buttons
*/
package tetris.gui.widget;

import java.awt.Color;

import tetris.GraphicsWrapper;
import tetris.controls.MouseInput;

public class Button {
	//id of the button
	protected int id;

	protected boolean hover;

	// Button width
	protected int width;

	//Button height
	protected int height;

	//The x position of the button
	protected int xPosition;

	// The y position of the button
	protected int yPosition;


	public Button (int buttonId, int xPos, int yPos, int w, int h){
		this.id = buttonId;
		xPosition = xPos;
		yPosition = yPos;
		width = w;
		height = h;
		hover = false;
	}

	public void draw(GraphicsWrapper g){
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

	public void checkCollision() {
		if ((MouseInput.getLocation().getX() > xPosition && MouseInput.getLocation().getX() < xPosition +width) && (MouseInput.getLocation().getY() > yPosition && MouseInput.getLocation().getY() < yPosition +height)) {
			hover = true;
		} else if (hover && ((MouseInput.getLocation().getX() < xPosition -25 || MouseInput.getLocation().getX() > xPosition +width-25) || (MouseInput.getLocation().getY() < 600 || MouseInput.getLocation().getY() > 700))) {
			hover = false;
		}
	}

	public int getId(){
		return this.id;
	}

}
