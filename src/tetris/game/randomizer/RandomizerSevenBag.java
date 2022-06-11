package tetris.game.randomizer;

import java.util.Collections;

public class RandomizerSevenBag extends Randomizer{
    @Override
    protected void fillBag() {
        Collections.shuffle(allPieces);
        bag.addAll(allPieces);
    }
}
