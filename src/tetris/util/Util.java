/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Util class. Contains miscellaneous static methods that are used in the game.
 */
package tetris.util;

import java.awt.*;
import java.util.Map;

public class Util {
    /**
     * Given a Graphics2D object, this method will find all the appropriate rendering hints and apply them.
     * This is used to make the game look better on different monitors.
     *
     * Based on https://stackoverflow.com/questions/31536952/how-to-fix-text-quality-in-java-graphics
     *
     * @param g Graphics2D object
     */
    public static void setGraphicsFlags(Graphics2D g){
        Map<?, ?> desktopHints =
                (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

        if (desktopHints != null) {
            g.setRenderingHints(desktopHints);
        }
    }


    /**
     * Clamps a value between a minimum and maximum value.
     *
     * If the value is less than the minimum, the minimum value is returned.
     * If the value is greater than the maximum, the maximum value is returned.
     * Otherwise, the value is returned as is.
     *
     * @param value Value to clamp
     * @param min Minimum value
     * @param max Maximum value
     * @return The clamped value.
     */

    public static float clamp(float value, float min, float max){
        return Math.max(min, Math.min(value, max));
    }

}
