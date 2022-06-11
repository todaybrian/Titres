package tetris.game.randomizer;

import tetris.game.PieceType;

import java.util.*;

public abstract class Randomizer {
    protected List<PieceType> allPieces;
    protected Queue<PieceType> bag;

    public Randomizer(){
        allPieces = Arrays.asList(PieceType.I, PieceType.J, PieceType.L, PieceType.O, PieceType.S, PieceType.T, PieceType.Z);
        bag = new LinkedList<>();
    }

    protected abstract void fillBag();

    public ArrayList<PieceType> getNextPieces(int amount){
        while(bag.size()<amount){
            fillBag();
        }
        ArrayList<PieceType> pieces = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            pieces.add(getNextPiece());
        }
        return pieces;
    }

    public PieceType getNextPiece(){
        if(bag.isEmpty()){
            fillBag();
        }
        return bag.remove();
    }

}
