package tetris.game;

import java.util.Arrays;

public enum PieceType {
    J(0), Z(1), S(2), L(3), T(4), O(5), I(6), BORDER, GHOST, NULL;

    private int id;
    private static PieceType[][][] pieceGrid;

    PieceType(int id) {
        this.id = id;
    }

    PieceType() {
        this.id = -1;
    }

    static{
        pieceGrid = new PieceType[7][][];

        //J
        pieceGrid[0] = new PieceType[3][];
        pieceGrid[0][0] = new PieceType[]{PieceType.J, PieceType.NULL, PieceType.NULL};
        pieceGrid[0][1] = new PieceType[]{PieceType.J, PieceType.J, PieceType.J};
        pieceGrid[0][2] = new PieceType[]{PieceType.NULL, PieceType.NULL, PieceType.NULL};

        //Z
        pieceGrid[1] = new PieceType[3][];
        pieceGrid[1][0] = new PieceType[]{PieceType.Z, PieceType.Z, PieceType.NULL};
        pieceGrid[1][1] = new PieceType[]{PieceType.NULL, PieceType.Z, PieceType.Z};
        pieceGrid[1][2] = new PieceType[]{PieceType.NULL, PieceType.NULL, PieceType.NULL};
        //S
        pieceGrid[2] = new PieceType[3][];
        pieceGrid[2][0] = new PieceType[]{PieceType.S, PieceType.S, PieceType.NULL};
        pieceGrid[2][1] = new PieceType[]{PieceType.NULL, PieceType.S, PieceType.S};
        pieceGrid[2][2] = new PieceType[]{PieceType.NULL, PieceType.NULL, PieceType.NULL};

        //L
        pieceGrid[3] = new PieceType[3][];
        pieceGrid[3][0] = new PieceType[]{PieceType.NULL, PieceType.NULL, PieceType.L};
        pieceGrid[3][1] = new PieceType[]{PieceType.L, PieceType.L, PieceType.L};
        pieceGrid[3][2] = new PieceType[]{PieceType.NULL, PieceType.NULL, PieceType.NULL};

        //T
        pieceGrid[4] = new PieceType[3][];
        pieceGrid[4][0] = new PieceType[]{PieceType.NULL, PieceType.T, PieceType.NULL};
        pieceGrid[4][1] = new PieceType[]{PieceType.T, PieceType.T, PieceType.T};
        pieceGrid[4][2] = new PieceType[]{PieceType.NULL, PieceType.NULL, PieceType.NULL};

        //O
        pieceGrid[5] = new PieceType[3][];
        pieceGrid[5][0] = pieceGrid[5][1] = new PieceType[]{PieceType.O, PieceType.O, PieceType.NULL};
        pieceGrid[5][2] = new PieceType[]{PieceType.NULL, PieceType.NULL, PieceType.NULL};

        //I
        pieceGrid[6] = new PieceType[4][];
        pieceGrid[6][0] = pieceGrid[6][2] = pieceGrid[6][3] = new PieceType[]{PieceType.NULL, PieceType.NULL, PieceType.NULL, PieceType.NULL};
        pieceGrid[6][1] = new PieceType[]{PieceType.I, PieceType.I, PieceType.I, PieceType.I};
    }

    public static PieceType[][] getPieceGrid(PieceType pieceType){
        return pieceGrid[pieceType.id];
    }

    public static PieceType[][] getPieceGridFromRot(PieceType pieceType, int rotIdx){
        PieceType[][] ret = getPieceGrid(pieceType);
        for (int i = 1; i <= rotIdx; i++) {
            PieceType[][] rot = new PieceType[ret.length][ret.length];
            for (int j = ret.length-1; j >= 0;j--) {
                for (int k = 0; k < ret.length;k++) {
                    rot[k][j] = ret[ret.length-j-1][k];
                }
            }
            ret = rot;
        }
        return ret;
    }
}
