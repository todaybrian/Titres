/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * This class is used to check if the window is focused or not.
 * This will prevent buttons from being hovered over when the game is not focused.
 */
package tetris.util;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowFocus implements WindowListener {
    //Variable to hold if the window is shown or not
    public static boolean isShown = true;

    //If window is minimized, no longer shown
    @Override
    public void windowIconified(WindowEvent e) {
      isShown = false;
    }

    //If window is restored, it is shown
    @Override
    public void windowDeiconified(WindowEvent e) {
        isShown = true;
    }

    //If window is in focus, it is considered shown
    @Override
    public void windowActivated(WindowEvent e) {
        isShown = true;
    }

    //If window is not in focus, it is not considered shown
    @Override
    public void windowDeactivated(WindowEvent e) {
        isShown = false;
    }

    //left empty because we don't need it; must be here because it is required to be overridden by the WindowListener interface
    @Override
    public void windowOpened(WindowEvent e) {}

    //left empty because we don't need it; must be here because it is required to be overridden by the WindowListener interface
    @Override
    public void windowClosing(WindowEvent e) {}

    //left empty because we don't need it; must be here because it is required to be overridden by the WindowListener interface
    @Override
    public void windowClosed(WindowEvent e) {}
}
