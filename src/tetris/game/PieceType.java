package tetris.game;

import java.util.Arrays;

public enum PieceType {
    J, Z, S, L, T, O, I, BORDER, GHOST, NULL;

    public static PieceType[][] getPieceGrid(PieceType pieceType){
        PieceType[][] ret = new PieceType[5][5];
        for (int i = 0; i < 5; i++) {
            Arrays.fill(ret[i], PieceType.NULL);
        }
        ret[2][2] = pieceType;
        switch(pieceType) {
            case I:
                ret[2][1] = ret[2][3] = ret[2][4] = pieceType;
                break;
            case J:
                ret[1][1] = ret[2][1] = ret[2][3] = pieceType;
                break;
            case L:
                ret[1][3] = ret[2][1] = ret[2][3] = pieceType;
                break;
            case O:
                ret[1][2] = ret[1][3] = ret[2][3] = pieceType;
                break;
            case S:
                ret[1][2] = ret[1][3] = ret[2][1] = pieceType;
                break;
            case T:
                ret[1][2] = ret[2][1] = ret[2][3] = pieceType;
                break;
            case Z:
                ret[1][2] = ret[1][1] = ret[2][3] = pieceType;
                break;
        }
        return ret;
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

    public int[][][] getRotations(PieceType pieceType) {
        if(pieceType == PieceType.O){
            return new int[][][]{{{0, 0}, {0, -1}, {-1, -1}, {-1, 0}}};
        } else if(pieceType == PieceType.I){
            return new int[][][]{{{0, 0}, {-1, 0}, {-1, 1}, {0, 1}},
                    {{-1, 0}, {0, 0}, {1, 1}, {0, 1}},
                    {{2, 0}, {0, 0}, {-2, 1}, {0, 1}},
                    {{-1, 0}, {0, 1}, {1, 0}, {0, -1}},
                    {{2, 0}, {0, -2}, {-2, 0}, {0, 2}}};
        } else{
            return new int[][][]{{{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                    {{0, 0}, {1, 0}, {0, 0}, {-1, 0}},
                    {{0, 0}, {1, -1}, {0, 0}, {-1, -1}},
                    {{0, 0}, {0, 2}, {0, 0}, {0, 2}},
                    {{0, 0}, {1, 2}, {0, 0}, {-1, 2}}};
        }
    }
}
