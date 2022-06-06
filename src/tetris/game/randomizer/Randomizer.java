package tetris.game.randomizer;

import tetris.game.Tetromino;

import java.awt.*;

public abstract class Randomizer {
    protected String[] list =  {"T", "L","BL","I","O","squigglyLeft","squigglyRight"};
    public Randomizer() {

    }
    abstract Image getNext();
}
