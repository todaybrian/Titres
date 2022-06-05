package tetris.settings;

import tetris.GamePanel;

public class GameSettings {
    public int music; // 0 for no music, 1 for NightSnow, 2 for Vremya, 3 for VirtualLight
    public double musicVolume; // self-explanatory
    public double soundVolume; // self-explanatory

    public int renderFPS; // the number of frames per second to render at
    private GamePanel instance;

    public GameSettings(){
        instance = GamePanel.getGamePanel();
        renderFPS = 60;
    }

    public void updateGameToSettings() {
        instance.getMusicPlayer().changeVolume(musicVolume);
        instance.getSFXPlayer().changeVolume(soundVolume);
    }
}
