package tetris.settings;

public class GameSettings {
    public int music; // 0 for no music, 1 for NightSnow, 2 for Vremya, 3 for VirtualLight
    public int volume; // self-explanatory

    public int renderFPS; // the number of frames per second to render at

    public GameSettings(){
        renderFPS = 60;
    }
}
