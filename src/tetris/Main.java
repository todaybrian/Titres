package tetris;


public class Main {
    public static GameFrame instance;
    public static void main(String[] args){
        System.setProperty("sun.java2d.uiScale.enabled", "false");
        instance = new GameFrame();
    }
}
