package tetris.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    private static boolean[] keys = new boolean[1024];
    private static int[] keyTimes = new int[1024];

    public KeyboardInput(){

    }

    public void update(){
        for (int i = 0; i < keys.length; i++) {
            if(keys[i]){
                keyTimes[i]++;
            } else{
                keyTimes[i] = 0;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < keys.length){
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() < keys.length){
            keys[e.getKeyCode()] = false;
        }
    }

    // Useless
    @Override
    public void keyTyped(KeyEvent e) {}
}
