package tetris.game.randomizer;

import java.util.ArrayList;

public class RandomizerSevenBag extends Randomizer{
    public RandomizerSevenBag() {
        bag = new ArrayList<Integer>();
        for (int i = 0; i < 7 ; i++) {
            bag.add(i);
        }

    }
    public int getNext() {
        if (bag.size() == 0) {
            for (int i = 0; i < 7 ; i++) {
                bag.add(i);
            }
        }
        int next = (int)(Math.random()*bag.size());
        int returnVal = bag.get(next);
        bag.remove(next);
        return returnVal;

    }
}
