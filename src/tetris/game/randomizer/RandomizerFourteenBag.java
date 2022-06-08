package tetris.game.randomizer;

import java.util.ArrayList;

public class RandomizerFourteenBag extends Randomizer{
    public RandomizerFourteenBag() {
        bag = new ArrayList<Integer>();
        for (int i = 0; i < 14 ; i++) {
            bag.add(i);
        }
    }
    int getNext() {
        int next = (int)(Math.random()*bag.size());
        int returnVal = bag.get(next)/2+1;
        bag.remove(next);
        return returnVal;
    }
}
