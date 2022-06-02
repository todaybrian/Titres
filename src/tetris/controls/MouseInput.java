package tetris.controls;

import tetris.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    private static double scale;
    public static boolean clicking = false;
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

    }

    public void mousePressed(MouseEvent e) {
        clicking = true;
    }
    public void mouseReleased(MouseEvent e) {
        clicking = false;
    }

    public void mouseEntered(MouseEvent e){}


    public void mouseExited(MouseEvent e) {}


}
