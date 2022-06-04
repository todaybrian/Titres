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
	// Length of animation in nanoseconds
	private final long ANIMATION_LENGTH_HOVER_NS = 180000000;
	private final long ANIMATION_LENGTH_CLICK_NS = 40000000;

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

	//Image of the button
	protected ImageIcon imageIcon;

	//Animation type
	protected AnimationType animationType;
	private boolean isAnimating;
	private int xOffsetGoal;
	private int yOffsetGoal;
	private double xOffsetCurrent;
	private double yOffsetCurrent;
	private double xOffsetStep;
	private double yOffsetStep;
	private long lastSystemTime;

	protected Button.IPressable onPress;
	public Button (int xPos, int yPos, ImageIcon imageIcon, Button.IPressable onPress, AnimationType animationType) {
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = imageIcon.getIconWidth();
		this.height = imageIcon.getIconHeight();
		this.isMouseOver = false;
		this.isClicked = false;

		this.imageIcon = imageIcon;

		this.onPress = onPress;

		this.animationType = animationType;

		xOffsetGoal = 0;
		xOffsetCurrent = 0;
		yOffsetCurrent = 0;
		yOffsetGoal = 0;
		xOffsetStep = yOffsetStep = 0;
		isAnimating = false;
		lastSystemTime = System.nanoTime();
	}

	public Button(int xPos, int yPos, ImageIcon imageIcon, Button.IPressable onPress){
		this(xPos, yPos, imageIcon, onPress, AnimationType.NONE);
	}

	public void draw(GraphicsWrapper g){
		checkHover();
		if(isClicked()){ // Clicked animation
			if(xOffsetGoal != this.animationType.getClickXOffset()){
				isAnimating = true;
				xOffsetGoal = this.animationType.getClickXOffset();
				xOffsetStep = (xOffsetGoal - xOffsetCurrent+0.0) / ANIMATION_LENGTH_CLICK_NS;
			}
			if(yOffsetGoal != this.animationType.getClickYOffset()){
				isAnimating = true;
				yOffsetGoal = this.animationType.getClickYOffset();
				yOffsetStep = (yOffsetGoal - yOffsetCurrent+0.0) / ANIMATION_LENGTH_CLICK_NS;
			}
		} else if(isMouseOver()){
			if(xOffsetGoal != this.animationType.getHoverXOffset()){
				isAnimating = true;
				xOffsetGoal = this.animationType.getHoverXOffset();
				xOffsetStep = (xOffsetGoal - xOffsetCurrent+0.0) / ANIMATION_LENGTH_HOVER_NS;
			}
			if(yOffsetGoal != this.animationType.getHoverYOffset()){
				isAnimating = true;
				yOffsetGoal = this.animationType.getHoverYOffset();
				yOffsetStep = (yOffsetGoal - yOffsetCurrent+0.0) / ANIMATION_LENGTH_HOVER_NS;
			}
		} else{
			if(xOffsetGoal != 0){
				isAnimating = true;
				xOffsetGoal = 0;
				xOffsetStep = (xOffsetGoal - xOffsetCurrent+0.0) / ANIMATION_LENGTH_HOVER_NS;
			}
			if(yOffsetGoal != 0){
				isAnimating = true;
				yOffsetGoal = 0;
				yOffsetStep = (yOffsetGoal - yOffsetCurrent+0.0) / ANIMATION_LENGTH_HOVER_NS;
			}
		}
		if(xOffsetStep != 0 || yOffsetStep != 0){
			boolean toRight = xOffsetGoal > xOffsetCurrent;
			xOffsetCurrent += xOffsetStep * (System.nanoTime() - lastSystemTime);
			if(xOffsetCurrent >= xOffsetGoal && toRight){
				xOffsetCurrent = xOffsetGoal;
				xOffsetStep = 0;
			} else if(xOffsetCurrent <= xOffsetGoal && !toRight){
				xOffsetCurrent = xOffsetGoal;
				xOffsetStep = 0;
			}
			boolean toUp = yOffsetGoal > yOffsetCurrent;
			yOffsetCurrent += yOffsetStep * (System.nanoTime() - lastSystemTime);
			if(yOffsetCurrent >= yOffsetGoal && toUp){
				yOffsetCurrent = yOffsetGoal;
				yOffsetStep = 0;
			} else if(yOffsetCurrent <= yOffsetGoal && !toUp){
				yOffsetCurrent = yOffsetGoal;
				yOffsetStep = 0;
			}
		}
		g.drawImage(imageIcon.getImage(), xPosition + xOffsetCurrent, yPosition + yOffsetCurrent, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		if(isClicked) {
			
		} else if (isMouseOver) {
			g.setColor(new Color(0, 0, 0, 35));
			g.fillRect(xPosition+ xOffsetCurrent, yPosition+yOffsetCurrent, width, height);
		} else{
			g.setColor(new Color(0, 0, 0, 50));
			g.fillRect(xPosition+ xOffsetCurrent, yPosition+yOffsetCurrent, width, height);
		}
		lastSystemTime = System.nanoTime();
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

	public enum AnimationType {
		NONE (0), RIGHT (1), LEFT (2), UP (3), DOWN (4);
		private final int id;

		private final static int[] HOVER_X_OFFSET = {0, 60, -60, 0, 0};
		private final static int[] CLICK_X_OFFSET = {0, 100, -100, 0, 0};
		private final static int[] HOVER_Y_OFFSET = {0, 0, 0, -10, 10};
		private final static int[] CLICK_Y_OFFSET = {0, 0, 0, -30, 30};


		AnimationType(int id){
			this.id = id;
		}
		public int getHoverXOffset(){
			return HOVER_X_OFFSET[id];
		}
		public int getClickXOffset(){
			return CLICK_X_OFFSET[id];
		}
		public int getHoverYOffset(){
			return HOVER_Y_OFFSET[id];
		}
		public int getClickYOffset(){
			return CLICK_Y_OFFSET[id];
		}
	}
}
