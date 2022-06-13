package tetris.util;

import java.awt.*;
import java.util.Map;

public class Util {
    public static void setGraphicsFlags(Graphics2D g){
        Map<?, ?> desktopHints =
                (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

        if (desktopHints != null) {
            g.setRenderingHints(desktopHints);
        }
    }

    public static float clamp(float value, float min, float max){
        return Math.max(min, Math.min(value, max));
    }

}
