package tetris.gui;

import tetris.GraphicsWrapper;
import tetris.gui.widget.Button;

import java.util.ArrayList;

public class Gui {
    private Gui parentScreen;
    protected ArrayList<Button> buttonList;

    public Gui(Gui parentScreen){
        this.parentScreen = parentScreen;
        buttonList = new ArrayList<>();
    }

    public void draw(GraphicsWrapper g){
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
}
