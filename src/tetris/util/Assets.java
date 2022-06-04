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

    public static final String SIGNIKA_FONT_FILE = "../assets/fonts/Signika.ttf";
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

    public static final String BACKGROUND_PREFIX = "src/assets/backgrounds/background";

    public static final String TOP_MAIN_MENU_FILE = "src/assets/menus/top_main_menu.png";
    public static final String BOTTOM_MAIN_MENU_FILE = "src/assets/menus/bottom_main_menu.png";
    public static final String TOP_SETTINGS_FILE = "src/assets/menus/top_settings.png";
    public static final String BOTTOM_SETTINGS_FILE = "src/assets/menus/bottom_settings.png";


    public class Button{
        public static final String EXIT_BUTTON="src/assets/button/exit_button.png";
        public static final String SOLO_BUTTON="src/assets/button/solo_button.png";
        public static final String BACK_BUTTON = "src/assets/button/back_button.png";

        public static final String SETTINGS_BUTTON="src/assets/button/settings_button.png";
        public static final String GITHUB_BUTTON = "src/assets/button/github_button.png";

        public static final String SLIDER = "src/assets/button/slider.png";
    }
}
