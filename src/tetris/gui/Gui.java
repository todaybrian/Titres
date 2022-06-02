package tetris.gui;

import tetris.GraphicsWrapper;
import tetris.gui.widget.Button;

import java.util.ArrayList;

public class Gui {
    private Gui parentScreen;
    private ArrayList<Button> buttonList;

    public Gui(Gui parentScreen){
        this.parentScreen = parentScreen;
        buttonList = new ArrayList<>();
    }

    public void draw(GraphicsWrapper g){
        for(Button button : buttonList){
            button.draw(g);
        }


    }
}
