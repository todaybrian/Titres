/** Brian Yan, Aaron Zhang
*
* Wrapper class for Buttons
*/
package tetris.gui.widget;

import java.awt.*;

import tetris.GamePanel;
import tetris.util.Assets;
import tetris.controls.MouseInput;

import javax.swing.*;

public class Button extends AnimatedRectangle {
	private GamePanel instance;

	// Length of animation in nanoseconds
	private final long ANIMATION_LENGTH_HOVER = 120000000;
	private final long ANIMATION_LENGTH_CLICK = 40000000;

	//Image of the button
	protected ImageIcon imageIcon;

	private boolean wasHovered;

	protected Button.IPressable onPress;
	public Button (int xPos, int yPos, ImageIcon imageIcon, Button.IPressable onPress, AnimationType animationType) {
		super(xPos, yPos, imageIcon.getIconWidth(), imageIcon.getIconHeight(), animationType);

		this.instance = GamePanel.getGamePanel();

		this.width = imageIcon.getIconWidth();
		this.height = imageIcon.getIconHeight();
		this.wasHovered = false;

		this.imageIcon = imageIcon;

		this.onPress = onPress;

		this.animationType = animationType;

	}

	public Button(int xPos, int yPos, ImageIcon imageIcon, Button.IPressable onPress){
		this(xPos, yPos, imageIcon, onPress, AnimationType.NONE);
	}

	public void draw(Graphics2D g){
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		checkHover();

		if(!wasHovered && isMouseOver){
			instance.getSFXPlayer().loadMusic(Assets.SFX.HOVER);
			instance.getSFXPlayer().playMusic();
		}
		wasHovered = isMouseOver;

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

		g.drawImage(imageIcon.getImage(), (int)x, (int)y, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
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


	public void clicked(){
		//Stop buttons from being clicked when they are in the middle of a menu switch animation
		//Otherwise call the interface function to handle the button press
		if(!inTransition)
			onPress.onPress(this);
	}

	public interface IPressable {
		void onPress(Button p_onPress_1_);
	}

	public double getValue() {
		return -1; // This is b/c I need to declare functions in parent so that child class can use them
	}

}
