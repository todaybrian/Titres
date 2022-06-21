/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Handles the logic differentiation between each piece type.
 */

package tetris.game;

public enum PieceType {
    J(0), Z(1), S(2), L(3), T(4), O(5), I(6), GHOST(7), NULL(-1); // Ghost = drop marker, null = blank

    private final int id; // Easy way to call the piece types
    private static final PieceType[][][] pieceGrid; // Holds all the piece grids (called by first index for specific piece type)

    //wall kick data
    public static final int[][][] wallKickDataJLSTZ;
    public static final int[][][] wallKickDataI; // The "I" piece handles wall kicks in a unique way

    //Constructor to initialize the id of each piece type
    PieceType(int id) {
        this.id = id;
    }

    // Returns the id of the piece type
    public int getId() {
        return id;
    }

    // Initializes wall kicks and default pieceGrids
    static {
        pieceGrid = new PieceType[7][][];

        //J
        pieceGrid[0] = new PieceType[][]{{J, NULL, NULL},
                {J, J, J},
                {NULL, NULL, NULL}};
        //Z
        pieceGrid[1] = new PieceType[][]{{Z, Z, NULL},
                {NULL, Z, Z},
                {NULL, NULL, NULL}};
        //S
        pieceGrid[2] = new PieceType[][]{{NULL, S, S},
                {S, S, NULL},
                {NULL, NULL, NULL}};

        //L
        pieceGrid[3] = new PieceType[][]{{NULL, NULL, L},
                {L, L, L},
                {NULL, NULL, NULL}};
        //T
        pieceGrid[4] = new PieceType[][]{{NULL, T, NULL},
                {T, T, T},
                {NULL, NULL, NULL}};

        //O
        pieceGrid[5] = new PieceType[][]{{O, O, NULL},
                {O, O, NULL},
                {NULL, NULL, NULL}};

        //I
        pieceGrid[6] = new PieceType[][]{{NULL, NULL, NULL, NULL},
                {I, I, I, I},
                {NULL, NULL, NULL, NULL},
                {NULL, NULL, NULL, NULL}};

        //Wall kick data from Tetris SRS
        //https://harddrop.com/wiki/SRS

        //J, L, S, T, Z pieces
        wallKickDataJLSTZ = new int[][][]{
                {{0, 0}, {-1, 0}, {-1, 1}, {0, -2}, {-1, -2}},
                {{0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}},
                {{0, 0}, {1, 0}, {1, 1}, {0, -2}, {1, -2}},
                {{0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}}
        };

        //I pieces
        wallKickDataI = new int[][][]{
                {{0, 0}, {-2, 0}, {1, 0}, {-2, 1}, {1, 2}},
                {{0, 0}, {-1, 0}, {2, 0}, {-1, 2}, {2, -1}},
                {{0, 0}, {2, 0}, {-1, 0}, {2, 1}, {-1, -2}},
                {{0, 0}, {1, 0}, {-2, 0}, {1, -2}, {-2, 1}}
        };
    }

    /**
     * Returns the piece grid for the given piece type.
     *
     * @param pieceType The piece type to get the grid for.
     * @return The piece grid for the given piece type.
     */
    public static PieceType[][] getPieceGrid(PieceType pieceType) {
        return pieceGrid[pieceType.id];
    }

    /**
     * Returns the piece grid for the given rotation.
     *
     * Rotation index (clockwise): 0 = 0 degrees, 1 = 90 degrees, 2 = 180 degrees, 3 = 270 degrees.
     *
     *
     * @param pieceType The piece type to get the grid for.
     * @param rotIdx The rotation index to get the grid for.
     * @return The piece grid for the given rotation.
     */
    public static PieceType[][] getPieceGridFromRot(PieceType pieceType, int rotIdx) {

        PieceType[][] ret = getPieceGrid(pieceType); // Rotated piece grid
        PieceType[][] temp;  // Temporary piece grid to hold rotated piece grid

        for (int i = 1; i <= rotIdx; i++) { // Rotate the piece grid 90 degrees until the rotation index is reached
            temp = new PieceType[ret.length][ret.length];

            for (int j = ret.length - 1; j >= 0; j--) {
                for (int k = 0; k < ret.length; k++) {
                    temp[k][j] = ret[ret.length - j - 1][k];
                }
            }
            ret = temp;
        }
        return ret;
    }
}
