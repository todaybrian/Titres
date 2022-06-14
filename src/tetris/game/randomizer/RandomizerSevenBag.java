/**
 * This is a 7-bag randomizer for the Tetris game.
 * A 7-bag randomizer shuffles a bag of the 7 tetrominoes
 */
package tetris.game.randomizer;

import java.util.Collections;

public class RandomizerSevenBag extends Randomizer{

    //Fill the bag with the 7 pieces.
    @Override
    protected void fillBag() {
        Collections.shuffle(allPieces); //Shuffle the list of all pieces.
        bag.addAll(allPieces); //Add all shuffled pieces to the bag.
    }
}
