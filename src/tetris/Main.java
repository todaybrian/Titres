/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 20, 22022
 *
 * Main class starts the game.
 * It first disables UI scaling, then runs the constructor in the GameFrame class.
 */
package tetris;

public class Main {
    public static void main(String[] args){
        //disable UI scaling, fix from https://stackoverflow.com/questions/47613006/how-to-disable-scaling-the-ui-on-windows-for-java-9-applications
        System.setProperty("sun.java2d.uiScale.enabled", "false");

        new GameFrame();
    }
}
