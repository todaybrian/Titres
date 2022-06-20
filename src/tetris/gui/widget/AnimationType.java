/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Enum to store different directions of animations.
 */

package tetris.gui.widget;

public enum AnimationType {
    NONE (0), RIGHT (1), LEFT (2), UP (3), DOWN (4); // The four directions, and 'none'
    private final int ID; // Easy way to call each direction in the enum

    // Offsets stored as final to maintain consistency for all button animations
    private final static int[] HOVER_X_OFFSET = {0, -60, 60, 0, 0};
    private final static int[] CLICK_X_OFFSET = {0, -100, 100, 0, 0};
    private final static int[] TRANSITION_X_OFFSET = {0, 600, -600, 0, 0};
    private final static int[] HOVER_Y_OFFSET = {0, 0, 0, 10, -10};
    private final static int[] CLICK_Y_OFFSET = {0, 0, 0, 20, -20};
    private final static int[] TRANSITION_Y_OFFSET = {0, 0, 0, -30, 60};


    AnimationType(int ID){
        this.ID = ID;
    }

    // Getter functions to access animation offset data
    public int getHoverXOffset(){
        return HOVER_X_OFFSET[ID];
    }
    public int getClickXOffset(){
        return CLICK_X_OFFSET[ID];
    }
    public int getTransitionXOffset(){
        return TRANSITION_X_OFFSET[ID];
    }
    public int getHoverYOffset(){
        return HOVER_Y_OFFSET[ID];
    }
    public int getClickYOffset(){
        return CLICK_Y_OFFSET[ID];
    }
    public int getTransitionYOffset(){
        return TRANSITION_Y_OFFSET[ID];
    }
}