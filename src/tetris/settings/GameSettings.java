package tetris.settings;

//TODO: QUESTION: Should this be static? Should we save this in a file?
//ANSWER: Static yes because these are essentially global variables, file yes because UsEr fRienDliNess
public class GameSettings {
    public static int music; // 0 for no music, 1 for NightSnow, 2 for Vremya, 3 for VirtualLight
    public static int volume; // self-explanatory

    public static int renderFPS; // the number of frames per second to render at

    public GameSettings(){
        renderFPS = 60;
    }
}
