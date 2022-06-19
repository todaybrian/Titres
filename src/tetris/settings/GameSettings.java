package tetris.settings;

import tetris.GamePanel;

import java.nio.file.Path;
import java.util.Properties;

public class GameSettings {
    private final Properties properties;

    public int music; // 0 for no music, 1 for NightSnow, 2 for Vremya, 3 for VirtualLight
    public double musicVolume; // self-explanatory
    public double sfxVolume; // self-explanatory

    public int renderFPS; // the number of frames per second to render at
    private GamePanel instance;


    public GameSettings(){
        properties = new Properties();
        instance = GamePanel.getGamePanel();
//        setSetting("renderFPS", instance.getMaxRenderFPS());
//        setSetting("musicVolume", 0.9);
//        setSetting("sfxVolume", 0.9);
    }

    public GameSettings(Properties properties){
        this.properties = properties;
        for(String key : properties.stringPropertyNames()){
            updateGameFromSetting(key);
        }
    }

    public static Properties loadFromFile(final Path path){
        final Properties properties2 = new Properties();
        try{
            properties2.load(path.toFile().toURI().toURL().openStream());
        }catch(Exception e){
            e.printStackTrace();
        }
        return properties2;
    }

    public void store(final Path path){
        try(final java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(path.toFile())){
            properties.store(fileOutputStream, null);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected double getSetting(String key, double defaultValue) {
        try {
            return Double.parseDouble(properties.getProperty(key, Double.toString(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    protected void setSetting(String key, double value) {
        properties.setProperty(key, Double.toString(value));
    }

    protected int getSetting(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, Integer.toString(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    protected void setSetting(String key, int value) {
        properties.setProperty(key, Integer.toString(value));
    }

    protected void updateGameFromSetting(String key){
        switch(key){
            case "renderFPS":
                instance.setRenderFPS(getSetting(key, instance.getMaxRenderFPS()));
                break;
            case "musicVolume":
                instance.getMusicPlayer().changeVolume(getSetting(key, 0.9));
                break;
            case "sfxVolume":
                instance.getSFXPlayer().changeVolume(getSetting(key, 0.9));
                break;
        }
    }

    public void updateGameToSettings() {
        instance.getMusicPlayer().changeVolume(musicVolume);
//        instance.getSFXPlayer().changeVolume(soundVolume);
    }
}
