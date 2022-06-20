/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * This is a MusicPlayer class that handles the playing of music/sfx.
 * In the GamePanel class, two instances of this class are created. One for music and one for sfx.
 *
 */
package tetris.music;

import javax.sound.sampled.*;
import java.io.File;

public class MusicPlayer {
    //Sound System Variables
    private Clip clip;
    private double volume;

    public MusicPlayer() {
        try {
            clip = AudioSystem.getClip(); //Initialize clip
        } catch (Exception e) {
            e.printStackTrace();
        }
        volume = 0.8; //Set volume to 80%
    }

    //Play a sound based on file
    public void play(File musicFile) {
        try {
            if (musicFile.exists()) { //If the file exists
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                DataLine.Info info = new DataLine.Info(Clip.class, audioInput.getFormat());

                clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioInput); //Open the clip
                this.changeVolume(volume); //Set the volume
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        clip.start(); //Start the clip
    }

    // Set if the current music should be looped or not
    public void setLoop(boolean loop) {
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.loop(0);
        }
    }

    //Stop the current music
    public void stopMusic() {
        clip.stop();
    }

    //Get the current volume of the music
    public int getVolume() {
        return (int) (volume * 100);
    }

    //Change the volume of the music
    public void changeVolume(double volume) {
        this.volume = volume;
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); //Get the gain control, which is used to change the volume

        // The volume system is based on a logarithmic scale, so the volume is converted to ensure a linear volume increase
        // At volume = 0, gainControl is -59dB (barely audible). At volume = 0.5, gainControl is -5dB. At volume = 1, gainControl is +1dB.
        // A 10dB increase means double in volume, but human ears are less sensitive to very minute changes in soft sounds, so the scale is weighted towards the loud end.
        gainControl.setValue(20f * (float) Math.log10(volume + 0.001) + 1);
    }
}
