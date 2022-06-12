/**
 * Brian Yan, Aaron Zhang
 *
 *
 */
package tetris.util;

import tetris.Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Assets {

    public static final String BACKGROUND_PREFIX = "src/assets/backgrounds/";

    public static class Fonts {
        public static final String KDAM_FONT_FILE = "../assets/fonts/KdamThmorPro-Regular.ttf";
        public static final Font KDAM_FONT;
        static {
            try {
                KDAM_FONT = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream(KDAM_FONT_FILE));
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public enum Music {
        NIGHT_SNOW("src/assets/music/NightSnow.wav"),
        VREMYA("src/assets/music/Vremya.wav"),
        VIRTUAL_LIGHT("src/assets/music/VirtualLight.wav");

        private final String file;

        Music(String file) {
            this.file = file;
        }

        public String get() {
            return file;
        }
    }

    public enum Gui {
        LOGO("src/assets/logo.png"),

        WELCOME_SCREEN("src/assets/welcome_screen.gif"),

        TOP_MAIN_MENU("src/assets/menus/top_main_menu.png"),
        BOTTOM_MAIN_MENU("src/assets/menus/bottom_main_menu.png"),

        TOP_SETTINGS("src/assets/menus/top_settings.png"),
        BOTTOM_SETTINGS("src/assets/menus/bottom_settings.png"),

        TOP_SOLO("src/assets/menus/top_solo.png"),
        BOTTOM_SOLO("src/assets/menus/bottom_solo.png"),

        TOP_40("src/assets/menus/top_40.png"),
        BOTTOM_40("src/assets/menus/bottom_40.png");

        private final Image image;

        Gui(String s) {
            try {
                ImageIcon imageIcon = new ImageIcon(s);
                image = imageIcon.getImage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Image get() {
            return image;
        }
    }

    public enum Game{
        TETRIS_GRID ("src/assets/game/tetris_grid.png"),
        PIECES("src/assets/game/pieces.png");

        private Image image;

        Game(String s) {
            try {
                ImageIcon imageIcon = new ImageIcon(s);
                image = imageIcon.getImage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Image get() {
            return image;
        }
    }

    public enum Button{
        EXIT_BUTTON("src/assets/button/exit_button.png"),
        SOLO_BUTTON("src/assets/button/solo_button.png"),
        BACK_BUTTON("src/assets/button/back_button.png"),

        FOURTY_LINES_BUTTON("src/assets/button/40_lines_button.png"),

        SETTINGS_BUTTON("src/assets/button/settings_button.png"),
        GITHUB_BUTTON("src/assets/button/github_button.png"),

        SLIDER("src/assets/button/slider.png"),

        START_40_BUTTON("src/assets/button/start_40.gif");

        private Image image;

        Button(String s) {
            try {
                ImageIcon imageIcon = new ImageIcon(s);
                image = imageIcon.getImage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Image get() {
            return image;
        }

    }

    public static class SFX {
        public static final String CLICK_1 = "src/assets/sfx/sfx_click_lvl_1.wav";
        public static final String CLICK_2 = "src/assets/sfx/sfx_click_lvl_2.wav";
        public static final String HOVER = "src/assets/sfx/sfx_hover.wav";

        public static final String CLICK_BACK = "src/assets/sfx/sfx_click_back.wav";

        //This is used to unlag the music/sfx player
        public static final String SILENCE = "src/assets/sfx/silence.wav";
    }
}
