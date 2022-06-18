/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * This class is used to handle the keyboard input.
 * The game uses this class to determine if a key is pressed or released.
 *
 * This class also bypasses quirks in the OS and how it handles key input (such as auto-repeat)
 */
package tetris.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    //Boolean array to store the state of each key
    public boolean[] keyPressed;

    //Constructor to initialize the keyPressed array
    //Called in the GamePanel constructor
    public KeyboardInput(){
        keyPressed = new boolean[KeyEvent.KEY_LAST+2];
    }

    //If a key is pressed, set the state of the key pressed in the keyPressed array to false
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < keyPressed.length) {// If the key is within the bounds of the array. We don't need to process all keys.
            keyPressed[e.getKeyCode()] = true;
        }
    }

    //If a key is released, set the state of the key pressed in the keyPressed array to false
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() < keyPressed.length) { // If the key is within the bounds of the array. We don't need to process all keys.
            keyPressed[e.getKeyCode()] = false;
        }
    }

    //left empty because we don't need it; must be here because it is required to be overridden by the KeyListener interface
    @Override
    public void keyTyped(KeyEvent e) {}
}
