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
		super(xPos, yPos, image.getWidth(null), image.getHeight(null), animationType); //Call super constructor

		this.instance = GamePanel.getGamePanel(); //Get the instance of the game panel

		//Store width and height
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);

		//Button is not hovered over initially
		this.wasHovered = false;

		//Store button image
		this.image = image;

		//Store onPress interface for button functionality
		this.onPress = onPress;

		//Store the animation type
		this.animationType = animationType;
	}

  	//Constructor for buttons that are not animated
	public Button(int xPos, int yPos, Image image, Button.IPressable onPress){
		this(xPos, yPos, image, onPress, AnimationType.NONE);
	}

	/**
	 * Draws the button to the screen.
	 *
	 * @param g The graphics object to draw on.
	 */
	public void draw(Graphics2D g){
		double animationLength=0.1; //Length of the animation in nanoseconds for hovering and clicking

		//Set the opacity of the button, based on if it is hovered over or not
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

		checkHover(); //Check if the button is being hovered over

		if(!wasHovered && isMouseOver){ //If the button was not hovered over and is now hovered over
			instance.getSFXPlayer().play(Assets.SFX.HOVER.get()); //Play the hover sound
		}
		wasHovered = isMouseOver; //Set the wasHovered variable to the current isMouseOver variable for the next frame

		//If the button is not in a menu transition (so hover and click animations don't work while in a menu transition)
		if(!inTransition) {
			int xOffsetGoal = 0, yOffsetGoal = 0, opacityGoal = 1;
			if (isClicked()) { // Clicked animation
				// Get offsets and animation lengths for the animation
				xOffsetGoal = this.animationType.getClickXOffset();
				yOffsetGoal = this.animationType.getClickYOffset();

				animationLength = ANIMATION_LENGTH_CLICK;
			} else if (isMouseOver()) { // Hover animation
				// Get offsets and animation lengths for the animation

				xOffsetGoal = this.animationType.getHoverXOffset();
				yOffsetGoal = this.animationType.getHoverYOffset();

				animationLength = ANIMATION_LENGTH_HOVER;
			}
			// Initialize animation using the offsets and animation lengths
			super.initAnimate(xOffsetGoal, yOffsetGoal, opacityGoal, animationLength);
		}
		super.animate(); //"Animate" the button by moving the coordinates

		//Draw the button
		g.drawImage(image, (int)x, (int)y, image.getWidth(null), image.getHeight(null), null);

		// Darken button if it is hovering over it, darken even more if it is not clicked
		// This is so hovering and clicking the button has a brightening effect
		if(!isClicked) {
			if (isMouseOver) {
				g.setColor(new Color(0, 0, 0, 35));
			} else{
				g.setColor(new Color(0, 0, 0, 50));
			}
			g.fillRect((int)x, (int)y, width, height);
		}

		//Reset the opacity to 100% for other components
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
