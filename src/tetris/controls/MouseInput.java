package tetris.controls;

import tetris.GamePanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//TODO: Make this not static
public class MouseInput implements MouseListener {
    private static double scale;

    public static void setScale(double scale){
        MouseInput.scale = scale;
    }

    public MouseInput() {}

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
    public void mouseClicked(MouseEvent e) {
        //Send left click event
        if(e.getButton() == MouseEvent.BUTTON1){
            GamePanel.getGamePanel().mouseClicked();
        }
    }

    public void mousePressed(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e){}


    public void mouseExited(MouseEvent e) {}


}
