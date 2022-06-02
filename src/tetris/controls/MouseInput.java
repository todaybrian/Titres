package tetris.controls;

import tetris.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseInput {
    private static double scale;
    public static void setScale(double scale){
        MouseInput.scale = scale;
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


}
