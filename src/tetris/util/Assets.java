/**
 * Brian Yan, Aaron Zhang
 *
 *
 */
package tetris.util;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Assets {

    public static final String BACKGROUND_PREFIX = "src/assets/backgrounds/";

    public enum Fonts {
        KDAM_FONT("KdamThmorPro-Regular.ttf");
        private Font font;

        Fonts(String file) {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, Files.newInputStream(Paths.get("src/assets/fonts/" + file)));
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }

        public Font get() {
            return font;
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
        TETRIS_GRID ("tetris_grid.png"),
        PIECES("pieces.png");

        private final Image image;

        Game(String s) {
            try {
                ImageIcon imageIcon = new ImageIcon("src/assets/game/" +s);
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
        EXIT_BUTTON("exit_button.png"),
        SOLO_BUTTON("solo_button.png"),
        BACK_BUTTON("back_button.png"),

        FOURTY_LINES_BUTTON("40_lines_button.png"),

        SETTINGS_BUTTON("settings_button.png"),
        GITHUB_BUTTON("github_button.png"),

        SLIDER("slider.png"),

        START_40_BUTTON("start_40.gif"),

        RETRY_BUTTON("retry_button.png"),

        BACK_TO_TITLE_BUTTON("back_to_title_button.png");

        private final Image image;

        Button(String s) {
            try {
                ImageIcon imageIcon = new ImageIcon("src/assets/button/"+s);
                image = imageIcon.getImage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Image get() {
            return image;
        }

    }

    public enum Music {
        NIGHT_SNOW("NightSnow.wav"),
        VREMYA("Vremya.wav"),
        VIRTUAL_LIGHT("VirtualLight.wav");

        private File file;

        Music(String s) {
            file = new File("src/assets/music/" + s);
        }

        public File get() {
            return file;
        }
    }

    public enum SFX {
        CLICK_1("sfx_click_lvl_1.wav"),
        CLICK_2("sfx_click_lvl_2.wav"),
        HOVER("sfx_hover.wav"),

        CLICK_BACK("sfx_click_back.wav"),

        //This is used to unlag the music/sfx player
        SILENCE("silence.wav");

        private final File file;

        SFX(String s) {
            file = new File("src/assets/sfx/" +s);
        }

        public File get() {
            return file;
        }
    }
}
