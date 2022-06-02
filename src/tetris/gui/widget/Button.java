* Brian Yan, Aaron Zhang
		*
		* Wrapper class for Buttons
		*/
		package tetris.gui.widget;

		import java.awt.Color;

		import tetris.GraphicsWrapper;
		import tetris.controls.MouseInput;

public class Button {
	boolean hover;
	public int x, y, width, height;
	public Button (int xPos, int yPos, int w, int h){
		x = xPos;
		y = yPos;
		width = w;
		height = h;
		hover = false;
	}

	public void draw(GraphicsWrapper g){
		if (!hover) {
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawString("I am a button!!!", x, y);
		} else {
			g.setColor(Color.PINK);
			g.fillRect(x-25, y, width, height);
			g.setColor(Color.BLACK);
			g.drawString("I AM BEING HOVERED!!!", x, y);
		}
	}

	public void checkCollision() {
		if ((MouseInput.getLocation().getX() > x && MouseInput.getLocation().getX() < x+width) && (MouseInput.getLocation().getY() > y && MouseInput.getLocation().getY() < y+height)) {
			hover = true;
		} else if (hover && ((MouseInput.getLocation().getX() < x-25 || MouseInput.getLocation().getX() > x+width-25) || (MouseInput.getLocation().getY() < 600 || MouseInput.getLocation().getY() > 700))) {
			hover = false;
		}
	}

}
