package tetris.controls;

import tetris.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    public boolean[] keyPressed;
    private GamePanel instance;

    public KeyboardInput(){
        keyPressed = new boolean[KeyEvent.KEY_LAST+2];
        instance = GamePanel.getGamePanel();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < keyPressed.length) {
            keyPressed[e.getKeyCode()] = true;
            instance.getGui().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() < keyPressed.length) {
            keyPressed[e.getKeyCode()] = false;
            instance.getGui().keyReleased(e);
        }
    }

    // Useless
    @Override
    public void keyTyped(KeyEvent e) {}
}
