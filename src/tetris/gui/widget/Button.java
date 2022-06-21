/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Class for buttons.
 */
package tetris.gui.widget;

import java.awt.*;

import tetris.GamePanel;
import tetris.util.Assets;

public class Button extends AnimatedRectangle {
	private GamePanel instance;

	// Length of animations in nanoseconds, self-explanatory
	private final double ANIMATION_LENGTH_HOVER = 0.12;
	private final double ANIMATION_LENGTH_CLICK = 0.04;

	//Image of the button
	protected Image image;

	//Variable for if the button was hovered over in the previous frame
	private boolean wasHovered;

	//Interface for the button to call when it is clicked
	protected Button.IPressable onPress;

	public Button (int xPos, int yPos, Image image, Button.IPressable onPress, AnimationType animationType) {
		super(xPos, yPos, image.getWidth(null), image.getHeight(null), animationType);

		this.instance = GamePanel.getGamePanel();

		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
		this.wasHovered = false;

		this.image = image;

		this.onPress = onPress;

		this.animationType = animationType;

	}

  	//Constructor for buttons that are not animated
	public Button(int xPos, int yPos, Image image, Button.IPressable onPress){
		this(xPos, yPos, image, onPress, AnimationType.NONE);
	}

	public void draw(Graphics2D g){
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		checkHover();

		if(!wasHovered && isMouseOver){
			instance.getSFXPlayer().play(Assets.SFX.HOVER.get());
		}
		wasHovered = isMouseOver;

		if(!inTransition) {
			double animationLength = ANIMATION_LENGTH_HOVER;
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

		g.drawImage(image, (int)x, (int)y, image.getWidth(null), image.getHeight(null), null);
		if(isClicked) {
			
		} else if (isMouseOver) {
			g.setColor(new Color(0, 0, 0, 35));
			g.fillRect((int)x, (int)y, width, height);
		} else{
			g.setColor(new Color(0, 0, 0, 50));
			g.fillRect((int)x, (int)y, width, height);
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	//Called if the button was clicked
	public void clicked(){
		if(!inTransition) //Stop buttons from being clicked when they are in the middle of a menu switch animation
			onPress.onPress(this); //call the interface function to handle the button press
	}

	// Interface to allow code to be run when button is clicked
	public interface IPressable {
		void onPress(Button p_onPress_1_);
	}

}
