package tetris.music;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;

public class MusicPlayer {
    public Clip clip;
    private HashMap<String, File> cache;
    private double volume;

    public MusicPlayer() {
        try {
            clip = AudioSystem.getClip(); //Initialize clip
        } catch (Exception e) {
            e.printStackTrace();
        }
        cache = new HashMap<>();
        volume = 0.8;
    }

    public void loadMusic(File musicFile) {
        try {
            if (musicFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                DataLine.Info info = new DataLine.Info(Clip.class, audioInput.getFormat());

                clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioInput);
                this.changeVolume(volume);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusic(){
        clip.start();
    }

    public void setLoop(boolean loop) {
        if(loop){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else{
            clip.loop(0);
        }
    }

    public void stopMusic(){
        clip.stop();
    }
    public int getVolume(){
        return (int)(volume*100);
    }

    public void changeVolume(double volume) {
        this.volume = volume;
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume+0.001)+1); // If a computer can't handle -59dB I give up
    }
}
