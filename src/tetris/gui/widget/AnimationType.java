package tetris.gui.widget;

public enum AnimationType {
    NONE (0), RIGHT (1), LEFT (2), UP (3), DOWN (4);
    private final int id;

    private final static int[] HOVER_X_OFFSET = {0, 60, -60, 0, 0};
    private final static int[] CLICK_X_OFFSET = {0, 100, -100, 0, 0};
    private final static int[] TRANSITION_X_OFFSET = {0, 600, -600, 0, 0};
    private final static int[] HOVER_Y_OFFSET = {0, 0, 0, -10, 10};
    private final static int[] CLICK_Y_OFFSET = {0, 0, 0, -20, 20};
    private final static int[] TRANSITION_Y_OFFSET = {0, 0, 0, 0, 0};


    AnimationType(int id){
        this.id = id;
    }
    public int getHoverXOffset(){
        return HOVER_X_OFFSET[id];
    }
    public int getClickXOffset(){
        return CLICK_X_OFFSET[id];
    }
    public int getTransitionXOffset(){
        return TRANSITION_X_OFFSET[id];
    }
    public int getHoverYOffset(){
        return HOVER_Y_OFFSET[id];
    }
    public int getClickYOffset(){
        return CLICK_Y_OFFSET[id];
    }
    public int getTransitionYOffset(){
        return TRANSITION_Y_OFFSET[id];
    }
}