package tetris.game.randomizer;

import java.util.ArrayList;

public abstract class Randomizer {
    ArrayList<Integer> bag;
    public Randomizer() {

    }
    abstract int getNext();
}
