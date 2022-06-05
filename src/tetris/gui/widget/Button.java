/** Brian Yan, Aaron Zhang
*
* Wrapper class for Buttons
*/
package tetris.gui.widget;

import java.awt.*;

import tetris.wrapper.GraphicsWrapper;
import tetris.controls.MouseInput;

import javax.swing.*;

public class Button extends AnimatedRectangle {
	// Length of animation in nanoseconds
	private final long ANIMATION_LENGTH_HOVER = 120000000;
	private final long ANIMATION_LENGTH_CLICK = 40000000;

	//Is the cursor over the button?
	protected boolean isMouseOver;

	//Was the left button of mouse pressed (but not released) on this button?
	private boolean isClicked;


	// Button width
	protected int width;

	//Button height
	protected int height;

	//Image of the button
	protected ImageIcon imageIcon;

	protected Button.IPressable onPress;
	public Button (int xPos, int yPos, ImageIcon imageIcon, Button.IPressable onPress, AnimationType animationType) {
		super(xPos, yPos, imageIcon.getIconWidth(), imageIcon.getIconHeight(), animationType);

		this.width = imageIcon.getIconWidth();
		this.height = imageIcon.getIconHeight();
		this.isMouseOver = false;
		this.isClicked = false;

		this.imageIcon = imageIcon;

		this.onPress = onPress;

		this.animationType = animationType;
	}

	public Button(int xPos, int yPos, ImageIcon imageIcon, Button.IPressable onPress){
		this(xPos, yPos, imageIcon, onPress, AnimationType.NONE);
	}

	public void draw(GraphicsWrapper g){
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		checkHover();
		if(!inTransition) {
			long animationLength = ANIMATION_LENGTH_HOVER;
			int xOffsetGoal = 0, yOffsetGoal = 0, opacityGoal = 1;
			if (isClicked()) { // Clicked animation
				xOffsetGoal = this.animationType.getClickXOffset();
				yOffsetGoal = this.animationType.getClickYOffset();
				animationLength = ANIMATION_LENGTH_CLICK;
			} else if (isMouseOver()) {
				xOffsetGoal = this.animationType.getHoverXOffset();
				yOffsetGoal = this.animationType.getHoverYOffset();
			}
			super.initAnimate(xOffsetGoal, yOffsetGoal, opacityGoal, animationLength);
		}
		super.animate();

		g.drawImage(imageIcon.getImage(), x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		if(isClicked) {
			
		} else if (isMouseOver) {
			g.setColor(new Color(0, 0, 0, 35));
			g.fillRect(x, y, width, height);
		} else{
			g.setColor(new Color(0, 0, 0, 50));
			g.fillRect(x, y, width, height);
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	protected void checkHover() {
		isMouseOver = (MouseInput.getLocation().getX() > x && MouseInput.getLocation().getX() < x +width) && (MouseInput.getLocation().getY() > y && MouseInput.getLocation().getY() < y +height);
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

	public double getValue() {
		return -1; // This is b/c I need to declare functions in parent so that child class can use them
	}

}
