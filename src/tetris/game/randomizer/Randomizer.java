/**
 * This is an abstract class for a Tetris bag randomizer.
 * A bag randomizer is a randomizer that randomly generates a bag of pieces.
 *
 * This class should be extended for different randomizers.
 * In this game, there is only one randomizer: RandomizerSevenBag.
 *
 * In the future, if we want to add more randomizers, we can extend this class easily.
 */
package tetris.game.randomizer;

import tetris.game.PieceType;

import java.util.*;

public abstract class Randomizer {
    //A list of all the pieces in the game.
    protected List<PieceType> allPieces;

    //The bag of pieces.
    protected Queue<PieceType> bag;

    // Constructor class for Randomizer.
    // Initializes the above variables.
    public Randomizer(){
        //Initialize the list of all pieces.
        allPieces = Arrays.asList(PieceType.I, PieceType.J, PieceType.L, PieceType.O, PieceType.S, PieceType.T, PieceType.Z);

        //Initialize the bag.
        bag = new LinkedList<>();
    }

    //Called when the bag does not have enough pieces.
    protected abstract void fillBag();

    /**
     * Return an ArrayList of PieceType objects. Used to show the user the next few pieces.
     *
     * @param amount The number of pieces to be returned.
     * @return ArrayList of PieceType objects (size = amount requested)
     */
    public ArrayList<PieceType> getNextPieces(int amount){
        //If there are not enough pieces in the bag, fill the bag.
        while(bag.size()<amount){
            fillBag();
        }
        //Iterator to iterate through the queue
        Iterator<PieceType> it = bag.iterator();

        //ArrayList to store the pieces returned
        ArrayList<PieceType> pieces = new ArrayList<>();

        //Add the pieces to the arraylist
        for (int i = 0; i < amount; i++) {
            pieces.add(it.next());
        }
        return pieces; //Return the arraylist
    }

    /**
     * Return the next piece in the bag and pops it from the bag.
     * Called to spawn a new piece.
     *
     * @return The next piece in the bag.
     */
    public PieceType popNextPiece(){
        // If there are not enough pieces in the bag, fill the bag.
        if(bag.isEmpty()){
            fillBag();
        }
        return bag.remove(); // Remove and return the next piece in the bag.
    }

}
