/**
 * Brian Yan, Aaron Zhang
 *
 *
 */
package tetris.util;

import tetris.Main;

import java.awt.*;
import java.io.IOException;

public class Assets {
    public static final String WELCOME_SCREEN = "src/assets/welcome_screen.gif";

    public static final String SIGNIKA_FONT_FILE = "src/assets/fonts/Signika.ttf";
    public static final Font SIGNIKA_FONT;

    static {
        try {
            SIGNIKA_FONT = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream(SIGNIKA_FONT_FILE));
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String LOGO_FILE = "src/assets/logo.png";
    public static final String NIGHT_SNOW = "src/assets/NightSnow.wav";
    public static final String VREMYA = "src/assets/Vremya.wav";
    public static final String VIRTUAL_LIGHT = "src/assets/VirtualLight.wav";
}
