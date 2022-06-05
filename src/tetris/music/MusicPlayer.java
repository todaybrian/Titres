package tetris.music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class MusicPlayer {
    static MusicPlayer player = new MusicPlayer();
    public static Clip clip;

    private MusicPlayer() {

    }
    public static MusicPlayer getInstance() {
        return player;
    }

    public static void loadMusic(String filepath) {
        try {
            File musicPath = new File(filepath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            }
        }catch (Exception e) {

        }
    }

    public void changeVolume(double volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        double gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue((float) gain);
    }
}
