/**
 * This is a 7-bag randomizer for the Tetris game.
 * A 7-bag randomizer shuffles a bag of the 7 tetrominoes
 */
package tetris.game.randomizer;

import java.util.Collections;

public class RandomizerSevenBag extends Randomizer{

    //Shuffle all 7 pieces and add them to the bag.
    @Override
    protected void fillBag() {
        Collections.shuffle(allPieces);
        bag.addAll(allPieces);
    }
}
