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
    }

    public void loadMusic(String filepath) {
        try {
            File musicPath = null;
            if(cache.containsKey(filepath)){
                musicPath = cache.get(filepath);
            } else {
                musicPath = new File(filepath);
                cache.put(filepath, musicPath);
            }
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                DataLine.Info info = new DataLine.Info(Clip.class, audioInput.getFormat());

                clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioInput);
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

    public int getVolume(){
        return (int)(volume*100);
    }

    public void changeVolume(double volume) {
        this.volume = volume;
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        double gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue((float) gain);
    }
}
