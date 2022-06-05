package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.AnimatedRectangle;
import tetris.wrapper.GraphicsWrapper;
import tetris.gui.widget.Button;

import java.util.ArrayList;

public class Gui {
    protected Gui parentScreen;

    protected ArrayList<AnimatedRectangle> componentList;
    protected ArrayList<Button> buttonList;
    protected GamePanel instance;

    public Gui(Gui parentScreen){
        this.parentScreen = parentScreen;
        buttonList = new ArrayList<>();
        componentList = new ArrayList<>();
        instance = GamePanel.getGamePanel();
    }

    public void draw(GraphicsWrapper g){
        instance.getGameBackground().draw(g);
        for (AnimatedRectangle component : componentList) {
            component.draw(g);
        }
        for(AnimatedRectangle component : componentList){
            component.draw(g);
        }
        for(Button button : buttonList){
            button.draw(g);
        }
    }

    public void mouseClicked(){
        //TODO: Check if mouse is above each button and run button interface if it is clicked
        for(Button button: buttonList){
            if(button.isMouseOver())
                button.setClicked(true);
        }
    }

    public void mouseReleased(){
        for(Button button: buttonList){
            if(button.isClicked() && button.isMouseOver())
                button.clicked();
            button.setClicked(false);
        }
    }

    public ArrayList<Button> getButtonList(){
        return buttonList;
    }

    public ArrayList<AnimatedRectangle> getComponentList(){
        return componentList;
    }
}
