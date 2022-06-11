package tetris.controls;

import tetris.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

    private GamePanel instance;

    public KeyboardInput(){
        instance = GamePanel.getGamePanel();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        instance.getGui().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        instance.getGui().keyReleased(e);
    }

    // Useless
    @Override
    public void keyTyped(KeyEvent e) {}
}
