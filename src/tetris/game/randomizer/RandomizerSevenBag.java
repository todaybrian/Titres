package tetris.game.randomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class RandomizerSevenBag extends Randomizer{
    private LinkedList<Integer> bag;
    public RandomizerSevenBag() {
        bag = new LinkedList<Integer>();
    }
    public int getNext() {
        if (bag.size() == 0) {
            for (int i = 0; i < 7 ; i++) {
                bag.add(i);
            }
            Collections.shuffle(bag);
        }
        int out = bag.getFirst();
        bag.removeFirst();
        return out;
    }
}
