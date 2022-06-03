package tetris.controls;

import tetris.GamePanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//TODO: Make this not static
public class MouseInput implements MouseListener {
    private GamePanel instance;

    private static double scale;

    public static void setScale(double scale){
        MouseInput.scale = scale;
    }

    public MouseInput() {
        instance = GamePanel.getGamePanel();
    }

    public static Point getLocation(){
        Point ret = MouseInfo.getPointerInfo().getLocation();
        ret.x = scale(ret.x);
        ret.y = scale(ret.y);
        return ret;
    }

    private static int scale(double val) {
        return (int)Math.round(val * scale);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Send left click event
        if(e.getButton() == MouseEvent.BUTTON1){
            instance.getGui().mouseClicked();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Send left click release event
        if(e.getButton() == MouseEvent.BUTTON1){
            instance.getGui().mouseReleased();
        }
    }

    // This function was not used because it is unreliable
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e) {}
}
